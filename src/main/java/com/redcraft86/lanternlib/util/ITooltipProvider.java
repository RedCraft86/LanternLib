package com.redcraft86.lanternlib.util;

import java.util.Map;
import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public interface ITooltipProvider {
    /** WARNING: This passes the actual object, not a copy! */
    @Nullable Map<String, Component> getTooltips();

    @Nullable default MutableComponent addTooltip(String key) {
        Map<String, Component> cache = getTooltips();
        MutableComponent newComp = null;
        if (cache != null) {
            newComp = Component.translatable(key);
            cache.put(key, newComp);
        }
        return newComp;
    }

    @Nullable default MutableComponent addTooltip(String key, Object... args) {
        Map<String, Component> cache = getTooltips();
        MutableComponent newComp = null;
        if (cache != null) {
            newComp = Component.translatable(key, args);
            cache.put(key, newComp);
        }
        return newComp;
    }

    default void appendTooltips(List<Component> tooltips) {
        Map<String, Component> cache = getTooltips();
        if (cache != null && !cache.isEmpty()) {
            tooltips.addAll(cache.values());
        }
    }
}
