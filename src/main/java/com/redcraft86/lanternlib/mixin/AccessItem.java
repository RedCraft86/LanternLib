package com.redcraft86.lanternlib.mixin;

import net.minecraft.world.item.Item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public interface AccessItem {
    @Accessor("craftingRemainingItem")
    Item getCraftRemainder();

    @Mutable
    @Accessor("craftingRemainingItem")
    void setCraftRemainder(Item item);
}