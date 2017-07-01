package com.example.examplemod.common.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import scala.tools.nsc.backend.icode.BasicBlocks.BasicBlock;

public final class ModBlocks {

	public static net.minecraft.block.Block modBlock;
	
	public static void createBlocks() {
		GameRegistry.register(modBlock = ((Block) new BasicBlock("modBlock")).setLightLevel(1.0f), "modBlock");
		//GameRegistry.registerBlock(modBlock = ((Block) new BasicBlock("modBlock")).setLightLevel(1.0f), "modBlock");
	}
}