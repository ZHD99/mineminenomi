package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncWorldDataPacket;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.debug.WyDebug;
import xyz.pixelatedw.wypi.network.WyNetwork;

public class GetWantedPosterCommand
{

	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		LiteralArgumentBuilder<CommandSource> builder = Commands.literal("getwantedposter").requires(source -> source.hasPermissionLevel(2));

		builder
			.then(Commands.argument("target", EntityArgument.player())
				.executes(context ->
					{
						return giveWantedPoster(context, EntityArgument.getPlayer(context, "target")); 
					}
				)
			);
		
		dispatcher.register(builder);
	}

	private static int giveWantedPoster(CommandContext<CommandSource> context, ServerPlayerEntity player)
	{
		IEntityStats entityStatsProps = EntityStatsCapability.get(player);
		ExtendedWorldData worldData = ExtendedWorldData.get(player.world);
			
		worldData.issueBounty(player.getUniqueID().toString(), entityStatsProps.getBounty());
						
		if(WyDebug.isDebug())
			WyHelper.sendMsgToPlayer(player, TextFormatting.GREEN + "" + TextFormatting.ITALIC + "[DEBUG] A new bounty was issued on your name!");
			
		ItemStack posterStack = new ItemStack(ModBlocks.WANTED_POSTER);
		posterStack.setTag(ItemsHelper.setWantedData(player.world, player.getUniqueID().toString(), worldData.getBounty(player.getUniqueID().toString())));
		player.inventory.addItemStackToInventory(posterStack);
		
		WyNetwork.sendToAllTracking(new SSyncEntityStatsPacket(player.getEntityId(), entityStatsProps), player);
		WyNetwork.sendToAllTracking(new SSyncWorldDataPacket(worldData), player);
		
		return 1;
	}
}
