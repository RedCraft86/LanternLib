package com.redcraft86.lanternlib.configs;

import java.util.List;

import com.redcraft86.lanternlib.util.ValidationUtils;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonCfg {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<List<? extends String>> GRIEF_BLACKLIST;

    static {
        GRIEF_BLACKLIST = BUILDER.comment("A list of entities blacklisted from griefing the world.")
                .defineListAllowEmpty("griefingBlacklist", List.of("minecraft:enderman", "minecraft:fireball"),
                        () -> "", ValidationUtils::resourceOnly);
    }

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean isLoaded() { return SPEC.isLoaded(); }
}
