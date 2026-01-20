package com.redcraft86.lanternlib.configs;

import net.minecraft.resources.ResourceLocation;

public class ValidationUtils {
    public static boolean isResourceLoc(final Object obj) {
        if (obj instanceof String str) {
            // n:p
            // 123 (3 chars minimum)
            if (str.length() < 3) {
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
        if (obj instanceof String str) {
            // #n:p
            // 1234 (4 chars minimum)
            return str.length() >= 4 && str.startsWith("#")
                    && isResourceLoc(str.substring(1));
        }
        return false;
    }

    public static boolean isResourceLocOrTag(final Object obj) {
        if (obj instanceof String str) {
            if (str.startsWith("#")) {
                str = str.substring(1);
            }
            return isResourceLoc(str);
        }
        return false;
    }
}
