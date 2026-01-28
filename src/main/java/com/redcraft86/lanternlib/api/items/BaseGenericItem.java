package com.redcraft86.lanternlib.api.items;

import java.util.List;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;

public class BaseGenericItem extends Item implements ITooltipProvider {
    public BaseGenericItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);
        components.addAll(getTooltips());
    }
}
