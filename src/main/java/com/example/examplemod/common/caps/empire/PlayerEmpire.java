package com.example.examplemod.common.caps.empire;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerEmpire implements IEmpire
{
	private UUID empireUUID = null;
	private EntityPlayer player;
	
	public PlayerEmpire(@Nullable EntityPlayer player)
	{
		this.player = player;
		//this.empireUUID = UUID.randomUUID();
	}
	
	public void setUUID(UUID empireUUID) 
	{
		this.empireUUID = empireUUID;
	}

	@Override
	public void abandonEmpire() 
	{
		this.empireUUID = null;
	}

	@Override
	public UUID getUUID() 
	{
		return empireUUID;
	}
}
