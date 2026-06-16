package com.redcraft86.lanternlib.mixin;

import net.minecraft.world.item.Item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public interface AccessItem {
    @Accessor("craftingRemainingItem")
    Item ll_getCraftRemainder();

    @Mutable
    @Accessor("craftingRemainingItem")
    void ll_setCraftRemainder(Item item);
}