package com.example.examplemod.common.blocks;

public final class ModBlocks {

	public static Block modBlock;
	
	public static void createBlocks() {
		GameRegistry.registerBlock(modBlock = new BasicBlock("modBlock").setLightLevel(1.0f), "modBlock")
	}
}
