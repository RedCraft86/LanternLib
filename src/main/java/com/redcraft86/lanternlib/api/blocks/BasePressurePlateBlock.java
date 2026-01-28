package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class BasePressurePlateBlock extends PressurePlateBlock implements ITooltipProvider {
    public BasePressurePlateBlock(BlockSetType type, Properties properties) {
        super(type, properties);
    }
}
