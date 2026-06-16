package com.redcraft86.lanternlib;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.IEventBus;

import com.redcraft86.lanternlib.configs.Configs;

@Mod(LanternLib.MOD_ID)
public final class LanternLib {
    public static final String MOD_ID = "lanternlib";

    public LanternLib(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);

        Configs.init();
        LogFilter.getOrInit();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
