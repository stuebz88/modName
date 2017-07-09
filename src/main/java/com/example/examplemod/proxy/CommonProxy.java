package com.example.examplemod.proxy;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.init.ModRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;
import ttftcuts.atg.ATGBiomes;
import ttftcuts.atg.generator.biome.VillageBlocks;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ATGBiomes.init();
        ExampleMod.modCompat.preInit();
        /*File configDir = e.getModConfigurationDirectory();
		config = new Configuration(new File(configDir.getPath(), "examplemod.cfg"));
		Config.readConfig();*/
		MinecraftForge.EVENT_BUS.register(new ModRegistry());
    }

    public void init(FMLInitializationEvent event) {
        MinecraftForge.TERRAIN_GEN_BUS.register(new VillageBlocks());
        ExampleMod.modCompat.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
    	ExampleMod.modCompat.postInit();
        //ModRegistry.initBiomes();
    }

    public void loadComplete(FMLLoadCompleteEvent event) {
    	ExampleMod.modCompat.processIMC(FMLInterModComms.fetchRuntimeMessages(this));
    	ExampleMod.modCompat.registerBuiltInModules();
    }

    public void serverStarting(FMLServerStartingEvent event) {

    }

    public void serverStopped(FMLServerStoppedEvent event) {

    }
}
