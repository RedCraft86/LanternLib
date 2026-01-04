package com.redcraft86.lanternlib.events;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import com.redcraft86.lanternlib.LanternLib;
import com.redcraft86.lanternlib.configs.CommonCfg;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityMobGriefingEvent;

@EventBusSubscriber(modid = LanternLib.MOD_ID)
public class ServerEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    static void onMobGrief(EntityMobGriefingEvent e) {
        Entity entity = e.getEntity();
        if (!entity.level().isClientSide()) {
            ResourceLocation resource = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            e.setCanGrief(!CommonCfg.GRIEF_BLACKLIST.get().contains(resource.toString()));
        }
    }
}
