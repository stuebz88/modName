package com.example.examplemod.common.map.tile;

public class TileEnum 
{
	public enum Biome
	{
		PLAINS("plains"),
		NONE("none");
		
		private String typeName;
		
		private Biome(String s)
		{
			typeName = s;
		}
	}
	
	public enum Terrain
	{
		HILLS("hills"),
		RIVER("river");
		
		private String typeName;
		
		private Terrain(String s)
		{
			typeName = s;
		}
	}
	
	public enum Structure
	{
		FARM("farm"),
		CITY("city");
		
		private String typeName;
		
		private Structure(String s)
		{
			typeName = s;
		}
	}
}