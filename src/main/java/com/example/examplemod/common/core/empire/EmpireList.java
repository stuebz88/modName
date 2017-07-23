package com.example.examplemod.common.core.empire;

import java.util.Map;
import java.util.UUID;

import com.example.examplemod.Ref;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class EmpireList extends WorldSavedData
{
	private static final String DATA_NAME = Ref.MODID + "_EmpireList";
	
	private Map<UUID, Empire> empires;
	
	public EmpireList()
	{
		super(DATA_NAME);
	}

	public EmpireList(String s)
	{
		super(s);
	}

	public Empire getEmpireByID (UUID empireID)
	{
		return empires.get(empireID);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		NBTTagList list = new NBTTagList();

		for(Map.Entry<Integer, Empire> entry : empires.entrySet())
		{
			list.appendTag(entry.getValue().writeToNBT());
		}
		
		nbt.setTag("empires", list);
		return nbt;
	}
	
	public Map<Integer, Empire> getEmpireMap()
	{
		return empires;
	}
	
	public boolean addEmpire(Empire empire)
	{
		if (empires.containsValue(empire)) return false;
		
		markDirty();
		empires.put(empire.getID(), empire);
		return true;
	}
	
	public static EmpireList get(World world)
	{
		MapStorage storage = world.getMapStorage();
		EmpireList instance = (EmpireList)storage.getOrLoadData(EmpireList.class, DATA_NAME);
		
		if (instance == null)
		{
			System.out.println("It was null");
			instance = new EmpireList();
			storage.setData(DATA_NAME, instance);
		}
		
		return instance;
	}
}
