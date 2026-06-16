package com.redcraft86.lanternlib.configs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

import me.fzzyhmstrs.fzzy_config.config.*;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;

import com.redcraft86.lanternlib.LanternLib;

public class CommonConfig extends Config {
    public CommonConfig() {
        super(ResourceLocation.fromNamespaceAndPath(LanternLib.MOD_ID, "common"));
    }

    public ValidatedList<ResourceLocation> griefingBlacklist = ValidatedIdentifier
            .ofRegistry(ResourceLocation.parse("creeper"), BuiltInRegistries.ENTITY_TYPE)
            .toList(ResourceLocation.parse("enderman"), ResourceLocation.parse("fireball"));
}
