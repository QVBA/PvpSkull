package com.github.QVBA.Commands;

import java.util.List;

import com.github.QVBA.Reference;
import com.github.QVBA.Helpers.ChatHelper;
import com.github.QVBA.NBT.NBTHelper;
import com.github.QVBA.NBT.PlayerEntityProperties;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class SaveItem extends Command{

	@Override
	public String getCommandName() {
		return "saveitem";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "Hold the item you wish to save in your hand and execute command";
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(!(sender instanceof EntityPlayer)) {
			System.out.println("This command is only for players stupid!");
			return;
		}
		
		EntityPlayer player = (EntityPlayer) sender;
		ItemStack heldItem = player.getHeldItem();
		
		if(heldItem == null) {
			ChatHelper.sendChatMessage(player, "Your not holding an item!");
			return;
		}
		if(heldItem.stackSize > 1) {
			ChatHelper.sendChatMessage(player, "You cannot save more than one item at a time!");
			return;
		}
		
		//Requires additional work. (Note, even after update, this still requires work)
		PlayerEntityProperties props = PlayerEntityProperties.get(player);
		if(props.isSkulled()) {
			ChatHelper.sendChatMessage(sender, "You are skulled, you cannot protect items");
			return;
		}
		props.addSavedItem(heldItem);
		NBTHelper.getModNbt(heldItem).setBoolean(NBTHelper.NBT_KEEPONDEATH, true);
	}
}
