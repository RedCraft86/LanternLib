package com.redcraft86.lanternlib.patches;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import com.redcraft86.lanternlib.LanternLib;
import net.minecraft.world.entity.LivingEntity;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.*;

/**
 * Idea initially from NaNny though the implementation is slightly more basic compared to it.
 * I'm not even sure how common this problem is so this might not stay for very long unless I forget.
 * <a href="https://github.com/Vonr/NaNny">NaNny Page</a>
 */
@Mod.EventBusSubscriber(modid = LanternLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class NaNHealth {
    public static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (Float.isNaN(event.getAmount())) {
            LOGGER.warn("{} tried dealing incoming NaN Dmg to {}", event.getSource(), entity.getName().getString());
            event.setCanceled(true);
            fixHealth(entity);
        }
    }

    @SubscribeEvent
    static void onLivingDamageEvent(LivingDamageEvent event) {
        float dmg = event.getAmount();
        if (Float.isNaN(dmg)) {
            LOGGER.warn("{} tried dealing NaN Dmg to {} (pre)", event.getSource(), event.getEntity().getName().getString());
            event.setAmount(0.0f);
        }
    }

    @SubscribeEvent
    static void onLivingAttack(LivingAttackEvent event) {
        float dmg = event.getAmount();
        LivingEntity entity = event.getEntity();
        if (Float.isNaN(dmg)) {
            LOGGER.warn("{} tried dealing NaN Dmg to {} (post)", event.getSource(), entity.getName().getString());
            fixHealth(entity);
        }
    }

    @SubscribeEvent
    static void onLivingHeal(LivingHealEvent event) {
        float amount = event.getAmount();
        LivingEntity entity = event.getEntity();
        if (Float.isNaN(amount)) {
            LOGGER.warn("Tried healing NaN health to {}", entity.getName().getString());
            event.setCanceled(true);
        }
        else if (Float.isNaN(entity.getHealth())) {
            LOGGER.warn("{}'s health was healed to NaN", entity.getName().getString());
            event.setCanceled(true);
            fixHealth(entity);
        }
    }

    @SubscribeEvent
    static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (Float.isNaN(entity.getHealth())) {
            LOGGER.warn("{}'s health was set to NaN after death by source {}", entity.getName().getString(), event.getSource());
            event.setCanceled(true);
            fixHealth(entity);
        }
    }

    private static void fixHealth(LivingEntity entity) {
        entity.setHealth(entity.getMaxHealth() * 0.1f); // A fixed 10% health
        entity.setAbsorptionAmount(0);
    }
}
