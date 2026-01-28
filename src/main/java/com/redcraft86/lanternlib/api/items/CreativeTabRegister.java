package com.redcraft86.lanternlib.api.items;

import java.util.function.Function;
import java.util.function.Supplier;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import com.redcraft86.lanternlib.utils.TranslationUtils;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CreativeTabRegister {
    private final DeferredRegister<CreativeModeTab> tabs;
    private final Object2ObjectOpenHashMap<String, Supplier<CreativeModeTab>> entries;

    public CreativeTabRegister(String modId) {
        tabs = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modId);
        entries = new Object2ObjectOpenHashMap<>();
    }

    public DeferredRegister<CreativeModeTab> getDeferredRegister() {
        return tabs;
    }

    public void register(IEventBus eventBus) {
        tabs.register(eventBus);
    }

    public Supplier<CreativeModeTab> getTab(ResourceKey<CreativeModeTab> tabKey) {
        return entries.get(tabKey.location().getPath());
    }

    public ResourceKey<CreativeModeTab> addTab(String name, Function<CreativeModeTab.Builder, CreativeModeTab.Builder> builder) {
        entries.put(name, tabs.register(name,
                () -> builder.apply(CreativeModeTab.builder()
                        .title(TranslationUtils.component("creativetab", tabs.getNamespace(), name))
                ).build()
        ));

        return ResourceKey.create(Registries.CREATIVE_MODE_TAB,
                ResourceLocation.fromNamespaceAndPath(tabs.getNamespace(), name)
        );
    }
}
