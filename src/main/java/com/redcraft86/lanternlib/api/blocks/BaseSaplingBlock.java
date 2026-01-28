package com.redcraft86.lanternlib.api.blocks;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;

public class BaseSaplingBlock extends SaplingBlock implements ITooltipProvider {
    public BaseSaplingBlock(TreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
    }
}
