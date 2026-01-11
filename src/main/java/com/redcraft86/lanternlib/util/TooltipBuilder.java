package com.redcraft86.lanternlib.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TooltipBuilder {
    private final Map<String, Component> tooltips = new HashMap<>();

    public TooltipBuilder() {}

    public MutableComponent add(String key) {
        MutableComponent component = Component.translatable(key);
        tooltips.put(key, component);
        return component;
    }

    public MutableComponent add(String key, Object... args) {
        MutableComponent component = Component.translatable(key, args);
        tooltips.put(key, component);
        return component;
    }

    @Nullable
    public Component get(String key) {
        return tooltips.getOrDefault(key, null);
    }

    public Collection<Component> build() {
        return tooltips.values();
    }
}
