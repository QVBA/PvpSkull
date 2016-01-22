package com.github.QVBA.Events;

import org.lwjgl.opengl.GL11;

import com.github.QVBA.NBT.PlayerEntityProperties;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class ClientOnlyPlayerEvents {

	@SubscribeEvent
	public void onEvent(RenderPlayerEvent.Specials.Post event) {
		EntityPlayer player = event.entityPlayer;
		//System.out.println("Rendering player " + player.getDisplayName());
		PlayerEntityProperties props = PlayerEntityProperties.get(player);
		if(player != null && player.worldObj != null && props.isSkulled()) {
			//System.out.println(player.getDisplayName() + " is skulled!");
			
			//Draw Skull here!
		}
	}
}
