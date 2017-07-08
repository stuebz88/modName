package com.example.examplemod.proxy;

import com.example.examplemod.init.ModRegistry;

import net.minecraftforge.common.MinecraftForge;
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
	}
	
	public void init(FMLInitializationEvent e)
	{
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{

	}
}