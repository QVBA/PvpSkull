package com.github.QVBA;

import java.util.ArrayList;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerManager {
	
	private ArrayList<EntityPlayer> skulledPlayers;
	private ArrayList<EntityPlayerItemStorage> unskulledPlayers;
	private ArrayList<EntityPlayerItemStorage> owedPlayers;
	
	public PlayerManager() {
		skulledPlayers = new ArrayList<EntityPlayer>();
		owedPlayers = new ArrayList<EntityPlayerItemStorage>();
	}
	
	/**
	 * Skulls the target player.
	 * Can also be used to refresh the skull duration.
	 */
	public void skullPlayer(EntityPlayer player) {
		if(skulledPlayers.contains(player)) {
			skulledPlayers.remove(player);
		}
		skulledPlayers.add(player);
	}
	
	public void unSkullPlayer(EntityPlayer player) {
		if(skulledPlayers.contains(player)) {
			skulledPlayers.remove(player);
		}
	}
	
	public boolean isPlayerSkulled(EntityPlayer player) {
		return skulledPlayers.contains(player);
	}
	
	public void addUnSkulledPlayer(EntityPlayerItemStorage playeritems) {
		owedPlayers.add(playeritems);
	}
	
	public void removeUnSkulledPlayer(EntityPlayerItemStorage playeritems) {
		owedPlayers.remove(playeritems);
	}
	
	public EntityPlayerItemStorage getUnSkulledPlayer(EntityPlayer player) {
		for(EntityPlayerItemStorage storage : owedPlayers) {
			if(storage.getPlayer().getDisplayName().equals(player.getDisplayName())) {
				return storage;
			}
		}
		return null;
	}
 
}
