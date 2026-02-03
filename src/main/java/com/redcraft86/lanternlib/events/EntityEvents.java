package com.redcraft86.lanternlib.events;

import com.redcraft86.lanternlib.LanternLib;
import com.redcraft86.lanternlib.configs.CommonCfg;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = LanternLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class EntityEvents {

    @SubscribeEvent
    static void onMobGriefing(EntityMobGriefingEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ResourceLocation resource = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (resource != null && CommonCfg.GRIEFING_BLACKLIST.get().contains(resource.toString())) {
            event.setResult(Event.Result.DENY);
        }
    }
}
