package com.redcraft86.lanternlib.util;

import net.minecraft.resources.ResourceLocation;

public class ValidationUtils {
    public static boolean resourceOnly(final Object obj) {
        if (obj instanceof String str) {
            String[] id = str.split(":", 2);
            return id.length == 2 && !id[0].isBlank() && !id[1].isBlank()
                    && ResourceLocation.isValidNamespace(id[0])
                    && ResourceLocation.isValidPath(id[1]);
        }
        return false;
    }

    public static boolean resourceOrTag(final Object obj) {
        return obj instanceof String str && resourceOnly(str.substring(str.startsWith("#") ? 1 : 0));
    }
}
