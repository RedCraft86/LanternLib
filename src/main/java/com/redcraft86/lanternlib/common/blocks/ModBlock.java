package com.redcraft86.lanternlib.common.blocks;

import com.redcraft86.lanternlib.util.TooltipBuilder;

import net.minecraft.world.level.block.Block;

public class ModBlock extends Block {
    private final TooltipBuilder tooltips = new TooltipBuilder();

    public ModBlock(Properties properties) {
        super(properties);
    }

    public TooltipBuilder getTooltips() {
        return tooltips;
    }
}
