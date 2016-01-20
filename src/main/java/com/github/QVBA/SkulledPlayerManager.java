package com.github.QVBA;

import java.util.ArrayList;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public class SkulledPlayerManager {
	
	private ArrayList<EntityPlayer> skulledPlayers;
	private ArrayList<EntityPlayerItemStorage> owedPlayers;
	
	public SkulledPlayerManager() {
		skulledPlayers = new ArrayList<EntityPlayer>();
		owedPlayers = new ArrayList<EntityPlayerItemStorage>();
	}
	
	public void skullPlayer(EntityPlayer player) {
		if(!skulledPlayers.contains(player)) {
			skulledPlayers.add(player);
		}else {
			System.out.println("Something tried to add an already skulled player to the skulled players list. The player has not been re-added.");
		}
	}
	
	public void unSkullPlayer(EntityPlayer player) {
		if(skulledPlayers.contains(player)) {
			skulledPlayers.remove(player);
		}else {
			System.out.println("Something tried to remove a non-skulled player from the skulled players list.");
		}
	}
	
	public boolean isPlayerSkulled(EntityPlayer player) {
		return skulledPlayers.contains(player);
	}
	
	public ArrayList<EntityPlayer> list() {
		return skulledPlayers;
	}
	
	public void addOwedPlayer(EntityPlayerItemStorage playeritems) {
		owedPlayers.add(playeritems);
	}
	
	public void removeOwedPlayer(EntityPlayerItemStorage playeritems) {
		owedPlayers.remove(playeritems);
	}
	
	public EntityPlayerItemStorage getOwedPlayer(EntityPlayer player) {
		for(EntityPlayerItemStorage storage : owedPlayers) {
			if(storage.player.getDisplayName().equals(player.getDisplayName())) {
				return storage;
			}
		}
		return null;
	}
 
}
