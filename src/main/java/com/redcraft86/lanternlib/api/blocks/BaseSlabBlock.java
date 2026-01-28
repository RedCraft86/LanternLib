package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.block.SlabBlock;

public class BaseSlabBlock extends SlabBlock implements ITooltipProvider {
    public BaseSlabBlock(Properties properties) {
        super(properties);
    }
}
