package com.redcraft86.lanternlib.common.patches;

import java.util.Map;
import java.util.HashMap;

import com.redcraft86.lanternlib.LanternLib;

import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffect;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Partially adapted from Dimensional Sycn (Sync?) Fixes which is under the LGPL-3 license.
 * Just like NaN Health, I'm not sure how common this problem is so it may get removed too if I remember to do it.
 * <a href="https://github.com/MCTeamPotato/DimensionalSyncFixes/blob/1.20.1/src/main/java/com/teampotato/dimensionalsycnfixes/DimensionalSycnFixes.java">Src</a>
 */
@EventBusSubscriber(modid = LanternLib.MOD_ID)
public final class DimTravelSync {
    @SubscribeEvent
    static void onEntityJoin(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide()) {
            return;
        }

        player.giveExperiencePoints(0);
        Map<Holder<MobEffect>, MobEffectInstance> effects = new HashMap<>(player.getActiveEffectsMap());
        effects.forEach((effect, instance) -> {
            player.removeEffect(effect);
            player.addEffect(instance);
        });
    }
}
