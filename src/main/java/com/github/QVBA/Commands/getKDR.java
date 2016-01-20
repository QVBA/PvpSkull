package com.github.QVBA.Commands;

import java.util.List;

import com.github.QVBA.Helpers.ChatHelper;
import com.github.QVBA.NBT.PlayerEntityProperties;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class getKDR extends Command{

	@Override
	public String getCommandName() {
		return "kdr";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "./kdr returns your kill:death ratio";
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] p_71515_2_) {
		if(!(sender instanceof EntityPlayer)) {
			System.out.println("You can't run this command as console");
			return;
		}
		EntityPlayer player = (EntityPlayer) sender;
		PlayerEntityProperties props = PlayerEntityProperties.get(player);
		ChatHelper.sendChatMessage(player, "K:" + props.getKills() + " D:" + props.getDeaths() + " R:" + (props.getDeaths() > 0 ? (double)props.getKills() / (double)props.getDeaths() : props.getKills()));
		ChatHelper.sendChatMessage(player, "You are currently on a " + props.getCurrentKillstreak() + " killstreak. Your highest killstreak is " + props.getHighestKillstreak());
	}

}
