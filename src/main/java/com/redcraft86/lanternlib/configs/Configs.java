package com.redcraft86.lanternlib.configs;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import me.fzzyhmstrs.fzzy_config.api.*;

public class Configs {
    public static final CommonConfig COMMON = ConfigApiJava.registerAndLoadConfig(CommonConfig::new, RegisterType.BOTH);
    public static final ClientConfig CLIENT = ConfigApiJava.registerAndLoadConfig(ClientConfig::new, RegisterType.CLIENT);
    public static final LogFilterConfig LOG_FILTER = ConfigApiJava.registerAndLoadConfig(LogFilterConfig::new, RegisterType.CLIENT);

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        if (COMMON == null) {
            LOGGER.error("[LanternLib] Failed to load CommonConfig!");
        }
        if (CLIENT == null) {
            LOGGER.error("[LanternLib] Failed to load ClientConfig!");
        }
        if (LOG_FILTER == null) {
            LOGGER.error("[LanternLib] Failed to load LogFilterConfig!");
        }
    }
}
