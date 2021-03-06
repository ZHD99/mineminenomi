package xyz.pixelatedw.mineminenomi.entities.projectiles.suke;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.api.abilities.ExplosionAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.common.CommonExplosionParticleEffect;
import xyz.pixelatedw.wypi.abilities.projectiles.AbilityProjectileEntity;

public class ShishaNoTeProjectile extends AbilityProjectileEntity
{
	public ShishaNoTeProjectile(World world)
	{
		super(SukeProjectiles.SHISHA_NO_TE, world);
	}

	public ShishaNoTeProjectile(EntityType type, World world)
	{
		super(type, world);
	}

	public ShishaNoTeProjectile(World world, double x, double y, double z)
	{
		super(SukeProjectiles.SHISHA_NO_TE, world, x, y, z);
	}

	public ShishaNoTeProjectile(World world, LivingEntity player)
	{
		super(SukeProjectiles.SHISHA_NO_TE, world, player);

		this.setDamage(18);
		
		this.onBlockImpactEvent = this::onBlockImpactEvent;
	}
	
	private void onBlockImpactEvent(BlockPos hit)
	{		
		ExplosionAbility explosion = AbilityHelper.newExplosion(this.getThrower(), hit.getX(), hit.getY(), hit.getZ(), 3);
		explosion.setExplosionSound(true);
		explosion.setDamageOwner(false);
		explosion.setDestroyBlocks(true);
		explosion.setFireAfterExplosion(false);
		explosion.setSmokeParticles(new CommonExplosionParticleEffect(2));
		explosion.setDamageEntities(true);
		explosion.doExplosion();
	}
}
