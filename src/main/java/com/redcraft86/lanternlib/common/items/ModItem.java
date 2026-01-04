package com.redcraft86.lanternlib.common.items;

import java.util.Map;
import java.util.List;
import java.util.LinkedHashMap;
import javax.annotation.Nullable;

import com.redcraft86.lanternlib.util.ITooltipProvider;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;

public class ModItem extends Item implements ITooltipProvider {
    protected Map<String, Component> cachedTooltips;

    public ModItem(Properties properties) {
        super(properties);
    }

    @Override
    public Map<String, Component> getTooltips() {
        if (cachedTooltips == null) {
            cachedTooltips = new LinkedHashMap<>();
        }
        return cachedTooltips;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        this.appendTooltips(tooltipComponents);
    }
}