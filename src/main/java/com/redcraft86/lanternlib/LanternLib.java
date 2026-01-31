package com.redcraft86.lanternlib;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import com.redcraft86.lanternlib.configs.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LanternLib.MOD_ID)
public class LanternLib {
    public static final String MOD_ID = "lanternlib";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LanternLib(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        context.registerConfig(ModConfig.Type.CLIENT, ClientCfg.SPEC);
        context.registerConfig(ModConfig.Type.COMMON, CommonCfg.SPEC);
    }
}