package xyz.pixelatedw.mineminenomi.events.passives;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.wypi.APIConfig;

@Mod.EventBusSubscriber(modid = APIConfig.PROJECT_ID)
public class GoroPassiveEvents {

    @SubscribeEvent
    public static void onEntityAttackEvent(LivingAttackEvent event) {
        LivingEntity entity = event.getEntityLiving();
        IDevilFruit devilFruitProps = DevilFruitCapability.get(entity);

        if (!devilFruitProps.getDevilFruit().equals("goro_goro"))
            return;

        DamageSource damageSource  = event.getSource();
        if(damageSource.equals(DamageSource.LIGHTNING_BOLT) || damageSource.equals(DamageSource.IN_FIRE)) {
            entity.extinguish();
            event.setCanceled(true);
        }
    }

/*    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        IDevilFruit devilFruitProps = DevilFruitCapability.get(entity);

        if (!devilFruitProps.getDevilFruit().equals("goro_goro"))
            return;

        World world = entity.world;
        if (!world.isRemote) {
            if (entity.getHealth() - event.getAmount() <= 0) {
                event.setCanceled(true);
                world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.PLAYERS, 1, 1);
                entity.setHealth(entity.getMaxHealth() / 20);
            }
        }
    }*/
}
