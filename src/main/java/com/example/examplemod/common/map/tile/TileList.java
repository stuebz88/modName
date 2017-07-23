package com.example.examplemod.common.map.tile;

import java.util.Map;
import java.util.TreeMap;

import com.example.examplemod.Ref;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class TileList extends WorldSavedData
{
private static final String DATA_NAME = Ref.MODID + "_EmpireList";
	
	private TreeMap<TilePos, Tile> tiles = new TreeMap<TilePos, Tile>();
	private static World world;
	
	public TileList()
	{
		super(DATA_NAME);
	}

	public TileList(String s)
	{
		super(s);
	}

	public Tile getTileByPos(TilePos pos)
	{
		return tiles.get(pos);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		NBTTagList list = (NBTTagList)nbt.getTag("tiles");
		tiles = new TreeMap<TilePos,Tile>();
		
		for(int i=0; i<list.tagCount(); i++)
		{
			NBTTagCompound entry = list.getCompoundTagAt(i);
			Tile tile = Tile.readFromNBT(entry, world);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		NBTTagList list = new NBTTagList();
		
		for(Map.Entry<TilePos, Tile> entry : tiles.entrySet())
		{
			list.appendTag(entry.getValue().writeToNBT(new NBTTagCompound()));
		}
		
		nbt.setTag("tiles", list);
		return nbt;
	}
	
	public Map<TilePos, Tile> getTileMap()
	{
		return tiles;
	}
	
	public boolean addTile(Tile tile)
	{
		if (tiles.containsValue(tile)) return false;
		markDirty();
		tiles.put(tile.getTilePos(), tile);
		return true;
	}
	
	public static TileList get(World world)
	{
		MapStorage storage = world.getMapStorage();
		TileList instance = (TileList)storage.getOrLoadData(TileList.class, DATA_NAME);
		TileList.world = world;
		
		if (instance == null)
		{
			System.out.println("It was null");
			instance = new TileList();
			storage.setData(DATA_NAME, instance);
		}
		
		return instance;
	}
}