package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class BaseHangingSignBlock extends CeilingHangingSignBlock implements ITooltipProvider {
    public BaseHangingSignBlock(WoodType type, Properties properties) {
        super(properties, type);
    }
}
