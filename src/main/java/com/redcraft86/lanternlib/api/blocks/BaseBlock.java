package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.block.Block;

public class BaseBlock extends Block implements ITooltipProvider {
    public BaseBlock(Properties properties) {
        super(properties);
    }
}
