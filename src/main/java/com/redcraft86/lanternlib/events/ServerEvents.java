package com.redcraft86.lanternlib.events;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import com.redcraft86.lanternlib.LanternLib;
import com.redcraft86.lanternlib.configs.CommonCfg;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = LanternLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    static void onMobGrief(EntityMobGriefingEvent e) {
        Entity entity = e.getEntity();
        if (!entity.level().isClientSide()) {
            ResourceLocation resource = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
            if (resource != null && CommonCfg.GRIEF_BLACKLIST.get().contains(resource.toString())) {
                e.setResult(Event.Result.DENY);
            }
        }
    }
}