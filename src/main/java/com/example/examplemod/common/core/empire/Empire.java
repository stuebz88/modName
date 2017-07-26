package com.example.examplemod.common.core.empire;

import java.util.Map;
import java.util.UUID;

import com.example.examplemod.common.caps.empire.IEmpire;
import com.example.examplemod.common.caps.turn.EmpireTurn;
import com.example.examplemod.common.core.city.City;
import com.example.examplemod.common.core.turn.WorldTurn;
import com.example.examplemod.common.map.tile.Tile;
import com.example.examplemod.common.map.tile.TilePos;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Empire
{
	private UUID empireID;
	private boolean exists = false;
	private String empireName = "";
	private World empireWorld = null;
	private EmpireTurn empireTurn = null;
	private Map<Integer, String> playerList = null;
	private Map<UUID, Long> resources = null;
	private Map<TilePos, Integer> territory = null;
	private Map<UUID, Integer> vassalList = null;
	private Map<UUID, Integer> cityList = null;
	
	public Empire(String empireName, World world, EntityPlayer player, String playerTitle, Map<UUID, Long> startingResources, Tile tile)
	{
		
		this.empireID = UUID.randomUUID();
		this.exists = true;
		int turn = WorldTurn.get(world).getTurn();
		
		this.empireName = empireName;
		this.empireWorld = world;
		this.empireTurn = new EmpireTurn(world, this.empireID);
		this.playerList.put(player.getEntityId(), playerTitle);
		this.resources.putAll(startingResources);
		this.territory.put(tile.getTilePos(), turn);
		
		//EmpireList.get(world).addEmpire(this);
		tile.setOwnership(this, turn);
	}
	
	public boolean addCity(City city)
	{
		if(!this.canAddCity()) return false;
		WorldTurn worldTurn = WorldTurn.get(this.empireWorld);
		
		cityList.put(city.getID(), worldTurn.getTurn());
		return true;
	}
	
	public boolean addVassal(Empire empire)
	{
		if(!this.canAddVassal(empire.getEmpireValue())) return false;
		WorldTurn worldTurn = WorldTurn.get(this.empireWorld);
		
		vassalList.put(empire.getUUID(),worldTurn.getTurn());
		return true;
	}
	
	public void addResource(Resource resource, long amount)
	{
		if (this.resources.putIfAbsent(resource.getID(), amount) != null)
		{
			this.resources.put(resource.getID(), this.resources.get(resource) + amount);
		}
	}
	
	public boolean canAddVassal(int empireValue)
	{
		return false;
	}
	
	public boolean canAddCity()
	{
		return true;
	}
	
	public void changeName(String name)
	{
		this.empireName = name;
	}
	
	public Map<UUID, Integer> getCities()
	{
		return this.cityList;
	}
	
	public int getMaximumVassalSize()
	{
		return 0;
	}
	
	public int getEmpireValue()
	{
		return 0;
	}
	
	public boolean empireExists()
	{
		return this.exists;
	}
	
	public long getResourceValue(Resource resource)
	{
		return this.resources.get(resource) != null ? this.resources.get(resource) : 0L;
	}
	
	public EmpireTurn getEmpireTurn()
	{
		return this.empireTurn;
	}
	
	public Map<UUID, Integer> getVassals()
	{
		return this.vassalList;
	}
	
	public String getName()
	{
		return this.empireName;
	}
	
	public UUID getUUID()
	{
		return this.empireID;
	}
	
	public Map<Integer, String> getPlayers()
	{
		return this.playerList;
	}
	
	public void destroyEmpire()
	{
		this.exists = false;
	}
	
	public void rebirthEmpire()
	{
		this.exists = true;
	}
	
	public NBTTagCompound writeToNBT ()
	{
		NBTTagCompound nbt;
		
		nbt.setLong("empireID1", this.empireID.getMostSignificantBits());
		nbt.setLong("empireID2", this.empireID.getLeastSignificantBits());
		nbt.setBoolean("empireExists", this.exists);
		nbt.setString("empireName", this.empireName);
		nbt = this.empireTurn.writeToNBT(nbt, externalID);
		nbt.setInteger("playerListSize", this.playerList.size());
		
		if(this.playerList.size() > 0)
		{
			int i = 0;
			for(Map.Entry<Integer, String> entry : this.playerList.entrySet())
			{
				String playerNumber = "#player_" + i;
				String playerPosition = "@player_" + i;
				nbt.setInteger(playerNumber, entry.getKey());
				nbt.setString(playerPosition, entry.getValue());
				
				i++;
			}
		}
		
		nbt.setInteger("resourceListSize", this.resources.size());
		
		if(this.resources.size() > 0)
		{
			int i = 0;
			for(Map.Entry<UUID, Long> entry : this.resources.entrySet())
			{
				String resourceID = "#resource_" + i;
				nbt = entry.getKey().writeToNBT(nbt, resourceID);
				nbt.setLong(resourceID + "_amount", entry.getValue());
				
				i++;
			}
		}
		
		nbt.setInteger("territorySize", this.territory.size());
		
		if(this.territory.size() > 0)
		{
			int i = 0;
			for(Map.Entry<Tile, Integer> entry : this.territory.entrySet())
			{
				String tileID = "#tile_" + i;
				nbt = entry.getKey().writeToNBT(nbt, tileID);
				nbt.setLong(tileID + "_length", entry.getValue());
				
				i++;
			}
		}
		
		nbt.setInteger("vassalListSize", this.vassalList.size());
		
		if(this.vassalList.size() > 0)
		{
			int i = 0;
			for(Map.Entry<Empire, Integer> entry : this.vassalList.entrySet())
			{
				String vassalID = "#vassal_" + i;
				nbt = entry.getKey().writeToNBT(nbt, vassalID);
				nbt.setInteger(vassalID + "_length", entry.getValue());
				
				i++;
			}
		}
		
		nbt.setInteger("cityListSize", this.cityList.size());
		
		if(this.cityList.size() > 0)
		{
			int i = 0;
			for(Map.Entry<City, Integer> entry : this.cityList.entrySet())
			{
				String cityID = "#city_" + i;
				nbt = entry.getKey().writeToNBT(nbt, cityID);
				nbt.setInteger(cityID + "_length", entry.getValue());
				
				i++;
			}
		}
		
		return nbt;
	}
	
	public static void readFromNBT (NBTTagCompound nbt)
	{
		
	}
}
