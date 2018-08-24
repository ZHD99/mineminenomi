package xyz.pixelatedw.MineMineNoMi3.abilities;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.DevilFruitsHelper;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.math.ISphere;
import xyz.pixelatedw.MineMineNoMi3.api.math.Sphere;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.blocks.BlockOpeMid;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.OpeProjectiles;
import xyz.pixelatedw.MineMineNoMi3.ieep.ExtendedEntityStats;
import xyz.pixelatedw.MineMineNoMi3.items.Heart;
import xyz.pixelatedw.MineMineNoMi3.items.weapons.ItemCoreWeapon;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketPlayer;

public class OpeAbilities
{
	
	public static Ability[] abilitiesArray = new Ability[] {new Room(), new Mes(), new CounterShock(), new GammaKnife(), new Takt(), new Shambles(), new InjectionShot()};
	
	public static class InjectionShot extends Ability
	{
		public InjectionShot() 
		{
			super(ListAttributes.INJECTIONSHOT); 
		}
		
		public void use(EntityPlayer player)
		{
			if(DevilFruitsHelper.isEntityInRoom(player))
			{		
				if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemCoreWeapon)
				{
					if(!this.isOnCooldown)
					{
						double mX = (double)(-MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4);
						double mZ = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4);
							
						double f2 = MathHelper.sqrt_double(mX * mX + player.motionY * player.motionY + mZ * mZ);
						mX /= (double)f2;
						mZ /= (double)f2;
						mX += player.worldObj.rand.nextGaussian() * 0.007499999832361937D * 1.0;
						mZ += player.worldObj.rand.nextGaussian() * 0.007499999832361937D * 1.0;
						mX *= 3;
						mZ *= 3;
					
						motion("=", mX, player.motionY, mZ, player);
						
						Minecraft.getMinecraft().thePlayer.swingItem();
					}
					
					super.use(player);
				}
				else
					WyHelper.sendMsgToPlayer(player, "You need a sword to use this ability !");
			}
			else
				WyHelper.sendMsgToPlayer(player, "" + this.getAttribute().getAttributeName() + " can only be used inside ROOM !");			
		}
		
	    public void duringCooldown(EntityPlayer player, int currentCooldown)
	    {
			if(currentCooldown > 13 * 20)
			{
				for(EntityLivingBase e : WyHelper.getEntitiesNear(player, 1.6))
				{
					e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) player), 20);
					
					e.addPotionEffect(new PotionEffect(Potion.poison.id, 10 * 20, 0));
					e.addPotionEffect(new PotionEffect(Potion.confusion.id, 10 * 20, 0));
				}
			}
	    }
	}
	
	public static class Takt extends Ability
	{
		public Takt() 
		{
			super(ListAttributes.TAKT); 
		}
		
		public void passive(EntityPlayer player)
		{
			if(!this.isPassiveActive())
			{
				if(DevilFruitsHelper.isEntityInRoom(player))
					super.passive(player);
				else
					WyHelper.sendMsgToPlayer(player, "" + this.getAttribute().getAttributeName() + " can only be used inside ROOM !");
			}		
			else
			{
				super.passive(player);
			}
		}
		
		public void duringPassive(EntityPlayer player, int passiveTimer) 
		{
			if(!DevilFruitsHelper.isEntityInRoom(player))
			{
				this.setPassiveActive(false);
				this.setCooldownActive(true);
				this.endPassive(player);
			}	
			
			if(passiveTimer >= 8)
			{
				this.setPassiveActive(false);
				this.setCooldownActive(true);
				this.endPassive(player);
			}
			
			for(EntityLivingBase entity : WyHelper.getEntitiesNear(player, 40))
			{
				if(DevilFruitsHelper.isEntityInRoom(entity) && !entity.equals(player) )
				{					
					if(!player.worldObj.isAirBlock((int)entity.posX, (int)entity.posY - 1, (int)entity.posZ) || !player.worldObj.isAirBlock((int)entity.posX, (int)entity.posY - 2, (int)entity.posZ)
							|| !player.worldObj.isAirBlock((int)entity.posX, (int)entity.posY - 3, (int)entity.posZ))
					{
						entity.motionX = 0;
						entity.motionY += 0.15;
						entity.motionZ = 0;
						entity.fallDistance = 0;
					}
				}
			}
		}
		
		public void endPassive(EntityPlayer player) 
		{
			this.startCooldown();
			this.startExtUpdate(player);
		}
	}
	
	public static class Shambles extends Ability
	{
		public Shambles() 
		{
			super(ListAttributes.SHAMBLES); 
		}
		
		public void use(EntityPlayer player)
		{
			if(DevilFruitsHelper.isEntityInRoom(player))
			{			
				if(!this.isOnCooldown)
				{
					EntityLivingBase prevEntity = null;		
					Random r = player.worldObj.rand;
					
					int loops = r.nextInt(2) + 1;
					
					for(int i = 0; i < loops + 1; i++)
					{
						for(EntityLivingBase entity : WyHelper.getEntitiesNear(player, 40))
						{
							if(DevilFruitsHelper.isEntityInRoom(entity) )
							{
								if(prevEntity == null)
									prevEntity = entity;
								else
								{
									int nPosX = (int) prevEntity.posX;
									int nPosY = (int) prevEntity.posY;
									int nPosZ = (int) prevEntity.posZ;
									
									prevEntity.setPosition(entity.posX + r.nextInt(5), entity.posY, entity.posZ + r.nextInt(5));
									entity.setPosition(nPosX + r.nextInt(5), nPosY, nPosZ + r.nextInt(5));
								}
							}
						}
					}
				}
				
				super.use(player);
			}
			else
				WyHelper.sendMsgToPlayer(player, "" + this.getAttribute().getAttributeName() + " can only be used inside ROOM !");			
		}
	}
	
	public static class GammaKnife extends Ability
	{
		public GammaKnife() 
		{
			super(ListAttributes.GAMMAKNIFE); 
		}
		
		public void use(EntityPlayer player)
		{
			if(DevilFruitsHelper.isEntityInRoom(player))
			{			
				this.projectile = new OpeProjectiles.GammaKnife(player.worldObj, player, attr);
				super.use(player);
			}
			else
				WyHelper.sendMsgToPlayer(player, "" + this.getAttribute().getAttributeName() + " can only be used inside ROOM !");			
		}
	}
	
	public static class Mes extends Ability
	{
		public Mes() 
		{
			super(ListAttributes.MES); 
		}	
		
		public void hitEntity(EntityPlayer player, EntityLivingBase target) 
		{
			ExtendedEntityStats targetprops = ExtendedEntityStats.get(target);
			
			if(targetprops.hasHeart())
			{
		        ItemStack heart = new ItemStack(ListMisc.Heart);
		        ((Heart)heart.getItem()).setHeartOwner(heart, target);
		        heart.setStackDisplayName(target.getCommandSenderName() + "'s Heart");
		        
		        player.inventory.addItemStackToInventory(heart);
		        
		        targetprops.setHasHeart(false);
			}
			
			super.hitEntity(player, target);
		}
	}
	
	public static class CounterShock extends Ability
	{
		public CounterShock() 
		{
			super(ListAttributes.COUNTERSHOCK); 
		}
		
		public void use(EntityPlayer player)
		{
			this.projectile = new OpeProjectiles.CounterShock(player.worldObj, player, attr);
			super.use(player);
		}
	}
	
	public static class Room extends Ability
	{
		private boolean canSpawnRoom = true;
		
		public Room() 
		{
			super(ListAttributes.ROOM); 
		}
		
		public void use(EntityPlayer player)
		{
			if(!this.isOnCooldown && canSpawnRoom)
			{
				final World world = player.worldObj;
				Sphere.generate((int)player.posX, (int)player.posY, (int)player.posZ, 20, new ISphere()
				{
					public void call(int x, int y, int z)
					{
						DevilFruitsHelper.placeIfCanReplaceBlock(world, x, y ,z, ListMisc.Ope);
					}
				});
				player.worldObj.setBlock((int) player.posX, (int) player.posY, (int) player.posZ, ListMisc.OpeMid);
				
				canSpawnRoom = false;
				super.use(player);
			}
			else if(!canSpawnRoom)
			{
				if(WyHelper.isBlockNearby(player, 20, ListMisc.OpeMid))
				{
					Block b = WyHelper.getBlockNearby(player, 20, ListMisc.OpeMid);
					((BlockOpeMid)b).clearRoom();
					canSpawnRoom = true;
				}
			}
		} 
		
		public void alterSpawnFlag(boolean flag)
		{
			canSpawnRoom = flag;
		}
	}

	private static void motion(String c, double x, double y, double z, EntityPlayer p)
	{
		WyNetworkHelper.sendTo(new PacketPlayer("motion" + c, x, y, z), (EntityPlayerMP) p);
	}
}
