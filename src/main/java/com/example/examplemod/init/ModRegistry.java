package com.example.examplemod.init;

import com.example.examplemod.Ref;
import com.example.examplemod.common.blocks.BlockModBanner;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModRegistry 
{
	public static final Block MODBANNER = new BlockModBanner();
	
	private static void initRecipes() 
	{
	
	}
	
	@SubscribeEvent
	public void onBlockRegistry(RegistryEvent.Register<Block> e) 
	{
		e.getRegistry().registerAll(Ref.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public void onItemRegistry(RegistryEvent.Register<Item> e) 
	{
		e.getRegistry().registerAll(Ref.ITEMS.toArray(new Item[0]));
		for (Block block : Ref.BLOCKS)
			e.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
	
	@SubscribeEvent
	public void onRecipeRegistry(RegistryEvent.Register<IRecipe> e)
	{
		initRecipes();
		e.getRegistry().registerAll(Ref.RECIPES.toArray(new IRecipe[0]));
	}
}
