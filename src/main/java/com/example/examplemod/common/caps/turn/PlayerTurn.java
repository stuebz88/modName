package com.example.examplemod.common.caps.turn;

import javax.annotation.Nullable;

import com.example.examplemod.common.core.turn.WorldTurn;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class PlayerTurn implements ITurn
{
	private int turn = 0;
	private int startingTurn = 0;
	private EntityPlayer player;
	
	public PlayerTurn (@Nullable EntityPlayer player) 
	{
		this.player = player;
		World world = player.world;
		WorldTurn worldTurn = WorldTurn.get(world);
		int currentTurn = worldTurn.getTurn();
		this.startingTurn = currentTurn;
		this.turn = 1;
	}
	
	@Override
	public void addTurn()
	{
		World world = this.player.getEntityWorld();
		
		this.turn++;
		if(!world.isRemote)
		{
			WorldTurn turn = WorldTurn.get(world);
			int currentTurn = turn.getTurn();
			String message = String.format("Hello there, it's a brand new turn! %n The World Turn is %d and your turn is %d", currentTurn, this.turn);
			player.sendMessage(new TextComponentString(message));
		}
	}
	
	@Override
	public void set(int turn)
	{
		this.turn = turn;
	}
	
	@Override
	public int getTurn()
	{
		return this.turn;
	}

	@Override
	public int getStartingTurn() 
	{
		return this.startingTurn;
	}
}
