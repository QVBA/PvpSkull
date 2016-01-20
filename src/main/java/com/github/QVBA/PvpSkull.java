package com.github.QVBA;

import net.minecraftforge.common.MinecraftForge;

import com.github.QVBA.Commands.AddItem;
import com.github.QVBA.Commands.AmISkulled;
import com.github.QVBA.Events.PlayerEvents;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.MOD_VERSION)
public class PvpSkull {

	private SkulledPlayerManager manager;
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		manager = new SkulledPlayerManager();
		PlayerEvents events = new PlayerEvents(manager);
		MinecraftForge.EVENT_BUS.register(events);
		FMLCommonHandler.instance().bus().register(events);
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new AddItem());
		event.registerServerCommand(new AmISkulled(manager));
	}
}
