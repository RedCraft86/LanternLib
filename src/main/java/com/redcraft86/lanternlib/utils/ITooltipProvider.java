package com.redcraft86.lanternlib.utils;

import java.util.List;
import java.util.Collections;

import net.minecraft.network.chat.Component;

public interface ITooltipProvider {
    default List<Component> getTooltips() {
        return Collections.emptyList();
    }
}
