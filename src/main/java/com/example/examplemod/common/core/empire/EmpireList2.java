package com.example.examplemod.common.core.empire;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.example.examplemod.Ref;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class EmpireList2 extends WorldSavedData
{
	private static final String DATA_NAME = Ref.MODID + "_EmpireList";
	
	private static World world;
	private ArrayList<UUID> empireIDs = new ArrayList<UUID>();
	private ArrayList<String> empireNames = new ArrayList<String>();
	
	private Map<UUID, Empire> empires = new TreeMap<UUID, Empire>();
	
	public EmpireList2()
	{
		super(DATA_NAME);
	}

	public EmpireList2(String s)
	{
		super(s);
	}

	public static EmpireList2 get(World world)
	{
		EmpireList2.world = world;
		MapStorage storage = world.getMapStorage();
		EmpireList2 instance = (EmpireList2)storage.getOrLoadData(EmpireList2.class, DATA_NAME);
		
		if (instance == null)
		{
			System.out.println("It was null");
			instance = new EmpireList2();
			storage.setData(DATA_NAME, instance);
		}
		
		return instance;
	}
	
	public boolean addEmpire(Empire empire)
	{
		if (empires.containsValue(empire)) return false;
		
		markDirty();
		empires.put(empire.getID(), empire);
		empireNames.add(empire.getName());
		return true;
	}

	public Empire getEmpireByID (UUID empireID)
	{
		return empires.get(empireID);
	}
	
	public Empire getEmpireByName(String empireName)
	{
		if(empireNames.indexOf(empireName) == -1)
		{
			return null;
		}
		
		else
		{
			return getEmpireByID(empireIDs.get(empireNames.indexOf(empireName)));
		}
	}
	
	public ArrayList<String> getEmpireNames()
	{
		return empireNames;
	}
	
	public Map<UUID, Empire> getEmpires()
	{
		return empires;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		NBTTagList list = nbt.getTagList("empires", 10);
		
		for(int i = 0; i<list.tagCount(); i++)
		{
			NBTTagCompound empireTag_i = list.getCompoundTagAt(i);
			
			Empire.readFromNBT(empireTag_i, world);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		NBTTagList list = new NBTTagList();

		for(Map.Entry<UUID, Empire> entry : empires.entrySet())
		{
			list.appendTag(entry.getValue().writeToNBT());
		}
		int type = list.getTagType();
		System.out.println(type);
		nbt.setTag("empires", list);
		return nbt;
	}
}
