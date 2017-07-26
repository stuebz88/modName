package com.example.examplemod.common.blocks;

import com.example.examplemod.Ref;
import com.example.examplemod.common.map.tile.Tile;
import com.example.examplemod.common.map.tile.TileList;
import com.example.examplemod.common.map.tile.TilePos;
import com.example.examplemod.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		TileList tileList = TileList.get(worldIn);
		Tile placedOn = tileList.getTileByPos(new TilePos(pos));
    }
}