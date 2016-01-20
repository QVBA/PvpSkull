package com.github.QVBA.Proxies;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Not actually used as a proxy yet ;P
 */
public class CommonProxy {
	
	//Used to store Player Custom NBT Data between death and respawn.
	//Note that if the player dies, logs out before respawning, and then the server restarts, this information will be lost.
	private static final Map<String, NBTTagCompound> PlayerEntityPropertiesData = new HashMap<String, NBTTagCompound>();
	
	public static void storePlayerData(String name, NBTTagCompound compound) {
		PlayerEntityPropertiesData.put(name, compound);
	}
	
	public static NBTTagCompound removePlayerData(String name) {
		return PlayerEntityPropertiesData.remove(name);
	}
	
	public static NBTTagCompound getPlayerData(String name) {
		return PlayerEntityPropertiesData.get(name);
	}

}
