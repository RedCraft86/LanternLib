package com.redcraft86.lanternlib.api.items;

import java.util.List;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.network.chat.Component;

public class BaseBoatItem extends BoatItem implements ITooltipProvider {

    public BaseBoatItem(boolean hasChest, Boat.Type type, Properties properties) {
        super(hasChest, type, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);
        components.addAll(getTooltips());
    }
}
