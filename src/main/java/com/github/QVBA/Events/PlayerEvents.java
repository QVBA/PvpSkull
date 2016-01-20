package com.github.QVBA.Events;

import java.util.ArrayList;
import java.util.Iterator;

import com.github.QVBA.EntityPlayerItemStorage;
import com.github.QVBA.Reference;
import com.github.QVBA.PlayerManager;
import com.github.QVBA.Helpers.ChatHelper;
import com.github.QVBA.Helpers.NBTHelper;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

public class PlayerEvents {
	
	private PlayerManager manager;
	
	public PlayerEvents(PlayerManager manager) {
		this.manager = manager;
	}
	
	@SubscribeEvent
	public void onEvent(AttackEntityEvent event) {
		if(event.entityPlayer.worldObj.isRemote) { //Return if this is clientside.
			return;
		}
		Entity attacked = event.target;
		EntityPlayer attacker = event.entityPlayer;
		if(attacked instanceof EntityPlayer) {
			if(!manager.isPlayerSkulled((EntityPlayer) attacked)) {
				manager.skullPlayer(attacker);
			}
		}
	}
	@SubscribeEvent
	public void onEvent(PlayerDropsEvent event) {
		if(event.entityPlayer.worldObj.isRemote) { //Return if this is clientside.
			return;
		}
		EntityPlayer died = event.entityPlayer;
		if(!manager.isPlayerSkulled(died)) {
			if(manager.getUnSkulledPlayer(died) == null) return; // The player doesn't have any owed items stored. 
			
			//Loop through the items in the players inventory and decide which ones to keep.
			ArrayList<EntityItem> drops = new ArrayList<EntityItem>();
			for(EntityItem item : event.drops) {
				ItemStack stack = item.getEntityItem();
				if(stack != null && NBTHelper.isItemKeepOnDeath(stack)) {
					if(manager.getUnSkulledPlayer(died).getItems().contains(stack)) {
						drops.add(item);
					}else {
						//We found an item that it marked to save, but is not the players. 
						//This can happen in many ways, so we just handle it here and fix items being incorrectly saved.
						NBTHelper.getModNbt(stack).setBoolean(NBTHelper.NBT_KEEPONDEATH, false);
					}
				}
			}
			
			if(drops.size() <  1) return;  // Couldn't find any saved items in the players inventory.
			
			//Remove any items we found to keep from the items to be dropped on the floor.
			Iterator dropsIterator = event.drops.iterator();
			while(dropsIterator.hasNext()) {
				EntityItem next = (EntityItem) dropsIterator.next();
				if(drops.contains(next)) {
					dropsIterator.remove();
				}
			}
			
			//Tell the PlayerRespawnEvent event handler that it needs to give the player their saved items back.
			manager.getUnSkulledPlayer(died).setOwedItems(true);
		}	
	}
	
	@SubscribeEvent
	public void onEvent(PlayerRespawnEvent event) {
		if(event.player.worldObj.isRemote) { //Return if this is clientside.
			return;
		}
		System.out.println("FIRED!");
		EntityPlayer player = event.player;
		EntityPlayerItemStorage storage = manager.getUnSkulledPlayer(player);
		if(storage != null) {
			for(ItemStack stack : storage.getItems()) {
				NBTHelper.getModNbt(stack).setBoolean(NBTHelper.NBT_KEEPONDEATH, false);
				player.inventory.addItemStackToInventory(stack);
			}
			
			manager.getUnSkulledPlayer(player).setOwedItems(false);
			manager.getUnSkulledPlayer(player).clearItems();
			ChatHelper.sendChatMessage(player, "Your items have been returned, and your saved items list has been cleared");
		}
	}

}
