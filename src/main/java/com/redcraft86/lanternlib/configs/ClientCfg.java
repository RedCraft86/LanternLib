package com.redcraft86.lanternlib.configs;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientCfg {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue HIDE_CREATIVE_TAB_TOOLTIPS;

    public static final ModConfigSpec.BooleanValue MEMORY_USAGE_TITLE;
    public static final ModConfigSpec.ConfigValue<String> CUSTOM_TITLE;

    static {
        HIDE_CREATIVE_TAB_TOOLTIPS = BUILDER.comment("Hides the tab tooltips on items in the creative inventory like \"Functional Blocks,\" \"Natural Blocks,\" etc.")
                .comment("This stops mod name tooltips with mods like JEI or EMI from doubling up.")
                .define("hideCreativeTabTips", true);

        BUILDER.push("titleBar");

        MEMORY_USAGE_TITLE = BUILDER.comment("Show memory usage, total, and allocation in title bar.")
                .define("memoryTitle", true);

        CUSTOM_TITLE = BUILDER.comment("Show a custom string in the title bar in place of the default text.")
                .define("customTitle", "DEFAULT");

        BUILDER.pop();
    }

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean isLoaded() { return SPEC.isLoaded(); }
}
