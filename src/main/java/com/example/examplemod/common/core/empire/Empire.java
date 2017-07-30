package com.example.examplemod.common.core.empire;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.example.examplemod.common.caps.intelligence.EmpireIntelligence;
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
	private TreeMap<UUID, Integer> cities = new TreeMap<UUID, Integer>();
	private UUID empireID;
	private EmpireIntelligence empireIntelligence = new EmpireIntelligence();
	private String empireName = "";
	private EmpireTurn empireTurn = null;
	private World empireWorld;
	private boolean exists = false;
	private TreeMap<UUID, String> players = new TreeMap<UUID, String>();
	private TreeMap<UUID, Long> resources = new TreeMap<UUID, Long>();
	private TreeMap<TilePos, Integer> tiles = new TreeMap<TilePos, Integer>();
	private TreeMap<UUID, Integer> vassals = new TreeMap<UUID, Integer>();
	
	public Empire(String empireName, World world, EntityPlayer player, String playerTitle, Map<UUID, Long> startingResources, Tile tile)
	{
		
		this.empireID = UUID.randomUUID();
		this.exists = true;
		int turn = WorldTurn.get(world).getTurn();
		
		this.empireName = empireName;
		this.empireWorld = world;
		this.empireTurn = new EmpireTurn(world, this.empireID);
		this.players.put(player.getUniqueID(), playerTitle);
		this.resources.putAll(startingResources);
		this.tiles.put(tile.getTilePos(), turn);
		
		EmpireList.get(world).addEmpire(this);
		tile.setOwnership(empireID, turn);
	}
	
	private Empire(UUID empireID, boolean empireExists, String empireName, EmpireTurn empireTurn, TreeMap<UUID, String> players, TreeMap<UUID, Long> resources, TreeMap<TilePos, Integer> tiles, TreeMap<UUID, Integer> vassals, TreeMap<UUID, Integer> cities, World world)
	{
		this.empireID = empireID;
		this.exists = empireExists;
		this.empireName = empireName;
		this.empireWorld = world;
		this.empireTurn = empireTurn;
		this.players = players;
		this.resources = resources;
		this.tiles = tiles;
		this.vassals = vassals;
		this.cities = cities;
		
		EmpireList.get(world).addEmpire(this);
	}
	
	public static void readFromNBT (NBTTagCompound nbt, World world)
	{
		UUID empireID = new UUID(nbt.getLong("empireID1"), nbt.getLong("empireID2"));
		boolean empireExists = nbt.getBoolean("empireExists");
		String empireName = nbt.getString("empireName");
		EmpireTurn empireTurn = EmpireTurn.readFromNBT(nbt);
		
		int playerListSize = nbt.getInteger("playerListSize");
		TreeMap<UUID, String> players = new TreeMap<UUID, String>();
		for(int i = 0; i < playerListSize; i++)
		{
			String playerString = "player" + i + "_ID";
			UUID playerID = new UUID(nbt.getLong(playerString + 1), nbt.getLong(playerString + 2));
			players.put(playerID, nbt.getString("@player" + i));
		}
		
		int resourceListSize = nbt.getInteger("resourceListSize");
		TreeMap<UUID, Long> resources = new TreeMap<UUID, Long>();
		for(int i = 0; i < resourceListSize; i++)
		{
			UUID resourceID = new UUID(nbt.getLong("resource" + i + "_ID1"), nbt.getLong("resource" + i + "_ID2"));
			resources.put(resourceID, nbt.getLong("resource" + i + "_amount"));
		}
		
		int territorySize = nbt.getInteger("territorySize");
		TreeMap<TilePos, Integer> tiles = new TreeMap<TilePos, Integer>();
		for(int i = 0; i < territorySize; i++)
		{
			String tileID = "#tile_" + i;
			TilePos tilePos = new TilePos(nbt.getInteger(tileID + "X"), nbt.getInteger(tileID + "Z"));
			tiles.put(tilePos, nbt.getInteger(tileID + "_length"));
		}
		
		int vassalListSize = nbt.getInteger("vassalListSize");
		TreeMap<UUID, Integer> vassals = new TreeMap<UUID, Integer>();
		for(int i = 0; i < vassalListSize; i++)
		{
			String vassalID = "#vassal_" + i;
			UUID vassalUUID = new UUID(nbt.getLong(vassalID + "_ID1"), nbt.getLong(vassalID + "_ID2"));
			vassals.put(vassalUUID, nbt.getInteger(vassalID + "_length"));
		}
		
		int cityListSize = nbt.getInteger("cityListSize");
		TreeMap<UUID, Integer> cities = new TreeMap<UUID, Integer>();
		for(int i = 0; i < cityListSize; i++)
		{
			String cityID = "#city_" + i;
			UUID cityUUID = new UUID(nbt.getLong(cityID + "_ID1"), nbt.getLong(cityID + "_ID2"));
			cities.put(cityUUID, nbt.getInteger(cityID + "_length"));
		}
		
		new Empire(empireID, empireExists, empireName, empireTurn, players, resources, tiles, vassals, cities, world);
	}
	
	public boolean addCity(City city)
	{
		if(!this.canAddCity()) return false;
		WorldTurn worldTurn = WorldTurn.get(this.empireWorld);
		
		cities.put(city.getID(), worldTurn.getTurn());
		return true;
	}
	
	public void addResource(Resource resource, long amount)
	{
		if (this.resources.putIfAbsent(resource.getID(), amount) != null)
		{
			this.resources.put(resource.getID(), this.resources.get(resource) + amount);
		}
	}
	
	public boolean addVassal(Empire empire)
	{
		if(!this.canAddVassal(empire.getEmpireValue())) return false;
		WorldTurn worldTurn = WorldTurn.get(this.empireWorld);
		
		vassals.put(empire.getID(),worldTurn.getTurn());
		return true;
	}
	
	public boolean canAddCity()
	{
		return true;
	}
	
	public boolean canAddVassal(int empireValue)
	{
		return false;
	}
	
	public void changeName(String name)
	{
		this.empireName = name;
	}
	
	public void destroyEmpire()
	{
		this.exists = false;
	}
	
	public boolean empireExists()
	{
		return this.exists;
	}
	
	public TreeMap<UUID, Integer> getCities()
	{
		return this.cities;
	}
	
	public EmpireIntelligence getEmpireIntelligence()
	{
		return this.empireIntelligence;
	}
	
	public EmpireTurn getEmpireTurn()
	{
		return this.empireTurn;
	}
	
	public int getEmpireValue()
	{
		return 0;
	}
	
	public UUID getID()
	{
		return this.empireID;
	}
	
	public int getMaximumVassalSize()
	{
		return 0;
	}
	
	public String getName()
	{
		return this.empireName;
	}
	
	public TreeMap<UUID, String> getPlayers()
	{
		return this.players;
	}
	
	public long getResourceValue(Resource resource)
	{
		return this.resources.get(resource) != null ? this.resources.get(resource) : 0L;
	}
	
	public TreeMap<UUID, Integer> getVassals()
	{
		return this.vassals;
	}
	
	public void rebirthEmpire()
	{
		this.exists = true;
	}

	public NBTTagCompound writeToNBT ()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setLong("empireID1", this.empireID.getMostSignificantBits());
		nbt.setLong("empireID2", this.empireID.getLeastSignificantBits());
		nbt.setBoolean("empireExists", this.exists);
		nbt.setString("empireName", this.empireName);
		nbt = this.empireTurn.writeToNBT(nbt);
		nbt.setInteger("playerListSize", this.players.size());
		
		if(this.players.size() > 0)
		{
			int i = 0;
			for(Map.Entry<UUID, String> entry : this.players.entrySet())
			{
				String playerString = "player_" + i + "_ID";
				String playerPosition = "@player_" + i;
				nbt.setLong(playerString + 1, entry.getKey().getMostSignificantBits());
				nbt.setLong(playerString + 2, entry.getKey().getLeastSignificantBits());
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
				String resourceID = "resource" + i;
				
				nbt.setLong(resourceID + "_ID1", entry.getKey().getMostSignificantBits());
				nbt.setLong(resourceID + "_ID2", entry.getKey().getLeastSignificantBits());
				nbt.setLong(resourceID + "_amount" + i, entry.getValue());
				
				i++;
			}
		}
		
		nbt.setInteger("territorySize", this.tiles.size());
		
		if(this.tiles.size() > 0)
		{
			int i = 0;
			for(Map.Entry<TilePos, Integer> entry : this.tiles.entrySet())
			{
				String tileID = "#tile_" + i;
				nbt.setInteger(tileID + "X", entry.getKey().x);
				nbt.setInteger(tileID + "Z", entry.getKey().z);
				nbt.setInteger(tileID + "_length", entry.getValue());
				
				i++;
			}
		}
		
		nbt.setInteger("vassalListSize", this.vassals.size());
		
		if(this.vassals.size() > 0)
		{
			int i = 0;
			for(Map.Entry<UUID, Integer> entry : this.vassals.entrySet())
			{
				String vassalID = "#vassal_" + i;
				nbt.setLong(vassalID + "_ID1", entry.getKey().getMostSignificantBits());
				nbt.setLong(vassalID + "_ID2", entry.getKey().getLeastSignificantBits());
				nbt.setInteger(vassalID + "_length", entry.getValue());
				
				i++;
			}
		}
		
		nbt.setInteger("cityListSize", this.cities.size());
	
		if(this.cities.size() > 0)
		{
			int i = 0;
			for(Map.Entry<UUID, Integer> entry : this.cities.entrySet())
			{
				String cityID = "#city_" + i;
				
				nbt.setLong(cityID + "_ID1", entry.getKey().getMostSignificantBits());
				nbt.setLong(cityID + "_ID2", entry.getKey().getLeastSignificantBits());
				nbt.setInteger(cityID + "_length", entry.getValue());
				
				i++;
			}
		}
		
		return nbt;
	}
}