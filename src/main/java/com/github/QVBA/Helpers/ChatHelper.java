package com.github.QVBA.Helpers;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import com.github.QVBA.Reference;

public class ChatHelper {
	
	public static ChatComponentText message(String message) {
		return new ChatComponentText("[" + Reference.MOD_NAME + "] " + message);
	}
	
	public static void sendChatMessage(ICommandSender sender, String message) {
		sender.addChatMessage(message(message));
	}

}
