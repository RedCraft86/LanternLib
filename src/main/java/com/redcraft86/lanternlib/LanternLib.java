package com.redcraft86.lanternlib;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import com.redcraft86.lanternlib.configs.*;
import com.redcraft86.lanternlib.common.CommonPatches;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(LanternLib.MOD_ID)
public class LanternLib {
    public static final String MOD_ID = "lanternlib";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LanternLib(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        CommonPatches.apply();

        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientCfg.SPEC);
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonCfg.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }
}