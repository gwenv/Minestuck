package com.mraof.minestuck.event;

import com.mraof.minestuck.world.MinestuckDimensionHandler;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ServerEventHandler
{
	
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event)
	{
		if(event.side.isServer() && event.phase == TickEvent.Phase.START)
		{
			MinestuckDimensionHandler.worldTick(event.world);
			
		}
	}
	
}