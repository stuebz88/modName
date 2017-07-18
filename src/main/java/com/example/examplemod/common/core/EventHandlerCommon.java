package com.example.examplemod.common.core;

public class EventHandlerCommon 
{
	
	/*@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent e)
	{
		EntityPlayer player = e.player;
		World world = player.world;
		ITurn turn = world.getCapability(TurnProvider.TURN_CAP, null);
		String message = String.format("Hello there, the World Turn is currently %d", turn);
		player.sendMessage(new TextComponentString(message));
	}
	
	@SubscribeEvent
	public void onServerTick(ServerTickEvent e)
	{
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		System.out.println(server);
		World world = server.getEntityWorld();
		System.out.println(world);
		long totalTime = world.getWorldTime();
		long currentTime = totalTime % 24000;
		System.out.println(currentTime);
		
		if(currentTime == 0)
		{
			System.out.println("Time is 0");
			ITurn turn = world.getCapability(TurnProvider.TURN_CAP, null);
			System.out.println(turn);
			turn.advanceTurn(world);
			System.out.println("current time = 0");
		}
	}*/
}
