package com.example.examplemod.init;

import com.example.examplemod.Ref;
import com.example.examplemod.common.blocks.BlockModBanner;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ttftcuts.atg.biome.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class ModRegistry 
{
	public static final Block MODBANNER = new BlockModBanner();
	
	
	public static final Biome GRAVEL_BEACH = new BiomeGravelBeach();
	public static final Biome SCRUBLAND = new BiomeScrubland();
	public static final Biome SHRUBLAND = new BiomeShrubland();
	public static final Biome GRAVEL_BEACH_SNOWY = new BiomeSnowyGravelBeach();
	public static final Biome TROPICAL_SHRUBLAND = new BiomeTropicalShrubland();
	public static final Biome TUNDRA = new BiomeTundra();
	public static final Biome WOODLAND = new BiomeWoodland();
	
	
	private static void initRecipes() 
	{
	
	}
	
	public static void initBiomes()
	{
		/*
		BiomeDictionary.addTypes(GRAVELBEACH, Type.COLD, Type.BEACH);
		BiomeDictionary.addTypes(SCRUBLAND, Type.HOT, Type.SPARSE, Type.DRY, Type.SANDY, Type.SAVANNA);
		BiomeDictionary.addTypes(SHRUBLAND, Type.PLAINS, Type.SPARSE);
		BiomeDictionary.addTypes(SNOWYGRAVELBEACH, Type.COLD, Type.BEACH, Type.SNOWY);
		BiomeDictionary.addTypes(TROPICALSHRUBLAND, Type.HOT, Type.WET, Type.JUNGLE, Type.FOREST, Type.SAVANNA);
		BiomeDictionary.addTypes(TUNDRA, Type.PLAINS, Type.COLD, Type.CONIFEROUS, Type.SPARSE);
		BiomeDictionary.addTypes(WOODLAND, Type.FOREST);
		*/
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
	
	@SubscribeEvent
	public void onBiomeRegistry(RegistryEvent.Register<Biome> e)
	{
		e.getRegistry().registerAll(Ref.BIOMES.toArray(new Biome[0]));
	}
}