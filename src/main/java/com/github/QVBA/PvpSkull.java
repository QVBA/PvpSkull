package com.github.QVBA;

import net.minecraftforge.common.MinecraftForge;

import com.github.QVBA.Commands.RemoveItem;
import com.github.QVBA.Commands.SaveItem;
import com.github.QVBA.Commands.AmISkulled;
import com.github.QVBA.Commands.SavedItems;
import com.github.QVBA.Commands.getKDR;
import com.github.QVBA.Events.PlayerEvents;
import com.github.QVBA.Networking.NetworkManager;
import com.github.QVBA.Networking.Packet;
import com.github.QVBA.Proxies.CommonProxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.MOD_VERSION)
public class PvpSkull {


    @SidedProxy(clientSide = "com.github.QVBA.Proxies.ClientProxy", serverSide = "com.github.QVBA.Proxies.CommonProxy")
    public static CommonProxy proxy;
    
    public static NetworkManager networkManager;
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		networkManager = new NetworkManager();
		networkManager.registerPacket(Packet.class, Packet.PacketHandler.class);
		PlayerEvents events = new PlayerEvents();
		MinecraftForge.EVENT_BUS.register(events);
		FMLCommonHandler.instance().bus().register(events);
		proxy.init();
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
