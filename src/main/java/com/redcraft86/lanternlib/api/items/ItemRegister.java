package com.redcraft86.lanternlib.api.items;

import java.util.function.Supplier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public final class ItemRegister {
    private final DeferredRegister<Item> items;
    private final ResourceKey<CreativeModeTab> defaultTab;
    private final Object2ObjectOpenHashMap<ResourceKey<CreativeModeTab>, ObjectArrayList<RegistryObject<? extends ItemLike>>> creativeTabs;

    public ItemRegister(String modId, ResourceKey<CreativeModeTab> creativeTab) {
        items = DeferredRegister.create(ForgeRegistries.ITEMS, modId);
        defaultTab = creativeTab;
        creativeTabs = new Object2ObjectOpenHashMap<>();
    }

    public DeferredRegister<Item> getDeferredRegister() {
        return items;
    }

    public void register(IEventBus eventBus) {
        items.register(eventBus);
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (creativeTabs.containsKey(event.getTabKey())) {
            creativeTabs.get(event.getTabKey()).forEach(event::accept);
        }
    }

    public RegistryObject<Item> addItem(String name, Item.Properties prop, ResourceKey<CreativeModeTab> tab) {
        return addItemToTab(tab, items.register(name, () -> new Item(prop == null ? new Item.Properties() : prop)));
    }

    public <T extends Item> RegistryObject<T> addItem(String name, Supplier<T> item, ResourceKey<CreativeModeTab> tab) {
        return addItemToTab(tab, items.register(name, item));
    }

    public <T extends Block> void addBlockItem(String name, RegistryObject<T> block, Item.Properties prop) {
        items.register(name, () -> new BaseBlockItem(block.get(), prop == null ? new Item.Properties() : prop));
    }

    private <T extends Item> RegistryObject<T> addItemToTab(ResourceKey<CreativeModeTab> tab, RegistryObject<T> item) {
        creativeTabs.computeIfAbsent(tab == null ? defaultTab : tab,
                k -> new ObjectArrayList<>()
        ).add(item);
        return item;
    }
}
