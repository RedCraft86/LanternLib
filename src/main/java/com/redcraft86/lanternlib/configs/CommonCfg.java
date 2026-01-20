package com.redcraft86.lanternlib.configs;

import java.util.List;

import com.redcraft86.lanternlib.utils.ValidationUtils;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonCfg {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<List<? extends String>> GRIEFING_BLACKLIST;

    static {
        GRIEFING_BLACKLIST = BUILDER.comment("List of entities that cannot grief the world.")
                .defineListAllowEmpty("griefingBlacklist", List.of("minecraft:enderman", "minecraft:fireball"),
                        () -> "minecraft:", ValidationUtils::isResourceLoc
                );
    }

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean isLoaded() { return SPEC.isLoaded(); }
}
