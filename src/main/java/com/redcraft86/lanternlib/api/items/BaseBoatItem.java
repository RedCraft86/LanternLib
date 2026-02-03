package com.redcraft86.lanternlib.api.items;

import java.util.List;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class BaseBoatItem extends BoatItem implements ITooltipProvider {

    public BaseBoatItem(boolean hasChest, Boat.Type type, Properties properties) {
        super(hasChest, type, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.addAll(getTooltips());
    }
}
