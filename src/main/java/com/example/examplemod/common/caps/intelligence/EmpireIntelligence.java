package com.example.examplemod.common.caps.intelligence;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.example.examplemod.common.core.turn.WorldTurn;
import com.example.examplemod.common.map.tile.TilePos;

import net.minecraft.world.World;

public class EmpireIntelligence implements IIntelligence
{
	UUID empireID;
	private TreeMap<UUID, Integer> metEmpires = new TreeMap<UUID, Integer>();
	private ArrayList<TilePos> seenTiles = new ArrayList<TilePos>();
	private TreeMap<TilePos, ArrayList<String>> tilesInView = new TreeMap<TilePos, ArrayList<String>>();
	World world;
	
	public EmpireIntelligence(World world, UUID empireID, TreeMap<UUID, String> players)
	{
		this.empireID = empireID;
		this.world = world;
		
		for(Map.Entry<UUID, String> entry : players.entrySet())
		{
			PlayerIntelligence playerIntelligence = (PlayerIntelligence) world.getPlayerEntityByUUID(entry.getKey()).getCapability(CapPlayerIntelligence.INTELLIGENCE_CAP, null);
			this.addMetEmpires(new ArrayList<UUID>(playerIntelligence.getMetEmpires().keySet()));
			this.addSeenTiles(playerIntelligence.getSeenTiles());
			this.addTilesInView(playerIntelligence.getTilesInView(), "p" + entry.getKey());
		}
	}
	
	public EmpireIntelligence()
	{
	}
	
	@Override
	public void addMetEmpire(UUID empireID)
	{
		WorldTurn worldTurn = WorldTurn.get(this.world);
		
		if(!metEmpires.containsKey(empireID)) metEmpires.put(empireID, worldTurn.getTurn()); 
	}

	@Override
	public void addMetEmpires(ArrayList<UUID> empires)
	{
		WorldTurn worldTurn = WorldTurn.get(this.world);
		
		for(int i = 0; i < empires.size(); i++)
		{
			if(!metEmpires.containsKey(empires.get(i))) metEmpires.put(empires.get(i), worldTurn.getTurn());
		}
	}

	@Override
	public void addSeenTile(TilePos tilePos)
	{
		if(!seenTiles.contains(tilePos)) seenTiles.add(tilePos);
	}
	
	@Override
	public void addSeenTiles(ArrayList<TilePos> tiles)
	{
		for(int i = 0; i < tiles.size(); i++)
		{
			if(!seenTiles.contains(tiles.get(i))) seenTiles.add(tiles.get(i));
		}
	}

	/** 
	 * Do Not Use
	 * Please use addTileInView(TilePos, String) instead 
	 */
	@Override
	public void addTileInView(TilePos tilePos)
	{
	}

	public void addTileInView(TilePos tilePos, String viewerString)
	{
		if(!tilesInView.containsKey(tilePos))
		{
			ArrayList<String> viewersStrings = new ArrayList<String>();
			
			viewersStrings.add(viewerString);
			tilesInView.put(tilePos, viewersStrings);
		}
		
		else if(!tilesInView.get(tilePos).contains(viewerString))
		{
			ArrayList<String> viewersStrings = tilesInView.get(tilePos);
			
			viewersStrings.add(viewerString);
			tilesInView.replace(tilePos, viewersStrings);
		}
	}
	/**
	 * Do Not Use
	 * Please use addTilesInView(ArrayList<TilePos>, String) instead
	 */
	@Override
	public void addTilesInView(ArrayList<TilePos> tiles)
	{	
	}
	
	public void addTilesInView(ArrayList<TilePos> tiles, String viewerString)
	{
		for(int i = 0; i < tiles.size(); i++)
		{
			TilePos tilePos = tiles.get(i);
			if(!tilesInView.containsKey(tilePos))
			{
				ArrayList<String> viewersStrings = new ArrayList<String>();
			
				viewersStrings.add(viewerString);
				tilesInView.put(tilePos, viewersStrings);
			}
		
			else if(!tilesInView.get(tilePos).contains(viewerString))
			{
				ArrayList<String> viewersStrings = tilesInView.get(tilePos);
			
				viewersStrings.add(viewerString);
				tilesInView.replace(tilePos, viewersStrings);
			}
		}
	}

	@Override
	public TreeMap<UUID, Integer> getMetEmpires()
	{
		return this.metEmpires;
	}

	@Override
	public ArrayList<TilePos> getSeenTiles()
	{
		return this.seenTiles;
	}
	
	@Override
	public ArrayList<TilePos> getTilesInView()
	{
		return new ArrayList<TilePos>(tilesInView.keySet());
	}

	@Override
	public boolean isMetEmpire(UUID empireID)
	{
		return this.metEmpires.containsKey(empireID);
	}

	@Override
	public boolean isSeenTile(TilePos tilePos)
	{
		return this.seenTiles.contains(tilePos);
	}

	@Override
	public boolean isTileInView(TilePos tilePos)
	{
		return this.tilesInView.containsKey(tilePos);
	}

	/**
	 * Do Not Use
	 * Use removeTileInView(TilePos, String) instead
	 */
	@Override
	public boolean removeTileInView(TilePos tilePos)
	{
		return false;
	}

	public boolean removeTileInView(TilePos tilePos, String viewerString)
	{
		if(!tilesInView.containsKey(tilePos) || !tilesInView.get(tilePos).contains(viewerString)) return false;
		
		else if(tilesInView.get(tilePos).size() == 1 && tilesInView.get(tilePos).contains(viewerString))
		{
			tilesInView.remove(tilePos);
			return true;
		}
		
		else
		{
			tilesInView.get(tilePos).remove(viewerString);
			return true;
		}
	}
	
	/**
	 * <p>Do Not Use</p>
	 * <p>Use <code>removeTilesInView(ArrayList<TilePos>, String)</code> instead</p>
	 * <p>Returns null no matter what</p>
	 */
	@Override
	public ArrayList<TilePos> removeTilesInView(ArrayList<TilePos> tiles)
	{
		return null;
	}

	/**
	 * <p>Input an ArrayList of tiles to be removed from the view of the empire as well as the string for the viewer they should be removed for.</p>
	 * <p>Will return an ArrayList<TilePos> containing all of the tiles NOT removed from the empire's view (either because they weren't in it, or they weren't in it for that viewer).</p>
	 */
	public ArrayList<TilePos> removeTilesInView(ArrayList<TilePos> tiles, String viewerString)
	{
		for(int i = 0; i < tiles.size(); i++)
		{
			TilePos tilePos = tiles.get(i);
				
			if(tilesInView.containsKey(tilePos))
			{
			
				if(tilesInView.get(tilePos).size() == 1 && tilesInView.get(tilePos).contains(viewerString))
				{
					tilesInView.remove(tilePos);
					tiles.remove(tilePos);
				}
			
				else if(tilesInView.get(tilePos).contains(viewerString))
				{
					tilesInView.get(tilePos).remove(viewerString);
					tiles.remove(tilePos);
				}
			}
		}
		
		return tiles;
	}
	
	@Override
	public void set(IIntelligence iintelligence)
	{
		EmpireIntelligence empireIntelligence = (EmpireIntelligence) iintelligence;
		
		this.empireID = empireIntelligence.empireID;
		this.metEmpires = empireIntelligence.getMetEmpires();
		this.seenTiles = empireIntelligence.getSeenTiles();
		this.world = empireIntelligence.world;
		this.tilesInView = empireIntelligence.tilesInView;
	}

	@Override
	public void set(ArrayList<TilePos> seenTiles, ArrayList<TilePos> tilesInView)
	{
	}

	@Override
	public void updateTilesInView(TilePos tilePos)
	{
	}
}
