package com.example.examplemod.proxy;

import com.example.examplemod.common.core.EventHandlerCommon;
import com.example.examplemod.common.core.ITurn;
import com.example.examplemod.common.core.WorldTurn;
import com.example.examplemod.common.core.WorldTurnStorage;
import com.example.examplemod.init.ModRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	//public static Configuration config;
	
	public void preInit(FMLPreInitializationEvent e)
	{
		/*File configDir = e.getModConfigurationDirectory();
		config = new Configuration(new File(configDir.getPath(), "examplemod.cfg"));
		Config.readConfig();*/
		MinecraftForge.EVENT_BUS.register(new ModRegistry());
		CapabilityManager.INSTANCE.register(ITurn.class, new WorldTurnStorage(), WorldTurn.class);
		MinecraftForge.EVENT_BUS.register(new EventHandlerCommon());
	}
	
	public void init(FMLInitializationEvent e)
	{
		
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		
	}
}
