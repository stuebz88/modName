package com.example.examplemod.common.map.tile;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import com.example.examplemod.common.core.empire.Empire;
import com.example.examplemod.common.core.turn.WorldTurn;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Tile 
{
	private final World world;
	private final TilePos tilePosition;
	private final ChunkPos[][] chunks;
	private TileEnum.Biome biome;
	private Map<UUID, Integer> resources = null;
	private boolean isOwned = false;
	private ArrayList<UUID> owners = new ArrayList<UUID>();
	private ArrayList<ArrayList<Integer>> ownersTime = new ArrayList<ArrayList<Integer>>();
	
	public Tile(Chunk chunk)
	{
		this.tilePosition = new TilePos(chunk.getPos());
		this.world = chunk.getWorld();
		this.chunks = this.createChunkArray();
		this.biome = generateTileBiome();
		this.generateTileBiome();
		TileList.get(world).addTile(this);
	}
	
	private Tile(World world, int x, int z, TileEnum.Biome biome, boolean isOwned, ArrayList<UUID> owners, ArrayList<ArrayList<Integer>> ownersTime)
	{
		this.world = world;
		this.tilePosition = new TilePos(x,z);
		this.chunks = createChunkArray();
		this.biome = biome;
		this.isOwned = isOwned;
		this.owners = owners;
		this.ownersTime = ownersTime;
		TileList.get(world).addTile(this);
	}
	
	private ChunkPos[][] createChunkArray()
	{
		ChunkPos[][] chunkArray = new ChunkPos[4][4];
		
		for(int i=0; i<4; i++)
		{
			for(int j=0; j<4; j++)
			{
				int chunkZ = this.tilePosition.z * 4 + j;
				int chunkX = this.tilePosition.x * 4 - 2 * (this.tilePosition.z % 2);
				
				chunkArray[i][j] = new ChunkPos(chunkX, chunkZ);
			}
		}
		
		return chunkArray;
	}
	
	public static ChunkPos[][] createChunkArray(TilePos tilePosition)
	{
		ChunkPos[][] chunkArray = new ChunkPos[4][4];
		
		for(int i=0; i<4; i++)
		{
			for(int j=0; j<4; j++)
			{
				int chunkZ = tilePosition.z * 4 + j;
				int chunkX = tilePosition.x * 4 - 2 * (tilePosition.z % 2);
				
				chunkArray[i][j] = new ChunkPos(chunkX, chunkZ);
			}
		}
		
		return chunkArray;
	}
	
	private TileEnum.Biome generateTileBiome()
	{
		int[] freq = new int[167];
		int max = 0;
		int maxBiome = 0;
		for(int i=0; i<4; i++)
		{
			for(int j=0; j<4; j++)
			{
				try 
				{
					byte[] currChunkBiomes = 
					Minecraft.getMinecraft().world.getChunkFromChunkCoords(chunks[i][j].x,chunks[i][j].z).getBiomeArray();
					
					for(int k=0; k<64; k++)
					{
						freq[currChunkBiomes[k]]++;
						if (freq[currChunkBiomes[k]]>max)
						{
							max = freq[currChunkBiomes[k]];
							maxBiome = currChunkBiomes[k];
						}
					}
				} 
				// This  is catching ArrayIndexOutOfBoundsException for using -1 somehow
				catch (Exception e) {}
			}
		}
		return maxBiome==1 ? TileEnum.Biome.PLAINS : TileEnum.Biome.NONE;
	}
	
	public ChunkPos[][] getChunks()
	{
		return this.chunks;
	}
	
	public World getWorld()
	{
		return this.world;
	}
	
	public TileEnum.Biome getTileBiome()
	{
		return this.biome;
	}
	
	public boolean setOwnership(Empire empire, int turn) {
		return true;
	}
	
	public Map<UUID, Integer> getResources()
	{
		return this.resources;
	}
	
	public ArrayList<UUID> getAllOwners()
	{
		return this.owners;
	}
	
	public UUID getCurrentOwner()
	{
		return isOwned ? owners.get(owners.size()-1) : null;
	}
	
	public TilePos getTilePos()
	{
		return this.tilePosition;
	}
	
	public int getOwnershipTime(@Nullable UUID empire)
	{
		int ownershipTime = 0;
		
		if(empire == null)
		{
			empire = this.getCurrentOwner();
		}
		
		else if(owners.indexOf(empire) == -1 || this.getCurrentOwner() == null)
		{
			return -1;
		}
		
		ArrayList<Integer> values = ownersTime.get(owners.indexOf(empire));
		ownershipTime = values.size() % 2 == 0 ? 0 : WorldTurn.get(this.world).getTurn() - values.get(values.size() - 1);
			
		for (int i=0; i < Math.floorDiv(values.size(),2); i++)
		{
			ownershipTime += (values.get(i * 2 + 1) - values.get(i * 2));
		}
			
		return ownershipTime;
	}
	
	public static Tile readFromNBT(NBTTagCompound nbt, World world)
	{
		int x = nbt.getInteger("x");
		int z = nbt.getInteger("z");
		TileEnum.Biome tileType = TileEnum.Biome.values()[nbt.getInteger("type")];
		boolean isOwned = nbt.getBoolean("isOwned");
		int owners_size = nbt.getInteger("owners.size");
		
		ArrayList<UUID> owners = new ArrayList<UUID>();
		ArrayList<ArrayList<Integer>> ownersTime = new ArrayList<ArrayList<Integer>>();
		
		for(int i=0; i<owners_size; i++)
		{
			String ownerID = "owner_"+i;
			UUID owner = new UUID(nbt.getLong(ownerID+"_ID1"),nbt.getLong(ownerID+"_ID2"));
			owners.add(owner);
			
			ArrayList<Integer> ownerTime = new ArrayList<Integer>();
			for(int j : nbt.getIntArray(ownerID+"_time"))
			{
				ownerTime.add(j);
			}
			ownersTime.add(ownerTime);
		}
		return new Tile(world,x,z,tileType,isOwned,owners,ownersTime);
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("x",this.tilePosition.x);
		nbt.setInteger("z",this.tilePosition.z);
		nbt.setInteger("type",this.biome.ordinal());
		nbt.setBoolean("isOwned",this.isOwned);
		nbt.setInteger("owners.size",this.owners.size());

		for (int i=0; i<owners.size(); i++)
		{
			String ownerID = "owner_"+i;
			nbt.setLong(ownerID+"_ID1",owners.get(i).getMostSignificantBits());
			nbt.setLong(ownerID+"_ID1",owners.get(i).getLeastSignificantBits());
			int[] ownersTimeInt = new int[ownersTime.get(i).size()];
			for (int j=0; j<ownersTimeInt.length; j++)
			{
				ownersTimeInt[j] = ownersTime.get(i).get(j);
			}
			nbt.setIntArray(ownerID+"_time", ownersTimeInt);
		}
		return nbt;
	}
}