package xyz.pixelatedw.wypi.quests.objectives;

import net.minecraft.item.ItemStack;

public interface IObtainItemObjective
{
	boolean checkItem(ItemStack stack);
}
