package com.example.examplemod.util;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IHasModel 
{
	default public void initModel(ModelRegistryEvent e) 
	{
		System.out.println(((IForgeRegistryEntry<?>) this).getRegistryName());
		if (this instanceof Item)
			ModelLoader.setCustomModelResourceLocation((Item) this, 0,
					new ModelResourceLocation(((IForgeRegistryEntry<?>) this).getRegistryName(), "inventory"));
		else if (this instanceof Block)
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock((Block) this), 0,
					new ModelResourceLocation(((IForgeRegistryEntry<?>) this).getRegistryName(), "inventory"));
			System.out.println("block");
		}
		else
			throw new IllegalArgumentException("No/invalid object for model");
	}
}
