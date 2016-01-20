package com.github.QVBA;

import java.util.ArrayList;

import com.github.QVBA.Helpers.ChatHelper;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntityPlayerItemStorage {
	
	private ArrayList<ItemStack> items;
	private EntityPlayer player;
	private boolean isOwedItems;
	
	public EntityPlayerItemStorage(EntityPlayer player, ArrayList<ItemStack> items) {
		this.items = items;
		this.player = player;
	}
	
	public EntityPlayerItemStorage (EntityPlayer player) {
		this.player = player;
		this.items = new ArrayList<ItemStack>();
	}
	
	public void addItem(ItemStack stack) {
		if(items.size() >= 3) {
			ChatHelper.sendChatMessage(player, "You cannot save more items, please remove an item first!");
			return;
		}
		items.add(stack);
		ChatHelper.sendChatMessage(player, stack.getDisplayName() + " is now protected on death");
	}
	
	public void removeItem(int index) {
		if(items.size() - 1 >= index) {
			removeItem(items.get(index));
		}
	}
	
	public void removeItem(ItemStack stack) {
		if(items.contains(stack)) {
			items.remove(stack);
			ChatHelper.sendChatMessage(player, stack.getDisplayName() + " is no longer protected on death");
			return;
		}
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public ArrayList<ItemStack> getItems() {
		return this.items;
	}
	
	public void setOwedItems(boolean owed) {
		this.isOwedItems = owed;
	}
	
	public boolean isOwedItems() {
		return this.isOwedItems;
	}
	
	public void clearItems(){
		items.clear();
	}
	
	public boolean hasItemsStored() {
		return this.items.size() > 0;
	}

}
