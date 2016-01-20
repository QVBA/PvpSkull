package com.github.QVBA;

import java.util.ArrayList;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public class EntityPlayerItemStorage {
	
	public ArrayList<EntityItem> itemsOwed;
	public EntityPlayer player;
	
	public EntityPlayerItemStorage(EntityPlayer player, ArrayList<EntityItem> itemsOwed) {
		this.itemsOwed = itemsOwed;
		this.player = player;
	}

}
