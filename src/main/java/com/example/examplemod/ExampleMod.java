package com.example.examplemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.examplemod.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import ttftcuts.atg.WorldTypeATG;
import ttftcuts.atg.compat.ModCompat;
import ttftcuts.atg.configuration.ConfigHandler;
import ttftcuts.atg.generator.GlobalRegistry;

@Mod(modid = Ref.MODID, name=Ref.NAME, version = Ref.VERSION)
public class ExampleMod
{
	@SidedProxy(clientSide = Ref.CLIENT_PROXY, serverSide = Ref.SERVER_PROXY)
	public static CommonProxy proxy;
	
	/* Taken from ATG */
	public static GlobalRegistry globalRegistry = new GlobalRegistry();
	public static final Logger logger = LogManager.getLogger(Ref.MODID);
	public static WorldTypeATG worldType;
	public static ConfigHandler config;
    public static ModCompat modCompat = new ModCompat();
    /* End taken from ATG */
	
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
    	/* Taken from ATG */
    	config = new ConfigHandler(e.getSuggestedConfigurationFile());
        worldType = new WorldTypeATG("ExampleMod");
        /* End taken from ATG */
    	proxy.preInit(e);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
    	proxy.postInit(e);
    }
    
    /* Taken from ATG */
    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete(event);
    }

    @Mod.EventHandler
    public void handleIMC(FMLInterModComms.IMCEvent event) {
        modCompat.processIMC(event.getMessages());
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        proxy.serverStopped(event);
    }    
    /* End taken from ATG */
}
