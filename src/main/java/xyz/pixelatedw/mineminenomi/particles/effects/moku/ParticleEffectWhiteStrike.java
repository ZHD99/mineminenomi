package xyz.pixelatedw.mineminenomi.particles.effects.moku;

import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ParticleEffectWhiteStrike extends ParticleEffect
{

	@Override
	public void spawn(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
	{
		/*
		Timer timer = new Timer(true); 
		
		CustomParticle cp = new CustomParticle(player.world, ModParticleTextures.MOKU,
				posX, 
				posY - 0.8, 
				posZ, 
				0, 0, 0)
				.setParticleScale(4F);
		Minecraft.getInstance().particles.addEffect(cp);

		timer.schedule(ParticleTaskWave.Create(player, cp.getPos().getX(), cp.getPos().getY(), cp.getPos().getZ(), cp, 15), 0);
		*/
	}
	
}