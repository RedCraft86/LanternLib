package com.redcraft86.lanternlib.mixin;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import com.redcraft86.lanternlib.configs.*;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Unique
    private int ll_tickCounter = 0;

    @Unique
    private static String ll_buildMemoryString() {
        final int CONVERSION = 1024 * 1024;

        Runtime run = Runtime.getRuntime();
        long total = run.totalMemory();
        long used = (total - run.freeMemory()) / CONVERSION;

        return String.format("Memory: %,d MB / %,d MB (%,d MB Allocated)",
                used, run.maxMemory() / CONVERSION, total / CONVERSION);
    }

    @Inject(method = "createTitle", at = @At("RETURN"), cancellable = true)
    private void ll_createTitle(CallbackInfoReturnable<String> cir) {
        final String customTitle = Configs.CLIENT.TitleBar.customText;
        final boolean showMemory = Configs.CLIENT.TitleBar.showMemory;

        if (!customTitle.isBlank() || showMemory) {
            final String baseTitle = customTitle.isBlank() ? cir.getReturnValue() : customTitle;
            cir.setReturnValue(showMemory ? String.format("%s | %s", baseTitle, ll_buildMemoryString()) : baseTitle);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void ll_tick(CallbackInfo ci) {
        if (--ll_tickCounter < 0) {
            ll_tickCounter = 5; // Update every 5 ticks
            ((Minecraft)(Object)this).updateTitle();
        }
    }
}
