package com.example.examplemod;

import javax.annotation.Nonnull;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldTypeMod extends WorldType
{
	//public static ChunkProviderMod;
	
	public WorldTypeMod(final String name)
	{
		super(name);
	}
	
	@Nonnull
	public IChunkGenerator getChunkGenerator(@Nonnull final World world, final String generatorOptions)
	{
		return null;
	}
}