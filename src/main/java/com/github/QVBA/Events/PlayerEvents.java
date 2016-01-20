package com.github.QVBA.Events;

import java.util.ArrayList;
import java.util.Iterator;

import com.github.QVBA.EntityPlayerItemStorage;
import com.github.QVBA.NBTHelper;
import com.github.QVBA.Reference;
import com.github.QVBA.SkulledPlayerManager;

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
	
	private SkulledPlayerManager skulledManager;
	
	public PlayerEvents(SkulledPlayerManager manager) {
		this.skulledManager = manager;
	}
	
	@SubscribeEvent
	public void onEvent(AttackEntityEvent event) {
		if(event.entityPlayer.worldObj.isRemote) { //Return if this is clientside.
			return;
		}
		Entity a = event.target;
		EntityPlayer attacker = event.entityPlayer;
		if(a instanceof EntityPlayer) {
			EntityPlayer attacked = (EntityPlayer) a;
			if(!skulledManager.isPlayerSkulled(attacked)) {
				skulledManager.skullPlayer(attacker);
			}
			System.out.println(attacker.getDisplayName() + " attacked " + attacked.getDisplayName());
			for(EntityPlayer player : skulledManager.list()) {
				System.out.println(player.getDisplayName() + " is skulled!");
			}
		}
	}
	
	@SubscribeEvent
	public void onEvent(PlayerDropsEvent event) {
		if(event.entityPlayer.worldObj.isRemote) { //Return if this is clientside.
			return;
		}
		EntityPlayer died = event.entityPlayer;
		if(!skulledManager.isPlayerSkulled(died)) {
			int size = event.drops.size();
			//If the player had 3 or less items, we don't need to bother figuring out which ones to keep, give him all of them.
			if(size <= 3) {
				event.drops.clear(); //First remove all of the items from the drops.
				return;
			}
			int i = 0;
			ArrayList<EntityItem> drops = new ArrayList<EntityItem>();
			for(EntityItem item : event.drops) {
				ItemStack stack = item.getEntityItem();
				if(i < 3 && stack != null && NBTHelper.getNbt(stack, Reference.MOD_ID) != null && NBTHelper.getNbt(stack, Reference.MOD_ID).hasKey("keepOnDeath") && NBTHelper.getNbt(stack, Reference.MOD_ID).getBoolean("keepOnDeath")) {
					drops.add(item);
					i++;
				}else {
					NBTHelper.getNbt(stack, Reference.MOD_ID).setBoolean("keepOnDeath", false);
				}
			}
			Iterator dropsIterator = event.drops.iterator();
			while(dropsIterator.hasNext()) {
				EntityItem next = (EntityItem) dropsIterator.next();
				if(drops.contains(next)) {
					dropsIterator.remove();
				}
			}
			
			skulledManager.addOwedPlayer(new EntityPlayerItemStorage(died, drops));
			if(i >= 3) {
				died.addChatMessage(new ChatComponentText("[" + Reference.MOD_ID + "]" + " You had more than 3 items saved, ONLY 3 MAY BE SAVED!"));
			}
		}	
	}
	
	@SubscribeEvent
	public void onEvent(PlayerRespawnEvent event) {
		if(event.player.worldObj.isRemote) { //Return if this is clientside.
			return;
		}
		System.out.println("FIRED!");
		EntityPlayer player = event.player;
		EntityPlayerItemStorage storage = skulledManager.getOwedPlayer(player);
		if(storage != null) {
			for(EntityItem item : storage.itemsOwed) {
				ItemStack stack = item.getEntityItem();
				NBTHelper.getNbt(stack, Reference.MOD_ID).setBoolean("keepOnDeath", false);
				player.inventory.addItemStackToInventory(stack);
			}
			skulledManager.removeOwedPlayer(storage);
			player.addChatMessage(new ChatComponentText("[" + Reference.MOD_NAME + "] Your items have been returned, and your items are no longer saved on death."));
		}
	}

}
