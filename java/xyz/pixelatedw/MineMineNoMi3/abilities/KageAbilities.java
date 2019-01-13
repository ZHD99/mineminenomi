package xyz.pixelatedw.MineMineNoMi3.abilities;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MovingObjectPosition;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.KageProjectiles;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.KageProjectiles.TsunotokagePillar;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.EntityDoppelman;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;
import xyz.pixelatedw.MineMineNoMi3.lists.ListExtraAttributes;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketPlayer;

public class KageAbilities
{

	public static Ability[] abilitiesArray = new Ability[]
	{
			new Doppelman(), new Kagemusha(), new BrickBat(), new BlackBox(), new Tsunotokage()
	};

	public static class BrickBat extends Ability
	{
		public BrickBat()
		{
			super(ListAttributes.BRICKBAT);
		}

		public void use(EntityPlayer player)
		{
			this.projectile = new KageProjectiles.BrickBat(player.worldObj, player, attr);
			super.use(player);
		}
	}

	public static class Kagemusha extends Ability
	{
		public Kagemusha()
		{
			super(ListAttributes.KAGEMUSHA);
		}

		public void use(EntityPlayer player)
		{
			if (!WyHelper.getEntitiesNear(player, 20, EntityDoppelman.class).isEmpty())
			{
				EntityDoppelman dopp = (EntityDoppelman) WyHelper.getEntitiesNear(player, 20, EntityDoppelman.class).stream().findFirst().orElse(null);

				if (dopp != null)
				{
					int[] auxPos = new int[]
					{
							(int) player.posX, (int) player.posY, (int) player.posZ
					};
					player.setPositionAndUpdate(dopp.posX, dopp.posY, dopp.posZ);
					dopp.setPositionAndUpdate(auxPos[0], auxPos[1], auxPos[2]);
				}
			}
			else
			{
				WyHelper.sendMsgToPlayer(player, "Your Doppelman is too far away");
			}
			super.use(player);
		}
	}

	public static class Doppelman extends Ability
	{
		private EntityDoppelman doppelman;

		public Doppelman()
		{
			super(ListAttributes.DOPPELMAN);
		}

		public void passive(EntityPlayer player)
		{			
			if(this.passiveActive && player.isSneaking() && doppelman != null)
			{
				doppelman.isAggressive = !doppelman.isAggressive;
				WyHelper.sendMsgToPlayer(player, "Your Doppelman is now " + (doppelman.isAggressive ? "aggressive" : "defensive"));
			}
			else
				super.passive(player);
		}
		
		public void startPassive(EntityPlayer player)
		{
			doppelman = new EntityDoppelman(player.worldObj, player);
			doppelman.setPositionAndRotation(player.posX, player.posY, player.posZ, 180, 0);
			player.worldObj.spawnEntityInWorld(doppelman);
		}

		public void endPassive(EntityPlayer player)
		{
			if (!WyHelper.getEntitiesNear(player, 20, EntityDoppelman.class).isEmpty())
				WyHelper.getEntitiesNear(player, 20, EntityDoppelman.class).forEach(x -> x.setDead());

			this.startCooldown();
			this.startExtUpdate(player);
		}

	}

	public static class BlackBox extends Ability
	{
		public BlackBox()
		{
			super(ListAttributes.BLACKBOX);
		}

		public void use(EntityPlayer player)
		{
			this.projectile = new KageProjectiles.BlackBox(player.worldObj, player, attr);
			super.use(player);
		}
	}

	public static class Tsunotokage extends Ability
	{
		public Tsunotokage()
		{
			super(ListAttributes.TSUNOTOKAGE);
		}

		public void use(EntityPlayer player)
		{
			if (!this.isOnCooldown)
			{
				MovingObjectPosition mop = WyHelper.rayTraceBlocks(player);

				if (mop != null)
				{
					double i = mop.blockX;
					double j = mop.blockY;
					double k = mop.blockZ;

					TsunotokagePillar pillar = new TsunotokagePillar(player.worldObj, player, ListExtraAttributes.TSUNOTOKAGEPILLAR);
					pillar.setLocationAndAngles(i, j + 1, k, 0, 0);
					pillar.motionX = 0;
					pillar.motionZ = 0;
					pillar.motionY = 0.7;
					player.worldObj.spawnEntityInWorld(pillar);
				}

				super.use(player);
			}
		}
	}
}
