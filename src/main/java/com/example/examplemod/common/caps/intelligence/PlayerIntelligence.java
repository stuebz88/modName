package com.example.examplemod.common.caps.intelligence;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Nullable;

import com.example.examplemod.common.caps.empire.CapPlayerEmpire;
import com.example.examplemod.common.core.empire.Empire;
import com.example.examplemod.common.core.empire.EmpireList;
import com.example.examplemod.common.core.turn.WorldTurn;
import com.example.examplemod.common.map.tile.TilePos;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class PlayerIntelligence implements IIntelligence
{
	private TreeMap<UUID, Integer> metEmpires = new TreeMap<UUID, Integer>();
	EntityPlayer player;
	private UUID playerEmpireID;
	private ArrayList<TilePos> seenTiles = new ArrayList<TilePos>();
	private ArrayList<TilePos> tilesInView = new ArrayList<TilePos>();

	public PlayerIntelligence(@Nullable EntityPlayer player)
	{
		this.player = player;
		this.playerEmpireID = player.getCapability(CapPlayerEmpire.EMPIRE_CAP, null).getUUID();
		this.tilesInView = TilePos.getTilesInRangeOf(new ChunkPos(player.chunkCoordX, player.chunkCoordZ), 1);
		this.seenTiles = this.tilesInView;
	}
	
	@Override
	public void addMetEmpire(UUID empireID)
	{
		World world = player.getEntityWorld();
		WorldTurn worldTurn = WorldTurn.get(world);
		Empire playerEmpire = EmpireList.get(world).getEmpireByID(playerEmpireID);
		
		metEmpires.put(empireID, worldTurn.getTurn());
		playerEmpire.getEmpireIntelligence().addMetEmpire(empireID);
	}


	@Override
	public void addMetEmpires(ArrayList<UUID> empires)
	{
		World world = this.player.getEntityWorld();
		int worldTurn = WorldTurn.get(world).getTurn();
		Empire playerEmpire = EmpireList.get(world).getEmpireByID(playerEmpireID);
		
		for(int i = 0; i < empires.size(); i++)
		{
			if(!this.metEmpires.containsKey(empires.get(i))) this.metEmpires.put(empires.get(i), worldTurn);
		}
		
		playerEmpire.getEmpireIntelligence().addMetEmpires(empires);
	}

	@Override
	public void addSeenTile(TilePos tilePos)
	{
		World world = this.player.getEntityWorld();
		Empire playerEmpire = EmpireList.get(world).getEmpireByID(playerEmpireID);
		
		if(!this.seenTiles.contains(tilePos)) this.seenTiles.add(tilePos);
		playerEmpire.getEmpireIntelligence().addSeenTile(tilePos);
	}
	
	@Override
	public void addSeenTiles(ArrayList<TilePos> tiles)
	{
		World world = this.player.getEntityWorld();
		Empire playerEmpire = EmpireList.get(world).getEmpireByID(playerEmpireID);
		
		for(int i = 0; i < tiles.size(); i++)
		{
			if(!this.seenTiles.contains(tiles.get(i))) this.seenTiles.add(tiles.get(i));
	
			else tiles.remove(i);	
		}
		
		playerEmpire.getEmpireIntelligence().addSeenTiles(tiles);
	}
	
	@Override
	public void addTileInView(TilePos tilePos)
	{
		World world = this.player.getEntityWorld();
		Empire playerEmpire = EmpireList.get(world).getEmpireByID(playerEmpireID);
		
		if(!this.tilesInView.contains(tilePos)) 
		{
			this.tilesInView.add(tilePos);
			playerEmpire.getEmpireIntelligence().addTileInView(tilePos);
		}
		if(!this.seenTiles.contains(tilePos))
		{
			this.seenTiles.add(tilePos);
			playerEmpire.getEmpireIntelligence().addSeenTile(tilePos);
		}
	}

	@Override
	public void addTilesInView(ArrayList<TilePos> tiles)
	{
		World world = this.player.getEntityWorld();
		Empire playerEmpire = EmpireList.get(world).getEmpireByID(playerEmpireID);
		ArrayList<TilePos> newTilesInView = new ArrayList<TilePos>();
		ArrayList<TilePos> newSeenTiles = new ArrayList<TilePos>();
		
		for(int i = 0; i < tiles.size(); i++)
		{
			if(!this.tilesInView.contains(tiles.get(i)))
			{
				this.tilesInView.add(tiles.get(i));
				newTilesInView.add(tiles.get(i));
			}
			if(!this.seenTiles.contains(tiles.get(i)))
			{
				this.seenTiles.add(tiles.get(i));
				newSeenTiles.add(tiles.get(i));
			}
		}
		
		playerEmpire.getEmpireIntelligence().addTilesInView(newTilesInView);
		playerEmpire.getEmpireIntelligence().addSeenTiles(newSeenTiles);
	}


	@Override
	public TreeMap<UUID, Integer> getMetEmpires()
	{
		return metEmpires;
	}

	public EntityPlayer getPlayer()
	{
		return this.player;
	}


	@Override
	public ArrayList<TilePos> getSeenTiles()
	{
		return this.seenTiles;
	}
	
	@Override
	public ArrayList<TilePos> getTilesInView()
	{
		return this.tilesInView;
	}

	@Override
	public boolean isMetEmpire(UUID empireID)
	{
		return metEmpires.containsKey(empireID);
	}

	@Override
	public boolean isSeenTile(TilePos tilePos)
	{
		return this.seenTiles.contains(tilePos) ? true : false;
	}

	@Override
	public boolean isTileInView(TilePos tilePos)
	{
		return this.tilesInView.contains(tilePos) ? true : false;
	}

	@Override
	public boolean removeTileInView(TilePos tilePos)
	{
		World world = this.player.getEntityWorld();
		Empire playerEmpire = EmpireList.get(world).getEmpireByID(playerEmpireID);
		
		if(this.tilesInView.contains(tilePos))
		{
			this.tilesInView.remove(tilePos);
			playerEmpire.getEmpireIntelligence().removeTileInView(tilePos);
			return true;
		}
		
		else
		{
			return false;
		}
	}

	@Override
	public ArrayList<TilePos> removeTilesInView(ArrayList<TilePos> tiles)
	{
		World world = this.player.getEntityWorld();
		Empire playerEmpire = EmpireList.get(world).getEmpireByID(playerEmpireID);
		
		ArrayList<TilePos> unremovedTiles = new ArrayList<TilePos>();
		for(int i = 0; i < tiles.size(); i++)
		{
			if(this.tilesInView.contains(tiles.get(i)))
			{
				this.tilesInView.remove(tiles.get(i));
			}
			
			else
			{
				unremovedTiles.add(tiles.get(i));
				tiles.remove(i);
			}
		}
		
		playerEmpire.getEmpireIntelligence().removeTilesInView(tiles);
		return unremovedTiles;
	}

	public void set(IIntelligence iintelligence)
	{
		PlayerIntelligence playerIntelligence = (PlayerIntelligence) iintelligence;
		this.player = playerIntelligence.getPlayer(); 
		this.seenTiles = playerIntelligence.getSeenTiles();
		this.tilesInView = playerIntelligence.getTilesInView();
	}

	@Override
	public void set(ArrayList<TilePos> seenTiles, ArrayList<TilePos> tilesInView)
	{
		this.seenTiles = seenTiles;
		this.tilesInView = tilesInView;
	}

	@Override
	public void updateTilesInView(TilePos tilePos)
	{
		World world = this.player.getEntityWorld();
		Empire playerEmpire = EmpireList.get(world).getEmpireByID(playerEmpireID);
		
		this.tilesInView = TilePos.getTilesInRangeOf(new ChunkPos(player.chunkCoordX, player.chunkCoordZ), 1);
		playerEmpire.getEmpireIntelligence().updateTilesInView(tilePos);
	}
}
