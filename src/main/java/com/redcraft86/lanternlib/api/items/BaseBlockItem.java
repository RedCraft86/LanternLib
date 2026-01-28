package com.redcraft86.lanternlib.api.items;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.network.chat.Component;

import java.util.List;

public class BaseBlockItem extends BlockItem {
    public BaseBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);
        if (getBlock() instanceof ITooltipProvider provider) {
            components.addAll(provider.getTooltips());
        }
    }
}
