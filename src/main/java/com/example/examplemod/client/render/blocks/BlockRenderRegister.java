package com.example.examplemod.client.render.blocks;


import com.example.examplemod.Ref;
import com.example.examplemod.common.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public final class BlockRenderRegister {

public static String modid = Ref.MODID;

public static void registerBlockRenderer() {
	reg(ModBlocks.modBlock);
}

public static void reg(Block modBlock) {
    Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
    .register(Item.getItemFromBlock(modBlock), 0, new ModelResourceLocation(modid + ":" + ((Block) modBlock).getUnlocalizedName().substring(5), "inventory"));
	}
}
