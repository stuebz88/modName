package com.example.examplemod.common.map.tile;

import java.util.ArrayList;

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
	
	public ChunkPos getIndexChunkPos()
	{
		int chunkZ = this.z * 4;
		int chunkX = this.x * 4 - 2 * (this.z % 2);
		return new ChunkPos(chunkX, chunkZ);
	}
	
	public ArrayList<TilePos> getTilesInRange(int range)
	{
		if(range < 1) return null;
		
		ArrayList<TilePos> tilesInRange = new ArrayList<TilePos>();
		ChunkPos indexChunkPos = this.getIndexChunkPos();
		
		for(int i = 0; i <= range ; i++)
		{
			for(int j = 0; j <= range ; j++)
			{
				int posposChunkX = indexChunkPos.x + i * 4;
				int posposChunkZ = indexChunkPos.z + j * 4;
				int posnegChunkX = indexChunkPos.x + i * 4;
				int posnegChunkZ = indexChunkPos.z + j * -4;
				int negposChunkX = indexChunkPos.x + i * -4;
				int negposChunkZ = indexChunkPos.z + j * 4;
				int negnegChunkX = indexChunkPos.x + i * -4;
				int negnegChunkZ = indexChunkPos.z + j * -4;
				
				TilePos posposTile = new TilePos(new ChunkPos(posposChunkX, posposChunkZ));
				TilePos posnegTile = new TilePos(new ChunkPos(posnegChunkX, posnegChunkZ));
				TilePos negposTile = new TilePos(new ChunkPos(negposChunkX, negposChunkZ));
				TilePos negnegTile = new TilePos(new ChunkPos(negnegChunkX, negnegChunkZ));
				
				if(!tilesInRange.contains(posposTile)) tilesInRange.add(posposTile);
				if(!tilesInRange.contains(posnegTile)) tilesInRange.add(posnegTile);
				if(!tilesInRange.contains(negposTile)) tilesInRange.add(negposTile);
				if(!tilesInRange.contains(negnegTile)) tilesInRange.add(negnegTile);
			}
		}
		
		return tilesInRange;
	}
	
	public static ArrayList<TilePos> getTilesInRangeOf(TilePos indexTilePos, int range)
	{
		if(range < 1) return null;
		
		ArrayList<TilePos> tilesInRange = new ArrayList<TilePos>();
		ChunkPos indexChunkPos = indexTilePos.getIndexChunkPos();
		
		for(int i = 0; i <= range ; i++)
		{
			for(int j = 0; j <= range ; j++)
			{
				int posposChunkX = indexChunkPos.x + i * 4;
				int posposChunkZ = indexChunkPos.z + j * 4;
				int posnegChunkX = indexChunkPos.x + i * 4;
				int posnegChunkZ = indexChunkPos.z + j * -4;
				int negposChunkX = indexChunkPos.x + i * -4;
				int negposChunkZ = indexChunkPos.z + j * 4;
				int negnegChunkX = indexChunkPos.x + i * -4;
				int negnegChunkZ = indexChunkPos.z + j * -4;
				
				TilePos posposTile = new TilePos(new ChunkPos(posposChunkX, posposChunkZ));
				TilePos posnegTile = new TilePos(new ChunkPos(posnegChunkX, posnegChunkZ));
				TilePos negposTile = new TilePos(new ChunkPos(negposChunkX, negposChunkZ));
				TilePos negnegTile = new TilePos(new ChunkPos(negnegChunkX, negnegChunkZ));
				
				if(!tilesInRange.contains(posposTile)) tilesInRange.add(posposTile);
				if(!tilesInRange.contains(posnegTile)) tilesInRange.add(posnegTile);
				if(!tilesInRange.contains(negposTile)) tilesInRange.add(negposTile);
				if(!tilesInRange.contains(negnegTile)) tilesInRange.add(negnegTile);
			}
		}
		
		return tilesInRange;
	}
	
	public static ArrayList<TilePos> getTilesInRangeOf(ChunkPos indexChunkPos, int range)
	{
		if(range < 1) return null;
		
		ArrayList<TilePos> tilesInRange = new ArrayList<TilePos>();
		
		for(int i = 0; i <= range ; i++)
		{
			for(int j = 0; j <= range ; j++)
			{
				int posposChunkX = indexChunkPos.x + i * 4;
				int posposChunkZ = indexChunkPos.z + j * 4;
				int posnegChunkX = indexChunkPos.x + i * 4;
				int posnegChunkZ = indexChunkPos.z + j * -4;
				int negposChunkX = indexChunkPos.x + i * -4;
				int negposChunkZ = indexChunkPos.z + j * 4;
				int negnegChunkX = indexChunkPos.x + i * -4;
				int negnegChunkZ = indexChunkPos.z + j * -4;
				
				TilePos posposTile = new TilePos(new ChunkPos(posposChunkX, posposChunkZ));
				TilePos posnegTile = new TilePos(new ChunkPos(posnegChunkX, posnegChunkZ));
				TilePos negposTile = new TilePos(new ChunkPos(negposChunkX, negposChunkZ));
				TilePos negnegTile = new TilePos(new ChunkPos(negnegChunkX, negnegChunkZ));
				
				if(!tilesInRange.contains(posposTile)) tilesInRange.add(posposTile);
				if(!tilesInRange.contains(posnegTile)) tilesInRange.add(posnegTile);
				if(!tilesInRange.contains(negposTile)) tilesInRange.add(negposTile);
				if(!tilesInRange.contains(negnegTile)) tilesInRange.add(negnegTile);
			}
		}
		
		return tilesInRange;
	}
}