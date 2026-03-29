package com.redcraft86.lanternlib.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.sounds.SoundEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SolidBucketItem.class)
public class MixinSolidBucketItem {
    @Unique private final AccessItem superObj = (AccessItem)this;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(Block block, SoundEvent placeSound, Item.Properties properties, CallbackInfo ci) {
        if (superObj.getCraftRemainder() == null) {
            superObj.setCraftRemainder(Items.BUCKET);
        }
    }
}
