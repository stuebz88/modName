package com.example.examplemod.common.map.tile;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.example.examplemod.common.core.empire.Empire;
import com.example.examplemod.common.core.empire.Resource;
import com.example.examplemod.common.core.turn.WorldTurn;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Tile 
{
	private final TilePos tilePosition;
	private final Chunk[][] chunks;
	private final World world;
	private EnumTileType tileType;
	private Map<Resource, Integer> resources = null;
	private boolean isOwned = false;
	private Map<Empire, List<Integer>> owners = null;
	
	public enum EnumTileType
	{
		PLAINS("plains"), CITY("city");
		
		private String typeName;
		
		private EnumTileType(String s)
		{
			typeName = s;
		}
	}
	
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
	
	public Tile(Chunk chunk)
	{
		this.tilePosition = new TilePos(chunk.getPos());
		this.world = chunk.getWorld();
		this.chunks = this.createChunkArray(tilePosition);
		this.tileType = this.generateTileType();
		this.generateResources();
	}
	
	@SuppressWarnings("null")
	private Chunk[][] createChunkArray(TilePos tilePosition)
	{
		Chunk[][] chunkArray = null;
		
		for(int i=0; i<4; i++)
		{
			for(int j=0; j<4; j++)
			{
				int chunkZ = tilePosition.z * 4 + j;
				int chunkX = tilePosition.x * 4 - 2 * (tilePosition.z % 2);
				
				chunkArray[i][j] = this.world.getChunkFromChunkCoords(chunkX, chunkZ);
			}
		}
		
		return chunkArray;
	}
	
	private EnumTileType generateTileType()
	{
		return EnumTileType.PLAINS;
	}
	
	private void generateResources()
	{
		switch(this.tileType)
		{
		case PLAINS: return;
		case CITY: return;
		}
	}
	
	public Chunk[][] getChunks()
	{
		return this.chunks;
	}
	
	public World getWorld()
	{
		return this.world;
	}
	
	public EnumTileType getTileType()
	{
		return this.tileType;
	}
	
	public boolean setOwnership(Empire empire, int turn) {
		return true;
	}
	
	public Map<Resource, Integer> getResources()
	{
		return this.resources;
	}
	
	public Map<Empire, List<Integer>> getAllOwners()
	{
		
		return this.owners;
	}
	
	public Empire getCurrentOwner()
	{
		Empire currentOwner = null;
		
		if(!this.isOwned) return null;
		
		for (Map.Entry<Empire, List<Integer>> entry : this.owners.entrySet())
		{
			List<Integer> entryValue = entry.getValue();
			int entryValueSize = entry.getValue().size();
			
			if(entryValueSize % 2 == 1)
			{
				if(currentOwner == null)
				{
					currentOwner = entry.getKey();
				}
				
				else if (entryValue.get(entryValueSize - 1) > owners.get(currentOwner).get(owners.get(currentOwner).size() - 1))
				{
					currentOwner = entry.getKey();
				}
			}
		}
		
		return currentOwner;
	}
	
	public TilePos getTilePos()
	{
		return this.tilePosition;
	}
	
	public int getOwnershipTime(@Nullable Empire empire)
	{
		int ownershipTime = 0;
		
		if(empire == null)
		{
			empire = this.getCurrentOwner();
		}
		
		else if(owners.get(empire) == null || this.getCurrentOwner() == null)
		{
			return -1;
		}
		
		List<Integer> values = owners.get(empire);
		ownershipTime = values.size() % 2 == 0 ? 0 : WorldTurn.get(this.world).getTurn() - values.get(values.size() - 1);
			
		for (int i=0; i < Math.floorDiv(values.size(),2); i++)
		{
			ownershipTime += (values.get(i * 2 + 1) - values.get(i * 2));
		}
			
		return ownershipTime;
	}
}
