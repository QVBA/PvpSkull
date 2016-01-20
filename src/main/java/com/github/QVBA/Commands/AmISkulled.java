package com.github.QVBA.Commands;

import java.util.List;

import com.github.QVBA.Reference;
import com.github.QVBA.SkulledPlayerManager;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class AmISkulled implements ICommand{
	
	private SkulledPlayerManager manager;
	
	public AmISkulled(SkulledPlayerManager manager) {
		this.manager = manager;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] p_71515_2_) {
		if(!(sender instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer sender1 = (EntityPlayer) sender;
		sender1.addChatMessage(new ChatComponentText(manager.isPlayerSkulled(sender1) ? "[" + Reference.MOD_NAME + "] You are skulled!" : "[" + Reference.MOD_NAME + "] You are not skulled!"));
		
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
		// TODO Auto-generated method stub
		return false;
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
