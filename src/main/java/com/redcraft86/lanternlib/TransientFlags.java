package com.redcraft86.lanternlib;

import java.util.Set;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class TransientFlags {
    private static final Set<String> flags = ConcurrentHashMap.newKeySet();

    public static void addFlag(String flag) {
        validateFlag(flag);
        flags.add(flag);
    }

    public static void removeFlag(String flag) {
        validateFlag(flag);
        flags.remove(flag);
    }

    public static boolean hasFlag(String flag) {
        validateFlag(flag);
        return flags.contains(flag);
    }

    public static boolean toggleFlag(String flag) {
        validateFlag(flag);
        if (flags.remove(flag)) {
            return false;
        } else {
            flags.add(flag);
            return true;
        }
    }

    public static Set<String> getFlags() {
        return Collections.unmodifiableSet(flags);
    }

    private static void validateFlag(String flag) {
        if (flag == null || flag.isBlank()) {
            throw new IllegalArgumentException("Flag must not be null or blank");
        }
    }
}
