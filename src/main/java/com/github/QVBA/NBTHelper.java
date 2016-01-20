package com.github.QVBA;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;
/**
 * @author diesieben07
 */
public class NBTHelper {
	
	 public static final int TAG_COMPOUND = 10;
	
    /**
     * <p>Get the NBTTagCompound associated with the given ItemStack and initializes it if necessary.</p>
     * @param stack the ItemStack
     * @return the NBTTagCompound associated with the ItemStack
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

}
