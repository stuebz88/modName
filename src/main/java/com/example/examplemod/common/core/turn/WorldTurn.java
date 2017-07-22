package com.example.examplemod.common.core.turn;

import com.example.examplemod.Ref;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class WorldTurn extends WorldSavedData
{
	private static final String DATA_NAME = Ref.MODID + "_WorldTurn";
	
	private int worldTurns = 1;
	
	public WorldTurn() 
	{
		super(DATA_NAME);
	}

	public WorldTurn(String s)
	{
		super(s);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		worldTurns = nbt.getInteger("worldTurns");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		nbt.setInteger("worldTurns", worldTurns);
		return nbt;
	}
	
	public int getTurn()
	{
		return worldTurns;
	}
	
	public int advanceTurn()
	{
		markDirty();
		return worldTurns++;
	}
	
	public static WorldTurn get(World world)
	{
		MapStorage storage = world.getMapStorage();
		System.out.println(storage);
		WorldTurn instance = (WorldTurn)storage.getOrLoadData(WorldTurn.class, DATA_NAME);
		
		if (instance == null)
		{
			System.out.println("It was null");
			instance = new WorldTurn();
			storage.setData(DATA_NAME, instance);
		}
		
		return instance;
	}
}