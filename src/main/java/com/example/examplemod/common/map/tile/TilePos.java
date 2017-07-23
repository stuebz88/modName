package com.example.examplemod.common.map.tile;

import net.minecraft.util.math.ChunkPos;

public class TilePos
{
	int x = 0;
	int z = 0;
	
	public TilePos(int x, int z) 
	{
		this.x = x;
		this.z = z;
	}
	
	public TilePos(ChunkPos chunkPos)
	{
		int tileZ = Math.floorDiv(chunkPos.z, 4);
		int tileX = Math.floorDiv(chunkPos.x + 2 * (tileZ % 2), 4);
		
		this.x = tileX;
		this.z = tileZ;
	}
}