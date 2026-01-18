package com.redcraft86.lanternlib.common.items;

import com.redcraft86.lanternlib.util.TooltipBuilder;
import com.redcraft86.lanternlib.common.blocks.ModBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ModBlockItem extends BlockItem {
    public ModBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public TooltipBuilder getTooltips() {
        return (getBlock() instanceof ModBlock modBlock) ? modBlock.getTooltips() : null;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);
        components.addAll(getTooltips().build());
    }
}
