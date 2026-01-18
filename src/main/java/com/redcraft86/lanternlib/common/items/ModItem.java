package com.redcraft86.lanternlib.common.items;

import com.redcraft86.lanternlib.util.TooltipBuilder;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ModItem extends Item {
    private final TooltipBuilder tooltips = new TooltipBuilder();

    public ModItem(Properties properties) {
        super(properties);
    }

    public TooltipBuilder getTooltips() {
        return tooltips;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);
        components.addAll(getTooltips().build());
    }
}
