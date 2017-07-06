package com.example.examplemod.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class RegisterUtil {
	public static void registerAll(FMLPreInitializationEvent event) {
		
	}
	
	private static void registerBlocks(FMLPreInitializationEvent event, Block...blocks) {
		for(Block block : blocks) {
			final ItemBlock itemblock = new ItemBlock(block);
			if(event.getSide() == Side.CLIENT) {
				//GameRegistry.register(block);
				//GameRegistry.register(itemblock)
			}
		}
	}
	
	private static void registerItems(FMLPreInitializationEvent event, Item...items) {
		
	}
}
