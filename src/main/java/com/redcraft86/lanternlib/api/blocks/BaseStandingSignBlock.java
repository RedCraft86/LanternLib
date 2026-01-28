package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class BaseStandingSignBlock extends StandingSignBlock implements ITooltipProvider {
    public BaseStandingSignBlock(WoodType type, Properties properties) {
        super(type, properties);
    }
}
