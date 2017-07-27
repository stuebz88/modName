package com.example.examplemod.common.map.tile;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class TilePos implements Comparable<TilePos>
{
	public int x = 0;
	public int z = 0;
	
	public TilePos(int x, int z) 
	{
		this.x = x;
		this.z = z;
	}
	
	@Override
	public int compareTo(TilePos comparing)
	{
		int sum1 = this.x+this.z;
		int sum2 = comparing.x+comparing.z;
		if(sum1<sum2)
		{
			return -1;
		}
		else if(sum1>sum2)
		{
			return 1;
		}
		else
		{
			if(this.x<comparing.x)
			{
				return -1;
			}
			else if(this.x>comparing.x)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
	}
	
	public boolean equals(TilePos comparing)
	{
		return this.x == comparing.x && this.z == comparing.z;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof TilePos)) return false;
		TilePos tp = (TilePos)o;
		return this.x==tp.x && this.z==tp.z;
	}
	
	public TilePos(ChunkPos chunkPos)
	{
		int tileZ = Math.floorDiv(chunkPos.z, 4);
		int tileX = Math.floorDiv(chunkPos.x + 2 * (tileZ % 2), 4);
		
		this.x = tileX;
		this.z = tileZ;
	}
	
	public TilePos(BlockPos blockPos)
	{
		this(new ChunkPos(blockPos));
	}
}