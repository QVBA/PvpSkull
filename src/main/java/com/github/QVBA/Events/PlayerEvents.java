package com.github.QVBA.Events;

import java.util.ArrayList;
import java.util.Iterator;

import com.github.QVBA.Reference;
import com.github.QVBA.Helpers.ChatHelper;
import com.github.QVBA.NBT.NBTHelper;
import com.github.QVBA.NBT.PlayerEntityProperties;
import com.github.QVBA.Proxies.CommonProxy;

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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
/** 
 * Events used : 
 * AttackEntityEvent
 * PlayerDropsEvent
 * PlayerRespawnEvent
 * EntityConstructingEvent
 * LivingDeathEvent
 * EntityJoinWorldEvent
 * @author QVBA/Roxox1
 */
public class PlayerEvents {
	
	@SubscribeEvent
	public void onEvent(AttackEntityEvent event) {
		if(event.entityPlayer.worldObj.isRemote) { //Return if this is clientside.
			return;
		}
		Entity a = event.target;
		if(a instanceof EntityPlayer) {
			EntityPlayer attacker = event.entityPlayer;
			EntityPlayer attacked = (EntityPlayer) a;
			PlayerEntityProperties attackerProps = PlayerEntityProperties.get(attacker);
			PlayerEntityProperties attackedProps = PlayerEntityProperties.get(attacked);
			if(!attackedProps.isSkulled()) {
				attackerProps.setSkulled(true);
			}
		}
	}
	@SubscribeEvent
	public void onEvent(PlayerDropsEvent event) {
		if(event.entityPlayer.worldObj.isRemote) { //Return if this is clientside.
			return;
		}
		EntityPlayer died = event.entityPlayer;
		PlayerEntityProperties diedProps = PlayerEntityProperties.get(died);
		if(!diedProps.isSkulled()) {
			//Loop through the items in the players inventory and decide which ones to keep.
			ArrayList<EntityItem> drops = new ArrayList<EntityItem>();
			int i = 0;
			for(EntityItem item : event.drops) {
				ItemStack stack = item.getEntityItem();
				if(stack != null && NBTHelper.isItemKeepOnDeath(stack)) {
					if(diedProps.savedItemsContains(stack)) {
						drops.add(item);
						diedProps.setOwedItem(i, stack);
						i++;
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
			diedProps.setOwed(true);
		}	
	}
	
	@SubscribeEvent
	public void onEvent(PlayerRespawnEvent event) {
		if(event.player.worldObj.isRemote) { //Return if this is clientside.
			return;
		}
		EntityPlayer player = event.player;
		PlayerEntityProperties props = PlayerEntityProperties.get(player);
		if(props.isOwed()) {
			for(ItemStack stack : props.getOwedItems()) {
				if(stack != null) {
					NBTHelper.getModNbt(stack).setBoolean(NBTHelper.NBT_KEEPONDEATH, false);
					player.inventory.addItemStackToInventory(stack);
				}
			}
			
			props.setOwed(false);
			props.clearOwedItems();
			ChatHelper.sendChatMessage(player, "Your items have been returned, and your saved items list has been cleared");
		}
	}
	
	@SubscribeEvent
	public void onEvent(EntityConstructing event) {
		if(!(event.entity instanceof EntityPlayer)) {
			return; // If the entity is not a player, we don't want to add NBT Tags..
		}
		EntityPlayer player = (EntityPlayer) event.entity;
		if(PlayerEntityProperties.get(player) == null) {
			PlayerEntityProperties.register(player);
		}
		
		if(player.getExtendedProperties(PlayerEntityProperties.EXT_PROP_NAME) == null) {
			player.registerExtendedProperties(PlayerEntityProperties.EXT_PROP_NAME, new PlayerEntityProperties(player));
		}
	}	
	

	@SubscribeEvent
	public void onEvent(PlayerEvent.Clone event) {
		PlayerEntityProperties.get(event.entityPlayer).copy(PlayerEntityProperties.get(event.original));
	}
}
