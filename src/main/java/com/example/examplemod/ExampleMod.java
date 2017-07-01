package com.example.examplemod;

import com.example.examplemod.proxy.IProxy;

// Test Comment2
// hi fattie
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Ref.MODID, name=Ref.NAME, version = Ref.VERSION)
public class ExampleMod
{
	@SidedProxy(clientSide=Ref.CLIENT_PROXY, serverSide=Ref.SERVER_PROXY)
	public static IProxy proxy;
	
    @Mod.EventHandler
    public void preInit(FMLInitializationEvent event)
    {
    	proxy.preInit();
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }
    
    @Mod.EventHandler
    public void postInit(FMLInitializationEvent event)
    {
    	proxy.postInit();
    }
}