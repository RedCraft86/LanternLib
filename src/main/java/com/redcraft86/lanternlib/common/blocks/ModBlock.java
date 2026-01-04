package com.redcraft86.lanternlib.common.blocks;

import java.util.Map;
import java.util.LinkedHashMap;

import com.redcraft86.lanternlib.util.ITooltipProvider;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

public class ModBlock extends Block implements ITooltipProvider {
    protected Map<String, Component> cachedTooltips;

    public ModBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Map<String, Component> getTooltips() {
        if (cachedTooltips == null) {
            cachedTooltips = new LinkedHashMap<>();
        }
        return cachedTooltips;
    }
}