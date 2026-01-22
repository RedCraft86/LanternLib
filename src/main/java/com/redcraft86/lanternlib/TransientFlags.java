package com.redcraft86.lanternlib;

import java.util.Set;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public final class TransientFlags {
    private static final Set<String> FLAGS = ConcurrentHashMap.newKeySet();

    public static void addFlag(String flag) {
        validateFlag(flag);
        FLAGS.add(flag);
    }

    public static void removeFlag(String flag) {
        validateFlag(flag);
        FLAGS.remove(flag);
    }

    public static boolean hasFlag(String flag) {
        validateFlag(flag);
        return FLAGS.contains(flag);
    }

    public static boolean toggleFlag(String flag) {
        validateFlag(flag);
        if (FLAGS.remove(flag)) {
            return false;
        } else {
            FLAGS.add(flag);
            return true;
        }
    }

    public static Set<String> getFlags() {
        return Collections.unmodifiableSet(FLAGS);
    }

    private static void validateFlag(String flag) {
        if (flag == null || flag.isBlank()) {
            throw new IllegalArgumentException("Flag must not be null or blank");
        }
    }
}
