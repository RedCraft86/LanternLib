package com.redcraft86.lanternlib.configs;

import java.util.List;

import com.redcraft86.lanternlib.util.ValidationUtils;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonCfg {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> GRIEF_BLACKLIST;

    static {
        GRIEF_BLACKLIST = BUILDER.comment("A list of entities blacklisted from griefing the world.")
                .defineListAllowEmpty("griefingBlacklist",
                        List.of("minecraft:enderman", "minecraft:fireball"),
                        ValidationUtils::resourceOnly);
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean isLoaded() { return SPEC.isLoaded(); }
}
