package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BaseStairBlock extends StairBlock implements ITooltipProvider {
    public BaseStairBlock(BlockState baseState, Properties properties) {
        super(baseState, properties);
    }
}
