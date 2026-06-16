package com.redcraft86.lanternlib.features;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityMobGriefingEvent;

import com.redcraft86.lanternlib.configs.*;
import com.redcraft86.lanternlib.LanternLib;

@EventBusSubscriber(modid = LanternLib.MOD_ID)
public class GriefingBlacklist {
    @SubscribeEvent
    static void onMobGriefing(EntityMobGriefingEvent event) {
        Entity entity = event.getEntity();
        if (!entity.level().isClientSide()) {
            ResourceLocation resource = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            if (Configs.COMMON.griefingBlacklist.contains(resource)) {
                event.setCanGrief(false);
            }
        }
    }
}
