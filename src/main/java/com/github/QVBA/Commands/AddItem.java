package com.github.QVBA.Commands;

import java.util.List;

import com.github.QVBA.NBTHelper;
import com.github.QVBA.Reference;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class AddItem implements ICommand{

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCommandName() {
		return "saveItem";
		//return null;
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
			player.addChatMessage(new ChatComponentText("Your not holding an item!"));
			return;
		}
		if(heldItem.stackSize > 1) {
			player.addChatMessage(new ChatComponentText("You cannot store more than one item at a time!"));
			return;
		}
		
		NBTHelper.getNbt(heldItem, Reference.MOD_ID).setBoolean("keepOnDeath", true);
		player.addChatMessage(new ChatComponentText("[" + Reference.MOD_NAME + "] " + heldItem.getDisplayName() + " has been saved!"));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_,
			String[] p_71516_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		// TODO Auto-generated method stub
		return false;
	}

}
