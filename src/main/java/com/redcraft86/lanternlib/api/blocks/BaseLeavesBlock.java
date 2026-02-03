package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;
import net.minecraft.world.level.block.LeavesBlock;

public class BaseLeavesBlock extends LeavesBlock implements ITooltipProvider {
    public BaseLeavesBlock(Properties properties) {
        super(properties);
    }
}
