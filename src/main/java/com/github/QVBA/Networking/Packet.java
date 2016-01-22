package com.github.QVBA.Networking;

import com.github.QVBA.NBT.PlayerEntityProperties;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class Packet implements IMessage{
	
	private boolean skullStatus;
	private int entityID;
	
	public Packet(){}
	public Packet(EntityPlayer player) {
		skullStatus = PlayerEntityProperties.get(player).isSkulled();
		entityID = player.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		skullStatus = buf.readBoolean();
		entityID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(skullStatus);
		buf.writeInt(entityID);
		
	}
	
	public static class PacketHandler implements IMessageHandler<Packet, IMessage> {

		@Override
		public IMessage onMessage(Packet message, MessageContext ctx) {
			int entityID = message.entityID;
			Entity myEntity = Minecraft.getMinecraft().theWorld.getEntityByID(entityID);
			if(myEntity instanceof EntityPlayer) { //Should never be null, but check anyway.
				EntityPlayer thePlayer = (EntityPlayer) myEntity;
				PlayerEntityProperties.get(thePlayer).setSkulled(message.skullStatus);
			}
			return null;
		}
	}
}
