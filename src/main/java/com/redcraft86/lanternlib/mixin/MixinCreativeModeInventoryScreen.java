package com.redcraft86.lanternlib.mixin;

import java.util.List;
import java.util.Collections;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreativeModeInventoryScreen.class)
public class MixinCreativeModeInventoryScreen {
    @Redirect(method = "getTooltipFromContainerItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/CreativeModeTabs;tabs()Ljava/util/List;"
            )
    )
    private List<CreativeModeTab> redirectTabs() {
        return Collections.emptyList(); // TODO: config
    }
}
