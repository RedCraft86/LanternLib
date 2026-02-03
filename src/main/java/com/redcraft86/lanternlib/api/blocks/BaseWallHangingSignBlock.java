package com.redcraft86.lanternlib.api.blocks;

import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class BaseWallHangingSignBlock extends WallHangingSignBlock {
    public BaseWallHangingSignBlock(WoodType type, Properties properties) {
        super(properties, type);
    }
}
