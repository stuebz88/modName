package com.example.examplemod.common.caps.empire;

import com.example.examplemod.common.core.empire.Empire;

public class PlayerEmpire implements IEmpire
{
	private Empire empire = null;
	
	@Override
	public void setEmpire(Empire empire) 
	{
		this.empire = empire;
	}

	@Override
	public Empire getEmpire() 
	{
		return this.empire;
	}

	@Override
	public void abandonEmpire() 
	{
		this.empire = null;
	}
}
