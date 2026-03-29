package com.redcraft86.lanternlib.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BucketItem.class)
public class MixinBucketItem {
    @Unique private final AccessItem ll_superObj = (AccessItem)this;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ll_init(Fluid content, Item.Properties properties, CallbackInfo ci) {
        if (ll_superObj.ll_getCraftRemainder() == null) {
            ll_superObj.ll_setCraftRemainder(Items.BUCKET);
        }
    }
}
