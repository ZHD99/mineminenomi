package xyz.pixelatedw.MineMineNoMi3.quests;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

public interface ITimedQuest
{

	@SideOnly(Side.CLIENT)
	void onTimePassEvent(EntityPlayer player);
	
}
