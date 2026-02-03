package com.redcraft86.lanternlib.mixin;

import com.redcraft86.lanternlib.configs.ClientCfg;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Unique private static final int TICK_INTERVAL = 5;
    @Unique private static int TICK_COUNTER = 0;

    @Unique private static String buildMemoryString() {
        final int CONVERSION = 1024 * 1024;

        Runtime run = Runtime.getRuntime();
        long total = run.totalMemory();
        long used = (total - run.freeMemory()) / CONVERSION;
        return String.format("Memory: %,d MB / %,d MB (%,d MB Allocated)",
                used, run.maxMemory() / CONVERSION, total / CONVERSION);
    }

    @Inject(method = "createTitle", at = @At("RETURN"), cancellable = true)
    private void onCreateTitle(CallbackInfoReturnable<String> cir) {
        if (!ClientCfg.isLoaded()) {
            return;
        }

        final String customTitle = ClientCfg.CUSTOM_TITLE_BAR.get();
        final boolean showMemory = ClientCfg.MEMORY_TITLE_BAR.get();
        if (!customTitle.isBlank() || showMemory) {
            String baseTitle = customTitle.isBlank() ? cir.getReturnValue() : customTitle;
            cir.setReturnValue(showMemory ? String.format("%s | %s", baseTitle, buildMemoryString()) : baseTitle);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (TICK_COUNTER > TICK_INTERVAL) {
            ((Minecraft)(Object)this).updateTitle();
            TICK_COUNTER = 0;
        }
        TICK_COUNTER++;
    }
}
