package com.redcraft86.lanternlib.utils;

import net.minecraft.resources.ResourceLocation;

public final class ValidationUtils {
    public static boolean isResourceLoc(final Object obj) {
        if (obj instanceof ResourceLocation) {
            return true;
        }
        if (obj instanceof String str) {
            // n:p
            // 123 (3 chars minimum)
            if (str.length() < 3 || str.contains(" ")) {
                return false;
            }

            String[] id = str.split(":", 2);
            return id.length == 2 && !id[0].isBlank() && !id[1].isBlank()
                    && ResourceLocation.isValidNamespace(id[0])
                    && ResourceLocation.isValidPath(id[1]);
        }
        return false;
    }

    public static boolean isResourceTag(final Object obj) {
        if (obj instanceof ResourceLocation) {
            // There isn't an easy way to check for a tag if it's already a ResourceLocation so we'll assume it is
            return true;
        }
        if (obj instanceof String str) {
            // #n:p
            // 1234 (4 chars minimum)
            return str.length() >= 4 && !str.contains(" ") && str.startsWith("#")
                    && isResourceLoc(str.substring(1));
        }
        return false;
    }

    public static boolean isResourceLocOrTag(final Object obj) {
        if (obj instanceof ResourceLocation) {
            return true;
        }
        if (obj instanceof String str) {
            if (str.contains(" ")) {
                return false;
            }
            if (str.startsWith("#")) {
                str = str.substring(1);
            }
            return isResourceLoc(str);
        }
        return false;
    }
}
