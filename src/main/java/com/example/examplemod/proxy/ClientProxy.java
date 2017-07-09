package com.example.examplemod.proxy;

import com.example.examplemod.Ref;
import com.example.examplemod.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ttftcuts.atg.tweaks.GrassColours;

public class ClientProxy extends CommonProxy {
	
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(this);
    }

	@SubscribeEvent
	public void onModelRegistry(ModelRegistryEvent e) 
	{
		for (Item item : Ref.ITEMS)
			if (item instanceof IHasModel)
			{
				((IHasModel) item).initModel(e);
				
			}
		for (Block block : Ref.BLOCKS)
			if (block instanceof IHasModel)
			{
				((IHasModel) block).initModel(e);
				System.out.println("initModel run");
			}
	}

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void loadComplete(FMLLoadCompleteEvent event) {
        super.loadComplete(event);
        GrassColours.init();
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        GrassColours.clearCache();
    }

    @Override
    public void serverStopped(FMLServerStoppedEvent event) {
        GrassColours.clearCache();
    }
}
