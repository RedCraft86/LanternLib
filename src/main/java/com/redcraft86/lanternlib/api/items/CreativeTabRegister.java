package com.redcraft86.lanternlib.api.items;

import java.util.function.Function;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import com.redcraft86.lanternlib.utils.TranslationUtils;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class CreativeTabRegister {
    private final String namespace;
    private final DeferredRegister<CreativeModeTab> tabs;
    private final Object2ObjectOpenHashMap<String, RegistryObject<CreativeModeTab>> entries;

    public CreativeTabRegister(String modId) {
        namespace = modId;
        tabs = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modId);
        entries = new Object2ObjectOpenHashMap<>();
    }

    public DeferredRegister<CreativeModeTab> getDeferredRegister() {
        return tabs;
    }

    public void register(IEventBus eventBus) {
        tabs.register(eventBus);
    }

    public RegistryObject<CreativeModeTab> getTab(ResourceKey<CreativeModeTab> tabKey) {
        return entries.get(tabKey.location().getPath());
    }

    public ResourceKey<CreativeModeTab> addTab(String name, Function<CreativeModeTab.Builder, CreativeModeTab.Builder> builder) {
        entries.put(name, tabs.register(name,
                () -> builder.apply(CreativeModeTab.builder()
                        .title(TranslationUtils.component("creativetab", namespace, name))
                ).build()
        ));

        return ResourceKey.create(Registries.CREATIVE_MODE_TAB,
                ResourceLocation.fromNamespaceAndPath(namespace, name)
        );
    }
}
