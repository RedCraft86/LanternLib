package com.redcraft86.lanternlib.patches;

import java.util.Set;
import java.util.UUID;
import java.util.HashSet;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import com.redcraft86.lanternlib.LanternLib;

import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

/**
 * Partially adapted from Duplicate Entity UUID Fix which is under the MIT license.
 * <a href="https://github.com/CAS-ual-TY/DuplicateEntityUUIDFix/blob/main/src/main/java/de/cas_ual_ty/deuf/DEUF.java">Src</a>
 */
@Mod.EventBusSubscriber(modid = LanternLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class DuplicateUUID {
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final Set<UUID> OCCUPIED = new HashSet<>();
    private static final int MAX_ITERATIONS = 100;

    @SubscribeEvent
    static void onEntityJoin(EntityJoinLevelEvent event) {
        Level level = event.getLevel();
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            Entity entity = event.getEntity();
            if (entity instanceof Player) {
                return;
            }

            UUID uuid = entity.getUUID();
            Entity otherEntity = serverLevel.getEntity(uuid);
            if (otherEntity != null && otherEntity != entity) {
                OCCUPIED.add(uuid);

                UUID newUUID;
                int iterations = 0;
                do {
                    newUUID = Mth.createInsecureUUID(serverLevel.getRandom());
                    if (OCCUPIED.contains(newUUID)) {
                        newUUID = null;
                    } else if (serverLevel.getEntity(newUUID) != null) {
                        OCCUPIED.add(newUUID);
                        newUUID = null;
                    }
                    iterations++;
                } while (newUUID == null && iterations < MAX_ITERATIONS);

                if (newUUID != null) {
                    LOGGER.warn("Resolving duplicate UUID on entity {} by changing it from {} to {}",
                            entity.getName().getString(), uuid, newUUID);

                    entity.setUUID(newUUID);
                } else {
                    LOGGER.error("Failed to resolve duplicate UUID on entity {} as it exceeded max iteration of {}",
                            entity.getName().getString(), MAX_ITERATIONS);
                }
            }
        }
    }
}
