package com.example.examplemod.common.core;

import java.util.ArrayList;

import com.example.examplemod.common.core.turn.CapPlayerTurn;
import com.example.examplemod.common.core.turn.ITurn;
import com.example.examplemod.common.core.turn.WorldTurn;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
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
		}
		world.getWorldType();
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
				System.out.println(totalTime);
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
	
	
}
