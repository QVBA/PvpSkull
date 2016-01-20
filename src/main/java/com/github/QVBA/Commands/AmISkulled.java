package com.github.QVBA.Commands;

import java.util.List;

import com.github.QVBA.Reference;
import com.github.QVBA.Helpers.ChatHelper;
import com.github.QVBA.NBT.PlayerEntityProperties;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class AmISkulled extends Command{
	
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
		EntityPlayer player = (EntityPlayer) sender;
		PlayerEntityProperties props = PlayerEntityProperties.get(player);
		ChatHelper.sendChatMessage(sender, props.isSkulled() ? "You are skulled!" : "You are not skulled!");
	}
}
