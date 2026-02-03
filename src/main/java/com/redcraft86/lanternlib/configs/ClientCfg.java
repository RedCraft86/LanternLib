package com.redcraft86.lanternlib.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientCfg {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue MEMORY_TITLE_BAR;
    public static final ForgeConfigSpec.ConfigValue<String> CUSTOM_TITLE_BAR;

    public static final ForgeConfigSpec.BooleanValue DISABLE_TAB_TOOLTIPS;

    static {
        BUILDER.push("titleBar");

        MEMORY_TITLE_BAR = BUILDER.comment("Whether to show the game client's current memory usage, max, and allocation.")
                .define("memoryTitleBar", true);

        CUSTOM_TITLE_BAR = BUILDER.comment("Custom text to use in the game's title bar. Leave empty for default.")
                .define("customTitleBar", "");

        BUILDER.pop();

        BUILDER.push("misc");

        DISABLE_TAB_TOOLTIPS = BUILDER
                .comment("Disables item tooltips in the creative mode player inventory from containing the tab name of that item.")
                .comment("This fixes the issue where if a mod like ModNameTooltip or JEI is running, the mod names double up in creative.")
                .define("disableTabTooltips", true);

        BUILDER.pop();
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean isLoaded() { return SPEC.isLoaded(); }
}