package com.example.examplemod.common.core.empire;

import java.util.List;
import java.util.Map;

import com.example.examplemod.Ref;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class EmpireList extends WorldSavedData
{
	private static final String DATA_NAME = Ref.MODID + "_EmpireList";
	
	private Map<Integer, Empire> empires;
	
	public EmpireList()
	{
		super(DATA_NAME);
	}

	public EmpireList(String s)
	{
		super(s);
	}

	public Empire getEmpireByID (int empireID)
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
		NBTTagList list;
		
		for(Map.Entry<Integer, Empire> entry : empires.entrySet())
		{
			list.appendTag(entry.getValue().writeToNBT());
		}
		return nbt;
	}
	
	public Map<Integer, Empire> getEmpireMap()
	{
		return empires;
	}
	
	public boolean addEmpire(Empire empire)
	{
		markDirty();
		if (empires.containsValue(empire)) return false;
		
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
