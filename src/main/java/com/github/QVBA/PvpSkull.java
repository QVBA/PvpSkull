package com.github.QVBA;

import net.minecraftforge.common.MinecraftForge;

import com.github.QVBA.Commands.RemoveItem;
import com.github.QVBA.Commands.SaveItem;
import com.github.QVBA.Commands.AmISkulled;
import com.github.QVBA.Commands.SavedItems;
import com.github.QVBA.Commands.getKDR;
import com.github.QVBA.Events.PlayerEvents;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.MOD_VERSION)
public class PvpSkull {

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PlayerEvents events = new PlayerEvents();
		MinecraftForge.EVENT_BUS.register(events);
		FMLCommonHandler.instance().bus().register(events);
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new SaveItem());
		event.registerServerCommand(new AmISkulled());
		event.registerServerCommand(new SavedItems());
		event.registerServerCommand(new RemoveItem());
		event.registerServerCommand(new getKDR());
	}
}
