package com.redcraft86.lanternlib.api.items;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;

import java.util.List;

public class BaseItem extends Item implements ITooltipProvider {
    public BaseItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);
        components.addAll(getTooltips());
    }
}
