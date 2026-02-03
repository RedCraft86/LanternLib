package com.redcraft86.lanternlib.configs;

import java.util.List;

import com.redcraft86.lanternlib.utils.ValidationUtils;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommonCfg {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> GRIEFING_BLACKLIST;

    static {
        GRIEFING_BLACKLIST = BUILDER.comment("List of entities that cannot grief the world.")
                .defineListAllowEmpty("griefingBlacklist", List.of("minecraft:enderman", "minecraft:fireball"),
                        ValidationUtils::isResourceLoc
                );
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean isLoaded() { return SPEC.isLoaded(); }
}