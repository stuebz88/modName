package com.example.examplemod.common.caps.intelligence;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.UUID;

import com.example.examplemod.common.map.tile.TilePos;

public interface IIntelligence
{
	public void addMetEmpire(UUID empireID);
	
	public void addMetEmpires(ArrayList<UUID> empires);
	
	public void addSeenTile(TilePos tilePos);
	
	public void addSeenTiles(ArrayList<TilePos> tiles);

    public void addTileInView(TilePos tilePos);
	
	public void addTilesInView(ArrayList<TilePos> tiles);

    public TreeMap<UUID, Integer> getMetEmpires();
    
    public ArrayList<TilePos> getSeenTiles();
    
    public ArrayList<TilePos> getTilesInView();
    
    public boolean isMetEmpire(UUID empireID);
    
    public boolean isSeenTile(TilePos tilePos);
    
    public boolean isTileInView(TilePos tilePos);
    
    public boolean removeTileInView(TilePos tilePos);
    
    public ArrayList<TilePos> removeTilesInView(ArrayList<TilePos> tiles);
    
    public void set(IIntelligence itile);
    
    public void set(ArrayList<TilePos> seenTiles, ArrayList<TilePos> tilesInView);
    
    public void updateTilesInView(TilePos tilePos);
}
