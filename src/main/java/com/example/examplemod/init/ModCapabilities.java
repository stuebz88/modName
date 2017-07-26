package com.example.examplemod.init;

import com.example.examplemod.common.caps.empire.CapPlayerEmpire;
import com.example.examplemod.common.caps.turn.CapPlayerTurn;

public class ModCapabilities 
{
	public static void registerCapabilities()
	{
		CapPlayerTurn.register();
		CapPlayerEmpire.register();
	}
}
