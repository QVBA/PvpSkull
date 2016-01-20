package com.github.QVBA.Commands;

import java.util.List;

import com.github.QVBA.EntityPlayerItemStorage;
import com.github.QVBA.PlayerManager;
import com.github.QVBA.Helpers.ChatHelper;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class SavedItems extends Command{
	
	private PlayerManager manager;
	
	public SavedItems(PlayerManager manager) {
		this.manager = manager;
	}

	@Override
	public String getCommandName() {
		return "saveditems";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "Returns a list of your saved items.";
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if(!(sender instanceof EntityPlayer)) {
			System.out.println("Console cannot save items, therefore has no saved items");
			return;
		}
		
		EntityPlayer player = (EntityPlayer) sender;
		
		if(manager.isPlayerSkulled(player)) {
			ChatHelper.sendChatMessage(sender, "You are skulled, and therefore have no saved items");
			return;
		}
		EntityPlayerItemStorage storage = manager.getUnSkulledPlayer(player);
		if(storage != null && storage.getItems().size() > 0) {
			int i = 1;
			ChatHelper.sendChatMessage(sender, "Saved Items - ");
			for(ItemStack stack : storage.getItems()) {
				ChatHelper.sendChatMessage(sender, i + ": " + stack.getDisplayName());
				i++;
			}
			return;
		}
		
		ChatHelper.sendChatMessage(sender, "You don't have any items saved.");
	}

}
