package com.github.QVBA.Commands;

import java.util.List;

import com.github.QVBA.Helpers.ChatHelper;
import com.github.QVBA.NBT.PlayerEntityProperties;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class SavedItems extends Command{
	
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
		PlayerEntityProperties props = PlayerEntityProperties.get(player);
		if(props.isSkulled()) {
			ChatHelper.sendChatMessage(sender, "You are skulled, and therefore have no saved items");
			return;
		}
		ChatHelper.sendChatMessage(sender, "Saved Items - ");
		for(int i = 0; i < props.getSavedItems().length; i++) {
			if(props.getSavedItem(i) != null) {
				ChatHelper.sendChatMessage(sender, i + ": " + props.getSavedItem(i).getDisplayName());
			}
		}	
	}

}
