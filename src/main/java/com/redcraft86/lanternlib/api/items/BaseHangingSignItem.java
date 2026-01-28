package com.redcraft86.lanternlib.api.items;

import java.util.List;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.network.chat.Component;

public class BaseHangingSignItem extends HangingSignItem implements ITooltipProvider {

    public BaseHangingSignItem(Block block, Block wallBlock, Properties properties) {
        super(block, wallBlock, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);
        if (getBlock() instanceof ITooltipProvider provider) {
            components.addAll(provider.getTooltips());
        }
    }
}
