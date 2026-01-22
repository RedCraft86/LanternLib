package com.redcraft86.lanternlib.common.events;

import com.redcraft86.lanternlib.LanternLib;
import com.redcraft86.lanternlib.configs.CommonCfg;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityMobGriefingEvent;

@EventBusSubscriber(modid = LanternLib.MOD_ID)
public final class EntityEvents {

    @SubscribeEvent
    static void onMobGriefing(EntityMobGriefingEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ResourceLocation resource = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (CommonCfg.GRIEFING_BLACKLIST.get().contains(resource.toString())) {
            event.setCanGrief(false);
        }
    }
}
