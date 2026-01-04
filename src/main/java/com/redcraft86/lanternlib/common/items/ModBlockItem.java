package com.redcraft86.lanternlib.common.items;

import java.util.Map;
import java.util.List;

import com.redcraft86.lanternlib.util.ITooltipProvider;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.network.chat.Component;

public class ModBlockItem extends BlockItem implements ITooltipProvider {
    public ModBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public Map<String, Component> getTooltips() {
        return getBlock() instanceof ITooltipProvider provider ? provider.getTooltips() : null;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);
        this.appendTooltips(tooltipComponents);
    }
}
