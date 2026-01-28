package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.block.RotatedPillarBlock;

public class BaseRotatedPillarBlock extends RotatedPillarBlock implements ITooltipProvider {
    public BaseRotatedPillarBlock(Properties properties) {
        super(properties);
    }
}
