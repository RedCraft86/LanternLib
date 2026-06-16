package com.redcraft86.lanternlib.configs;

import net.minecraft.resources.ResourceLocation;

import me.fzzyhmstrs.fzzy_config.config.*;
import me.fzzyhmstrs.fzzy_config.annotations.*;

import com.redcraft86.lanternlib.LanternLib;

public class ClientConfig extends Config {
    public ClientConfig() {
        super(ResourceLocation.fromNamespaceAndPath(LanternLib.MOD_ID, "client"));
    }

    public TitleSection TitleBar = new TitleSection();
    public static class TitleSection extends ConfigSection {
        public TitleSection() {
            super();
        }

        @Comment("Show the game client's current memory usage, max, and allocation.")
        public boolean showMemory = true;

        @Comment("Custom text to replace the game's title bar. Leave empty to not change.")
        public String customText = "";
    }

    @Comment("""
            Disables tooltips in the creative mode player inventory from containing the tab name of that item.
            Fixes the issue where if a mod like ModNameTooltip or JEI is active, the mod names double up in creative.
        """)
    public boolean disableTabTooltips = true;
}
