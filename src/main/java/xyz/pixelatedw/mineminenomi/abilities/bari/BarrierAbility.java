package xyz.pixelatedw.mineminenomi.abilities.bari;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.block.AirBlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.block.FoliageBlockProtectionRule;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.wypi.APIConfig.AbilityCategory;
import xyz.pixelatedw.wypi.abilities.ContinuousAbility;

public class BarrierAbility extends ContinuousAbility
{
	public static final BarrierAbility INSTANCE = new BarrierAbility();

	private static final BlockProtectionRule GRIEF_RULE = new BlockProtectionRule(AirBlockProtectionRule.INSTANCE, FoliageBlockProtectionRule.INSTANCE).setBypassGriefRule(); 
	private List<BlockPos> posList = new ArrayList<BlockPos>();
	
	public BarrierAbility()
	{
		super("Barrier", AbilityCategory.DEVIL_FRUIT);
		this.setThreshold(30);
		this.setDescription("The user creates an impenetrable wall that shields them from attacks.");
		
		this.onStartContinuityEvent = this::onStartContinuityEvent;
		this.onEndContinuityEvent = this::onEndContinuityEvent;
	}
	
	private boolean onStartContinuityEvent(PlayerEntity player)
	{
		Direction dir = Direction.getFacingDirections(player)[0];

		if (this.posList.isEmpty())
		{
			if (dir == Direction.NORTH)
				this.posList.addAll(AbilityHelper.createFilledCube(player.world, player.posX, player.posY, player.posZ - 4, 3, 4, 1, ModBlocks.BARRIER, GRIEF_RULE));
			if (dir == Direction.SOUTH)
				this.posList.addAll(AbilityHelper.createFilledCube(player.world, player.posX, player.posY, player.posZ + 4, 3, 4, 1, ModBlocks.BARRIER, GRIEF_RULE));
			if (dir == Direction.EAST)
				this.posList.addAll(AbilityHelper.createFilledCube(player.world, player.posX + 4, player.posY, player.posZ, 1, 4, 3, ModBlocks.BARRIER, GRIEF_RULE));
			if (dir == Direction.WEST)
				this.posList.addAll(AbilityHelper.createFilledCube(player.world, player.posX - 4, player.posY, player.posZ, 1, 4, 3, ModBlocks.BARRIER, GRIEF_RULE));
		}
		
		return true;	
	}
	
	private boolean onEndContinuityEvent(PlayerEntity player)
	{
		for (BlockPos pos : this.posList)
		{
			Block currentBlock = player.world.getBlockState(pos).getBlock();
			if (currentBlock == ModBlocks.BARRIER)
				player.world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		this.posList = new ArrayList<BlockPos>();

		int cooldown = (int) Math.round(this.continueTime / 50.0);
		this.setMaxCooldown(cooldown);
		
		return true;
	}
}
