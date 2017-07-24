package com.example.examplemod.common.event;

import java.util.ArrayList;

import com.example.examplemod.common.caps.turn.CapPlayerTurn;
import com.example.examplemod.common.caps.turn.ITurn;
import com.example.examplemod.common.core.turn.WorldTurn;
import com.example.examplemod.common.map.tile.Tile;
import com.example.examplemod.common.map.tile.TileList;
import com.example.examplemod.common.map.tile.TilePos;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class EventHandlerCommon 
{
	private int counter = 0;
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e)
	{
		World world = e.getWorld();
		if(!world.isRemote)
		{
			WorldTurn.get(world);
			TileList.get(world);
		}
	}
	
	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent e)
	{
		EntityPlayer player = e.player;
		World world = player.world;
		
		if(world.isRemote == false) 
		{
			WorldTurn turn = WorldTurn.get(world);
		
			int currentTurn = turn.getTurn();
			String message = String.format("Hello there, the World Turn is currently %s", currentTurn);
			player.sendMessage(new TextComponentString(message));
		}
	}
	
	@SubscribeEvent
	public void onServerTick(ServerTickEvent e)
	{
		if(counter == 1)
		{
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			World world = server.getEntityWorld();
		
			if(world.isRemote == false) 
			{
			
				long totalTime = world.getWorldTime();
				long currentTime = totalTime % 24000;
			
				if(currentTime == 1)
				{
					System.out.println("Time is 0");
					WorldTurn turn = WorldTurn.get(world);
					int currentTurn = turn.advanceTurn();
					System.out.println("New Turn!!  The World Turn is now " + currentTurn);
					ArrayList<EntityPlayerMP> playerList = (ArrayList<EntityPlayerMP>)(world.getMinecraftServer().getServer().getPlayerList().getPlayers());
					for(int i=0; i < playerList.size(); i++)
					{
						EntityPlayer player = playerList.get(i);
						ITurn playerTurn = player.getCapability(CapPlayerTurn.TURN_CAP, null);
						playerTurn.addTurn();
					}
				}
			}
			
			counter = 0;
		}
		else
		{
			counter++;
		}
	}
	
	@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load e)
	{
		World world = e.getWorld();
		Chunk chunk = e.getChunk();
		TileList tileList = TileList.get(world);
		
		TilePos tilePos = new TilePos(chunk.getPos());
		ChunkPos[][] chunks = Tile.createChunkArray(tilePos);
		
		if(tileList.getTileByPos(tilePos)==null)
		{
			// Check if all chunks are loaded before creating the tile
			for(int i=0; i<4; i++)
			{
				for(int j=0; j<4; j++)
				{
					if(!world.getChunkProvider().isChunkGeneratedAt(chunks[i][j].x,chunks[i][j].z))
					{
						return;
					}
				}
			}
			System.out.println("Adding new tile - x: "+tilePos.x+" z: "+tilePos.z); //////////////////
			Tile tile = new Tile(chunk);
		}
	}
	
	 @SubscribeEvent
	 public void onDebugOverlay(RenderGameOverlayEvent.Text e)
	 {
		 //if(e.getType() == RenderGameOverlayEvent.ElementType.DEBUG)
		 {
			 try {
				 int playerx = Minecraft.getMinecraft().player.chunkCoordX;
				 int playerz = Minecraft.getMinecraft().player.chunkCoordZ;
				 ChunkPos chunkPos = new ChunkPos(playerx,playerz);
				 TilePos tilePos = new TilePos(chunkPos);
				 TileList tileList = TileList.get(Minecraft.getMinecraft().player.world);
				 e.getRight().add("Chunk coords:"+playerx+" "+playerz);
				 e.getRight().add("Tile information:");
				 
				 Tile currTile = tileList.getTileByPos(tilePos);
				 
				 e.getRight().add(currTile==null ? "Tile " +tilePos.x+" "+tilePos.z+ "not created properly" : tilePos.x+" "+tilePos.z);
				 e.getRight().add("Tile biome: "+currTile.getTileBiome().name());
			 }
			 catch (Exception e1) {}
				 
		 }
	 }
}
