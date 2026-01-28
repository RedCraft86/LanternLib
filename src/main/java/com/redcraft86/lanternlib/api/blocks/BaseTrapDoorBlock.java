package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class BaseTrapDoorBlock extends TrapDoorBlock implements ITooltipProvider {
    public BaseTrapDoorBlock(BlockSetType type, Properties properties) {
        super(type, properties);
    }
}
