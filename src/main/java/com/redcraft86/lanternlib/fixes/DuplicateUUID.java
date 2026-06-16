package com.redcraft86.lanternlib.fixes;

import java.util.Set;
import java.util.UUID;
import java.util.HashSet;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import com.redcraft86.lanternlib.LanternLib;

/**
 * Partially adapted from Duplicate Entity UUID Fix which is under the MIT license.
 * <a href="https://github.com/CAS-ual-TY/DuplicateEntityUUIDFix/blob/main/src/main/java/de/cas_ual_ty/deuf/DEUF.java">Src</a>
 */
@EventBusSubscriber(modid = LanternLib.MOD_ID)
public class DuplicateUUID {
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
                // This uuid is being matched with a different entity, so obviously it's occupied
                OCCUPIED.add(uuid);

                UUID newUUID = null;
                for (int i = 0; i < MAX_ITERATIONS; i++) {
                    newUUID = Mth.createInsecureUUID(serverLevel.getRandom());
                    if (OCCUPIED.contains(newUUID)) {
                        newUUID = null;
                    } else if (serverLevel.getEntity(newUUID) != null) {
                        OCCUPIED.add(newUUID);
                        newUUID = null;
                    } else {
                        // We found a valid uuid!!!
                        break;
                    }
                }

                if (newUUID != null) {
                    entity.setUUID(newUUID);
                    LOGGER.warn("Resolving duplicate UUID on entity {} by changing it from {} to {}",
                            entity.getName().getString(), uuid, newUUID);
                } else {
                    LOGGER.error("Failed to resolve duplicate UUID on entity {} as it exceeded max iteration of {}",
                            entity.getName().getString(), MAX_ITERATIONS);
                }
            }
        }
    }
}
