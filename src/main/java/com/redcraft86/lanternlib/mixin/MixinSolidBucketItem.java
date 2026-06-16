package com.redcraft86.lanternlib.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.sounds.SoundEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SolidBucketItem.class)
public class MixinSolidBucketItem {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void ll_init(Block block, SoundEvent placeSound, Item.Properties properties, CallbackInfo ci) {
        // Makes all solid bucketed items return a bucket remainder after being used in crafting if not already set
        if (((AccessItem)this).ll_getCraftRemainder() == null) {
            ((AccessItem)this).ll_setCraftRemainder(Items.BUCKET);
        }
    }
}