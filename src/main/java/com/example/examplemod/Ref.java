package com.example.examplemod;

import java.util.ArrayList;
import java.util.List;

import com.example.examplemod.init.ModRegistry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public class Ref 
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final List<IRecipe> RECIPES = new ArrayList<IRecipe>();
	
	public static final String MODID = "examplemod";
    public static final String NAME = "ModName";
    public static final String VERSION = "1.0";
    public static final String CLIENT_PROXY = "com.example.examplemod.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.example.examplemod.proxy.CommonProxy";
    
    //defines the mod-specific creative tab
    public static final CreativeTabs TAB = new CreativeTabs(MODID)
	{
		@Override
		public ItemStack getTabIconItem() 
		{
			//place item type for the creative tab logo
			return new ItemStack(ModRegistry.MODBANNER);
		}
	};
}