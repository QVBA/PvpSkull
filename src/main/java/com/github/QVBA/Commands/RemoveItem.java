package com.github.QVBA.Commands;

import java.util.List;

import com.github.QVBA.Helpers.ChatHelper;
import com.github.QVBA.NBT.PlayerEntityProperties;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class RemoveItem extends Command{

	@Override
	public String getCommandName() {
		return "removeitem";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "Removes an item from your save list, takes 1 argument, the ID of the item shown by ./saveditems";
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(!(sender instanceof EntityPlayer)) { 
			System.out.println("This command cannot be used by console!");
			return;
		}
		EntityPlayer player = (EntityPlayer) sender;
		String usage = "/removeitem itemindex (itemindex can be found in ./saveditems)";
		if(args.length == 0) {
			ChatHelper.sendChatMessage(player, usage);
			return;
		}
		String id = args[0];
		try {
			int id2 = Integer.valueOf(id);
			PlayerEntityProperties.get(player).setSavedItem(id2, null);
			ChatHelper.sendChatMessage(player, "Item was removed from your saved list");
		} catch (Exception e) {
			ChatHelper.sendChatMessage(player, usage);
			return;
		}
		
	}

}
