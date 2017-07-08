package com.example.examplemod.common.blocks;

import com.example.examplemod.Ref;
import com.example.examplemod.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockModBanner extends Block implements IHasModel
{
	public BlockModBanner() 
	{
		super(Material.WOOD);
		this.setSoundType(SoundType.CLOTH);
		this.setTickRandomly(true);
		this.setHardness(0.3f);
		this.setRegistryName(Ref.MODID + ":banner");
		this.setUnlocalizedName(Ref.MODID + ".banner");
		this.setCreativeTab(Ref.TAB);
		Ref.BLOCKS.add(this);
	}
}
