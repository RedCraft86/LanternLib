package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class BaseFenceGateBlock extends FenceGateBlock implements ITooltipProvider {
    public BaseFenceGateBlock(WoodType type, Properties properties) {
        super(type, properties);
    }
}
