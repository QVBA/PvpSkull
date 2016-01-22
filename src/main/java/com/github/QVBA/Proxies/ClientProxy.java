package com.github.QVBA.Proxies;

import com.github.QVBA.Events.ClientOnlyPlayerEvents;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{
	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(new ClientOnlyPlayerEvents());
	}

}
