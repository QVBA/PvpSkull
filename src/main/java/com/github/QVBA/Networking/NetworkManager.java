package com.github.QVBA.Networking;

import net.minecraft.entity.player.EntityPlayer;

import com.github.QVBA.Reference;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkManager {
	
	public static SimpleNetworkWrapper INSTANCE;
	
	public NetworkManager() {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
	}
	
	public void registerPacket(Class packetClass, Class packetHandler) {
		INSTANCE.registerMessage(packetHandler, packetClass, 0, Side.CLIENT);
	}
	
	public void sendToAllAround(IMessage message, EntityPlayer player) {
		INSTANCE.sendToAllAround(message, new TargetPoint(player.dimension, (double) player.getPlayerCoordinates().posX, (double) player.getPlayerCoordinates().posY, (double) player.getPlayerCoordinates().posZ, 100));
	}

}
