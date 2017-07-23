package com.example.examplemod.common.caps.turn;

import java.util.UUID;

import com.example.examplemod.common.core.turn.WorldTurn;

import net.minecraft.world.World;

public class EmpireTurn implements ITurn
{
	private int turn = 0;
	private int startingTurn = 0;
	private UUID empireID;
	private World world;
	
	public EmpireTurn(World world, UUID empireID)
	{
		this.turn = 1;
		this.startingTurn = WorldTurn.get(world).getTurn();
		this.empireID = empireID;
		this.world = world;
	}
	
	@Override
	public void addTurn() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void set(int turn) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public World getWorld()
	{
		return this.world;
	}

	public UUID getEmpireID()
	{
		return this.empireID;
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
