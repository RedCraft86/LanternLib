package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class BaseDoorBlock extends DoorBlock implements ITooltipProvider {
    public BaseDoorBlock(BlockSetType type, Properties properties) {
        super(properties, type);
    }
}
