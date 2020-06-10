package xyz.pixelatedw.mineminenomi.entities.mobs.pirates;

import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.wypi.WyHelper;

import javax.annotation.Nullable;

public class PirateWithSwordEntity extends GenericPirateEntity
{

	public PirateWithSwordEntity(World world)
	{
		super(ModEntities.PIRATE_WITH_SWORD, world, new String[] {"pirate1", "pirate2", "pirate3", "pirate4", "pirate5"});
	}
	
	@Override
	protected void registerGoals()
	{
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, true));
	}
	
	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23F);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
		
		this.setDoriki(10 + WyHelper.randomWithRange(0, 5));
		this.setBelly(5 + WyHelper.randomWithRange(0, 5));
	}

	@Override
	protected void registerData()
	{
		super.registerData();
	}

	@Override
	@Nullable
	public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) 
	{
		spawnData = super.onInitialSpawn(world, difficulty, reason, spawnData, dataTag);
		
		ItemStack randomSword = new ItemStack(this.pirateSwords[this.rand.nextInt(this.pirateSwords.length)]);
		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, randomSword);

		return spawnData;
	}
}
