package com.redcraft86.lanternlib.mixin;

import java.util.List;
import java.util.Collections;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import com.redcraft86.lanternlib.configs.*;

@Mixin(CreativeModeInventoryScreen.class)
public class MixinCreativeModeInventoryScreen {
    @Redirect(method = "getTooltipFromContainerItem",
        at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/item/CreativeModeTabs;tabs()Ljava/util/List;"
        )
    )
    private List<CreativeModeTab> ll_disableTabTooltip() {
        return Configs.CLIENT.disableTabTooltips ? Collections.emptyList() : CreativeModeTabs.tabs();
    }
}
