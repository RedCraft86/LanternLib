package com.redcraft86.lanternlib.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class TranslationUtils {
    public static String keyString(String type, String modId, String key) {
        return String.format("%s.%s.%s", type, modId, key);
    }

    public static MutableComponent component(String type, String modId, String key) {
        return Component.translatable(keyString(type, modId, key));
    }
}
