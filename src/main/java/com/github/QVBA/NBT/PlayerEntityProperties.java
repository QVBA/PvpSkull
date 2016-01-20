package com.github.QVBA.NBT;

import com.github.QVBA.Reference;
import com.github.QVBA.Helpers.ChatHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.EntityEvent;

/**
 * Only for use on players.
 * @author QVBA/Roxox1
 */
public class PlayerEntityProperties implements IExtendedEntityProperties
{

	public static final String EXT_PROP_NAME = Reference.MOD_ID + "_skull";
	private final EntityPlayer player;

	//Keys
	private final String KEY_IS_SKULLED = "isSkulled";
	private final String KEY_IS_OWED = "isOwed";
	private final String KEY_SAVED_ITEMS = "savedItems";
	private final String KEY_OWED_ITEMS = "owedItems";
	private final String KEY_TOTAL_KILLS = "totalKills";
	private final String KEY_TOTAL_DEATHS = "totalDeaths";
	private final String KEY_KILLSTREAK_CURRENT = "currentKillstreak";
	private final String KEY_KILLSTREAK_HIGHEST = "highestKillstreak";
	
	//Values
	private boolean isSkulled;
	private boolean isOwed;
	private ItemStack[] savedItems;
	private ItemStack[] owedItems; //We don't want to give players back items on death that they didn't have in their inventory.
	private int totalKills;
	private int totalDeaths;
	private int currentKillstreak;
	private int highestKillstreak;
	
	public PlayerEntityProperties(EntityPlayer player) {
		this.player = player;
		this.isSkulled = false;
		this.isOwed = false;
		this.savedItems = new ItemStack[3];
		this.owedItems = new ItemStack[3];
		this.totalKills = 0;
		this.totalDeaths = 0;
		this.currentKillstreak = 0;
		this.highestKillstreak = 0;
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(EXT_PROP_NAME, new PlayerEntityProperties(player));
	}
	
	public static final PlayerEntityProperties get(EntityPlayer player) {
		return (PlayerEntityProperties) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	//Save custom data.
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setBoolean(KEY_IS_SKULLED, this.isSkulled);
		properties.setBoolean(KEY_IS_OWED, this.isOwed);
		properties.setInteger(KEY_TOTAL_KILLS, this.totalKills);
		properties.setInteger(KEY_TOTAL_DEATHS, this.totalDeaths);
		properties.setInteger(KEY_KILLSTREAK_CURRENT, this.currentKillstreak);
		NBTTagList list = new NBTTagList();
		for(ItemStack stack : savedItems) {
			if(stack != null) {
				list.appendTag(stack.writeToNBT(new NBTTagCompound()));
			}	
		}
		properties.setTag(KEY_SAVED_ITEMS, list);
		NBTTagList list2 = new NBTTagList();
		for(ItemStack stack : owedItems) {
			if(stack != null) {
				list.appendTag(stack.writeToNBT(new NBTTagCompound()));
			}	
		}
		properties.setTag(KEY_OWED_ITEMS, list2);
		compound.setTag(EXT_PROP_NAME, properties);
	}

	//Load the data we saved.
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.isSkulled = properties.getBoolean(KEY_IS_SKULLED);
		this.isOwed = properties.getBoolean(KEY_IS_OWED);
		this.totalKills = properties.getInteger(KEY_TOTAL_KILLS);
		this.totalDeaths = properties.getInteger(KEY_TOTAL_DEATHS);
		this.currentKillstreak = properties.getInteger(KEY_KILLSTREAK_CURRENT);
		this.highestKillstreak = properties.getInteger(KEY_KILLSTREAK_HIGHEST);
		NBTTagList list = properties.getTagList(KEY_SAVED_ITEMS, Constants.NBT.TAG_LIST);
		for(int i = 0; i < list.tagCount(); i++) {
			this.savedItems[i] = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
		}
		NBTTagList list2 = properties.getTagList(KEY_OWED_ITEMS, Constants.NBT.TAG_LIST);
		for(int i = 0; i < list2.tagCount(); i++) {
			this.owedItems[i] = ItemStack.loadItemStackFromNBT(list2.getCompoundTagAt(i));
		}
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub
		
	}
	
	public void setSkulled(boolean skulled) {
		this.isSkulled = skulled;
	}
	
	public boolean isSkulled() {
		return this.isSkulled;
	}
	
	public boolean isOwed() {
		return this.isOwed;
	}
	
	public void setOwed(boolean owed) {
		this.isOwed = owed;
	}
	
	public ItemStack[] getSavedItems() {
		return this.savedItems;
	}
	
	public void setSavedItem(int index, ItemStack stack) {
		this.savedItems[index] = stack;
	}
	
	public ItemStack getSavedItem(int index) {
		return this.savedItems[index];
	}
	
	public boolean savedItemsContains(ItemStack stack) {
		for(ItemStack a : savedItems) {
			if(a == stack) return true;
		}
		return false;
	}
	
	public ItemStack[] getOwedItems() {
		return this.owedItems;
	}
	
	public void setOwedItem(int index, ItemStack stack) {
		this.owedItems[index] = stack;
	}
	
	public ItemStack getOwedItem(int index) {
		return this.owedItems[index];
	}
	
	public void clearOwedItems() {
		this.owedItems[0] = null;
		this.owedItems[1] = null;
		this.owedItems[2] = null;
	}
	
	public void addSavedItem(ItemStack stack) {
		int index = -1;
		for(int i = 0; i < savedItems.length; i++) {
			if(savedItems[i] == null) {
				savedItems[i] = stack;
				ChatHelper.sendChatMessage(player, stack.getDisplayName() + " is now protected on death!");
				return;
			}
		}
		ChatHelper.sendChatMessage(player, "You cannot save anymore items. Consider removing some if you want to save this item.");
	}

	public void copy(PlayerEntityProperties p) {
		this.isSkulled = p.isSkulled;
		this.isOwed = p.isOwed;
		this.owedItems = p.owedItems;
		this.savedItems = p.savedItems;
		this.totalDeaths = p.totalDeaths;
		this.totalKills = p.totalKills;
		this.highestKillstreak = p.highestKillstreak;
		if(p.currentKillstreak > this.highestKillstreak) {
			this.highestKillstreak = p.currentKillstreak;
		}
	}
	
	public int getKills() {
		return this.totalKills;
	}
	
	public void addKill() {
		this.totalKills++;
		this.currentKillstreak++;
		if(this.currentKillstreak > this.highestKillstreak) this.highestKillstreak = this.currentKillstreak;
	}
	
	public void setKills(int amount) {
		this.totalKills = amount;
	}

	public void addDeath() {
		this.totalDeaths++;
	}
	
	public int getDeaths() {
		return this.totalDeaths;
	}
	
	public void setDeaths(int amount) {
		this.totalDeaths = amount;
	}
	
	public int getCurrentKillstreak() {
		return this.currentKillstreak;
	}
	
	public int getHighestKillstreak() {
		return this.highestKillstreak;
	}
}