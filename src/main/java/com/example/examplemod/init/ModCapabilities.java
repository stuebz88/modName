package com.example.examplemod.init;

import com.example.examplemod.common.core.turn.CapPlayerTurn;

public class ModCapabilities 
{
	public static void registerCapabilities()
	{
		CapPlayerTurn.register();
	}
}