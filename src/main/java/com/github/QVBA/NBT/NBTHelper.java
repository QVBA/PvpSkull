package com.github.QVBA.NBT;

import javax.annotation.Nonnull;

import com.github.QVBA.Reference;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class NBTHelper {
	
	 public static final int TAG_COMPOUND = 10;
	 public static final String NBT_KEEPONDEATH = "keepOnDeath"; //Used to prevent spelling mistakes.
	
    /**
     * <p>Get the NBTTagCompound associated with the given ItemStack and initializes it if necessary.</p>
     * @param stack the ItemStack
     * @return the NBTTagCompound associated with the ItemStack
     * @author diesiben07
     */
    public static NBTTagCompound getNbt(ItemStack stack) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        return stack.stackTagCompound;
    }
    
    /**
     * <p>Get the NBTTagCompound with the given key from the NBTTagCompound associated with the given ItemStack and initializes
     * both if necessary.</p>
     * @param stack the ItemStack
     * @param key the key
     * @return the NBTTagCompound
     * @author diesiben07
     */
    public static NBTTagCompound getNbt(ItemStack stack, String key) {
        return getOrCreateCompound(getNbt(stack), key);
    }
    
    /**
     * <p>Get the NBTTagCompound with the given key in {@code parent} or, if no entry for that key is present,
     * create a new NBTTagCompound and store it in {@code parent} with the given key.</p>
     *
     * @param parent the parent NBTTagCompound
     * @param key    the key
     * @return an NBTTagCompound
     * @author diesiben07
     */
    @Nonnull
    public static NBTTagCompound getOrCreateCompound(NBTTagCompound parent, String key) {
        NBTBase nbt = parent.getTag(key);
        if (nbt == null || nbt.getId() != TAG_COMPOUND) {
            nbt = new NBTTagCompound();
            parent.setTag(key, nbt);
        }
        return (NBTTagCompound) nbt;
    }
    
    /**
     * @author roxox1
     */
    public static NBTTagCompound getModNbt(ItemStack stack) {
    	return getNbt(stack, Reference.MOD_ID);
    }
    
    /**
     * @author roxox1
     */
    public static boolean isItemKeepOnDeath(ItemStack stack) {
    	return NBTHelper.getModNbt(stack) != null && NBTHelper.getModNbt(stack).hasKey(NBT_KEEPONDEATH) && NBTHelper.getNbt(stack, Reference.MOD_ID).getBoolean(NBT_KEEPONDEATH);
    }
}
