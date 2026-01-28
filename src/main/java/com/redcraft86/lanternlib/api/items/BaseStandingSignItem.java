package com.redcraft86.lanternlib.api.items;

import java.util.List;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.network.chat.Component;

public class BaseStandingSignItem extends SignItem implements ITooltipProvider {

    public BaseStandingSignItem(Properties properties, Block standingBlock, Block wallBlock) {
        super(properties, standingBlock, wallBlock);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);
        if (getBlock() instanceof ITooltipProvider provider) {
            components.addAll(provider.getTooltips());
        }
    }
}
