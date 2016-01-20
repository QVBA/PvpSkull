package com.github.QVBA.Commands;

import java.util.List;

import com.github.QVBA.Reference;
import com.github.QVBA.PlayerManager;
import com.github.QVBA.Helpers.ChatHelper;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class AmISkulled extends Command{
	
	private PlayerManager manager;
	
	public AmISkulled(PlayerManager manager) {
		this.manager = manager;
	}

	@Override
	public String getCommandName() {
		return "amiskulled";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "For lack of a better way (currently) to tell if a player is skulled, this will have to do temporarily";
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] p_71515_2_) {
		if(!(sender instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer sender1 = (EntityPlayer) sender;
		ChatHelper.sendChatMessage(sender1, manager.isPlayerSkulled(sender1) ? "You are skulled!" : "You are not skulled!");
	}
}
