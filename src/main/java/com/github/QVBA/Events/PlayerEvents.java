package com.github.QVBA.Events;

import java.util.ArrayList;
import java.util.Iterator;

import com.github.QVBA.Reference;
import com.github.QVBA.Helpers.ChatHelper;
import com.github.QVBA.Helpers.UtilityHelper;
import com.github.QVBA.NBT.NBTHelper;
import com.github.QVBA.NBT.PlayerEntityProperties;

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
*  PlayerEvent.Clone
 * @author QVBA/Roxox1
 */
public class PlayerEvents {
	
	@SubscribeEvent
	public void onEvent(AttackEntityEvent event) {
		if(event.entityPlayer.worldObj.isRemote) {
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
		if(event.entityPlayer.worldObj.isRemote) {
			return;
		}
		EntityPlayer died = event.entityPlayer;
		PlayerEntityProperties diedProps = PlayerEntityProperties.get(died);
		if(!diedProps.isSkulled()) {
			
			//Remove any items we found to keep from the items to be dropped on the floor. Add the items we find to the list of items to refund the player.
			Iterator dropsIterator = event.drops.iterator();
			int i = 0;
			while(dropsIterator.hasNext()) {
				ItemStack stack = ((EntityItem) dropsIterator.next()).getEntityItem();
				if(stack != null && UtilityHelper.listContains(diedProps.getSavedItems(), stack) && NBTHelper.isItemKeepOnDeath(stack)) {
					diedProps.setOwedItem(i, stack);
					dropsIterator.remove();
					i++;
				}
			}
			diedProps.setOwed(i > 0);
		}	
	}
	
	@SubscribeEvent
	public void onEvent(PlayerRespawnEvent event) {
		if(event.player.worldObj.isRemote) {
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
		//Update the players NBT tags when the player dies. Otherwise NBT is lost on death.
		PlayerEntityProperties.get(event.entityPlayer).copy(PlayerEntityProperties.get(event.original));
	}
	
	@SubscribeEvent
	public void onEvent(LivingDeathEvent event) {
		//Tracks K/D. Only applies to PvP. 
		if(!(event.entity instanceof EntityPlayer)) return;
		if(!(event.source.getEntity() instanceof EntityPlayer)) return;
		PlayerEntityProperties.get((EntityPlayer) event.source.getEntity()).addKill();
		PlayerEntityProperties.get((EntityPlayer) event.entityLiving).addDeath();
	}
}
