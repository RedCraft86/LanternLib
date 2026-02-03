package com.redcraft86.lanternlib.api.blocks;

import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class BaseWallStandingSignBlock extends WallSignBlock {
    public BaseWallStandingSignBlock(WoodType type, Properties properties) {
        super(properties, type);
    }
}
