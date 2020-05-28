package xyz.pixelatedw.mineminenomi.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.pixelatedw.mineminenomi.config.CommonConfig;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.GenericNewEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.marines.GenericMarineEntity;
import xyz.pixelatedw.mineminenomi.events.custom.BountyEvent;
import xyz.pixelatedw.mineminenomi.events.custom.DorikiEvent;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.packets.server.SEntityStatsSyncPacket;
import xyz.pixelatedw.wypi.APIConfig;
import xyz.pixelatedw.wypi.network.WyNetwork;

@Mod.EventBusSubscriber(modid = APIConfig.PROJECT_ID)
public class EventsOnGain
{

	@SubscribeEvent
	public static void onDorikiGained(DorikiEvent event)
	{
		if (event.player != null && CommonConfig.instance.isExtraHeartsEnabled())
		{
			IAttributeInstance maxHpAttribute = event.player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);

			if (event.props.getDoriki() / 100 <= 20)
				maxHpAttribute.setBaseValue(20);
			else
				maxHpAttribute.setBaseValue(event.props.getDoriki() / 100);
		}
	}

	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event)
	{
		if (event.getSource().getTrueSource() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
			IEntityStats props = EntityStatsCapability.get(player);
			LivingEntity target = event.getEntityLiving();

			IAttributeInstance attrAtk = target.getAttributes().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE);
			IAttributeInstance attrHP = target.getAttributes().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);

			int rng = player.world.rand.nextInt(3) + 1;
			int plusBelly = 0;
			long plusBounty = 0;
			double plusDoriki = 0;

			boolean targetPlayer = false;

			if (target instanceof PlayerEntity)
			{
				IEntityStats targetprops = EntityStatsCapability.get(player);

				plusDoriki = (targetprops.getDoriki() / 4) + rng;
				plusBounty = (targetprops.getBounty() / 2) + rng;
				plusBelly = targetprops.getBelly();

				targetPlayer = true;
			}
			else
			{
				if (props.isMarine() && target instanceof GenericMarineEntity)
					return;

				if (target instanceof GenericNewEntity)
				{
					GenericNewEntity entity = (GenericNewEntity) target;

					if ((props.getDoriki() / 100) > entity.getDoriki())
					{
						int x = (props.getDoriki() / 100) - entity.getDoriki();
						if (x <= 0)
							x = 1;

						plusDoriki = 1 / x;
						if (plusDoriki < 1)
							plusDoriki = 1;
					}
					else
						plusDoriki = entity.getDoriki();

					//plusDoriki *= MainConfig.modifierDorikiReward;

					plusBounty = (entity.getDoriki() * 2) + rng;
					plusBelly = entity.getBelly() + rng;
				}
				else
				{
					if (attrAtk != null && attrHP != null)
					{
						double i = attrAtk.getBaseValue();
						double j = attrHP.getBaseValue();

						plusDoriki = (int) Math.round(((i + j) / 10) / Math.PI) + rng;
						plusBounty = (int) Math.round((i + j) / 10) + rng;
						plusBelly = 1;

						plusDoriki *= CommonConfig.instance.getDorikiRewardMultiplier();

					}
					else
					{
						plusDoriki = 0;
						plusBounty = 0;
						plusBelly = 1;
					}
				}

				if (plusDoriki > 0)
				{
					if (props.getDoriki() + plusDoriki <= ModValues.MAX_DORIKI)
					{
						props.alterDoriki((int) Math.round(plusDoriki));
						DorikiEvent e = new DorikiEvent(player);
						if (MinecraftForge.EVENT_BUS.post(e))
							return;
					}
				}

				if (props.isPirate())
					if (plusBounty > 0)
						if (props.getBounty() + plusBounty < ModValues.MAX_GENERAL)
						{
							props.alterBounty(plusBounty);
							BountyEvent e = new BountyEvent(player, plusBounty);
							if (MinecraftForge.EVENT_BUS.post(e))
								return;
						}

				if (props.getBelly() + plusBelly < ModValues.MAX_GENERAL)
					props.alterBelly(plusBelly);

			}

			WyNetwork.sendTo(new SEntityStatsSyncPacket(player.getEntityId(), props), (ServerPlayerEntity) player);
		}
	}

}
