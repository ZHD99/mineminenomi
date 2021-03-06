package xyz.pixelatedw.wypi.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import xyz.pixelatedw.wypi.APIConfig;
import xyz.pixelatedw.wypi.abilities.events.AbilityEvent;
import xyz.pixelatedw.wypi.data.quest.IQuestData;
import xyz.pixelatedw.wypi.data.quest.QuestDataCapability;
import xyz.pixelatedw.wypi.network.WyNetwork;
import xyz.pixelatedw.wypi.network.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.wypi.quests.Quest;
import xyz.pixelatedw.wypi.quests.objectives.IAbilityUseObjective;
import xyz.pixelatedw.wypi.quests.objectives.IBrewPotionObjective;
import xyz.pixelatedw.wypi.quests.objectives.IEntityInteractObjective;
import xyz.pixelatedw.wypi.quests.objectives.IEquipItemObjective;
import xyz.pixelatedw.wypi.quests.objectives.IHitEntityObjective;
import xyz.pixelatedw.wypi.quests.objectives.IKillEntityObjective;
import xyz.pixelatedw.wypi.quests.objectives.IObtainItemObjective;
import xyz.pixelatedw.wypi.quests.objectives.Objective;

@Mod.EventBusSubscriber(modid = APIConfig.PROJECT_ID)
public class QuestEvents
{
	@SubscribeEvent
	public static void onPlayerAbilityUse(AbilityEvent.Use event)
	{
		if(!event.getPlayer().world.isRemote)
		{	
			PlayerEntity player = event.getPlayer();
			IQuestData questProps = QuestDataCapability.get(player);
			
			for (Objective obj : getObjectives(questProps))
			{
				if (obj instanceof IAbilityUseObjective)
				{
					if (((IAbilityUseObjective) obj).checkAbility(player, event.getAbility()))
					{
						obj.alterProgress(1);
						WyNetwork.sendTo(new SSyncQuestDataPacket(player.getEntityId(), questProps), player);
					}
				} 
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerBrews(PlayerBrewedPotionEvent event)
	{
		if(event.getPlayer().world.isRemote)
			return;
		
		PlayerEntity player = event.getPlayer();
		IQuestData questProps = QuestDataCapability.get(player);
		
		for (Objective obj : getObjectives(questProps))
		{
			if (obj instanceof IBrewPotionObjective)
			{
				if (((IBrewPotionObjective) obj).checkPotion(player, event.getStack()))
				{
					obj.alterProgress(1);
					WyNetwork.sendTo(new SSyncQuestDataPacket(player.getEntityId(), questProps), player);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerEquips(PlayerTickEvent event)
	{
		if(event.side == LogicalSide.SERVER)
		{
			PlayerEntity player = event.player;
			IQuestData questProps = QuestDataCapability.get(player);
			ItemStack heldItem = player.getHeldItem(player.getActiveHand());
			
			boolean hasEquipment = player.inventory.armorInventory.stream().anyMatch(itemStack -> !itemStack.isEmpty());

			if(hasEquipment)
			{
				for (Objective obj : getObjectives(questProps))
				{
					if (obj instanceof IEquipItemObjective)
					{
						if (((IEquipItemObjective) obj).checkEquippedItem(player, heldItem))
						{
							obj.alterProgress(1);
							WyNetwork.sendTo(new SSyncQuestDataPacket(player.getEntityId(), questProps), player);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onEntityDies(LivingDeathEvent event)
	{
		if (!(event.getSource().getTrueSource() instanceof PlayerEntity) || event.getSource().getTrueSource().world.isRemote)
			return;

		PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
		LivingEntity target = event.getEntityLiving();
		IQuestData questProps = QuestDataCapability.get(player);

		for (Objective obj : getObjectives(questProps))
		{
			if (obj instanceof IKillEntityObjective)
			{
				if (((IKillEntityObjective) obj).checkKill(player, target, event.getSource()))
				{
					obj.alterProgress(1);
					WyNetwork.sendTo(new SSyncQuestDataPacket(player.getEntityId(), questProps), player);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onHitEntity(LivingHurtEvent event)
	{
		if (!(event.getSource().getTrueSource() instanceof PlayerEntity) || event.getSource().getTrueSource().world.isRemote)
			return;
		
		PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
		LivingEntity target = event.getEntityLiving();
		IQuestData questProps = QuestDataCapability.get(player);
		
		for (Objective obj : getObjectives(questProps))
		{
			if (obj instanceof IHitEntityObjective)
			{
				if (((IHitEntityObjective) obj).checkHit(player, target, event.getSource()))
				{
					obj.alterProgress(1);
					WyNetwork.sendTo(new SSyncQuestDataPacket(player.getEntityId(), questProps), player);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onItemPickedUp(EntityItemPickupEvent event)
	{
		PlayerEntity player = event.getPlayer();
		IQuestData questProps = QuestDataCapability.get(player);

		if(player.world.isRemote)
			return;
		
		for (Objective obj : getObjectives(questProps))
		{
			if (obj instanceof IObtainItemObjective)
			{
				if (((IObtainItemObjective) obj).checkItem(event.getItem().getItem()))
				{
					obj.alterProgress(event.getItem().getItem().getCount());
					WyNetwork.sendTo(new SSyncQuestDataPacket(player.getEntityId(), questProps), player);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onItemTossed(ItemTossEvent event)
	{
		PlayerEntity player = event.getPlayer();
		IQuestData questProps = QuestDataCapability.get(player);
		
		if(player.world.isRemote)
			return;
		
		for (Objective obj : getObjectives(questProps))
		{
			if (obj instanceof IObtainItemObjective)
			{
				if (((IObtainItemObjective) obj).checkItem(event.getEntityItem().getItem()) && obj.getProgress() > 0)
				{
					obj.alterProgress(-event.getEntityItem().getItem().getCount());
					WyNetwork.sendTo(new SSyncQuestDataPacket(player.getEntityId(), questProps), player);
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onEntityInteract(PlayerInteractEvent.EntityInteractSpecific event)
	{
		PlayerEntity player = event.getPlayer();
		IQuestData questProps = QuestDataCapability.get(player);

		if(player.world.isRemote)
			return;
		
		boolean restartQuest = false; 
		
		for(Quest quest : questProps.getInProgressQuests())
		{
			if(quest != null && quest.checkRestart(player))
			{
				for (Objective obj : getObjectives(questProps))
				{
					obj.setProgress(0);
				}
				quest.triggerStartEvent(player);
				restartQuest = true;
				break;
			}
		}

		if(restartQuest)
			return;
					
		for (Objective obj : getObjectives(questProps))
		{
			if (obj instanceof IEntityInteractObjective)
			{
				if (((IEntityInteractObjective) obj).checkInteraction(player, event.getTarget()))
				{
					obj.alterProgress(1);
					WyNetwork.sendTo(new SSyncQuestDataPacket(player.getEntityId(), questProps), player);
				}
			}
		}
	}

	private static List<Objective> getObjectives(IQuestData questProps)
	{
		List<Objective> objectives = new ArrayList<Objective>();

		for (Quest quest : questProps.getInProgressQuests())
		{
			if(quest == null)
				continue;
			
			if (!quest.isComplete())
			{
				for (Objective obj : quest.getObjectives())
				{
					if (!obj.isHidden() && !obj.isLocked() && !obj.isComplete())
					{
						objectives.add(obj);
					}
				}
			}
		}

		return objectives;
	}

}
