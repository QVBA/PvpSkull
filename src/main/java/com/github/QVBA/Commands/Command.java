package com.github.QVBA.Commands;

import java.util.List;

import com.github.QVBA.Reference;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public abstract class Command implements ICommand{

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) { return true; }
	@Override
	public int compareTo(Object o) { return 0; }
	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) { return false; }
	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) { return null; }

}
