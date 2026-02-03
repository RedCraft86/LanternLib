package com.redcraft86.lanternlib.api.items;

import java.util.List;

import com.redcraft86.lanternlib.utils.ITooltipProvider;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class BaseHangingSignItem extends HangingSignItem implements ITooltipProvider {

    public BaseHangingSignItem(Block block, Block wallBlock, Properties properties) {
        super(block, wallBlock, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        if (getBlock() instanceof ITooltipProvider provider) {
            tooltip.addAll(provider.getTooltips());
        }
    }
}
