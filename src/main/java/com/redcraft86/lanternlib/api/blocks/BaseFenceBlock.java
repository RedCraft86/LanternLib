package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;
import net.minecraft.world.level.block.FenceBlock;

public class BaseFenceBlock extends FenceBlock implements ITooltipProvider {
    public BaseFenceBlock(Properties properties) {
        super(properties);
    }
}
