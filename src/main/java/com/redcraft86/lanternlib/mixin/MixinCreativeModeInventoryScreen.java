package com.redcraft86.lanternlib.mixin;

import java.util.List;
import java.util.Collections;

import com.redcraft86.lanternlib.configs.ClientCfg;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
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
        if (ClientCfg.DISABLE_TAB_TOOLTIPS.get()) {
            return Collections.emptyList();
        }
        return CreativeModeTabs.tabs();
    }
}
