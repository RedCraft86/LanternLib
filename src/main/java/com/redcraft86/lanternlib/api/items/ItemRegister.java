package com.redcraft86.lanternlib.api.items;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public final class ItemRegister {
    private final ResourceLocation defaultTab;
    private final DeferredRegister.Items items;
    private final Map<ResourceLocation, List<DeferredItem<? extends ItemLike>>> creativeTabs;

    public ItemRegister(String modId, ResourceKey<CreativeModeTab> creativeTab) {
        items = DeferredRegister.createItems(modId);
        defaultTab = creativeTab.location();
        creativeTabs = new HashMap<>();
    }

    public DeferredRegister.Items getDeferredRegister() {
        return items;
    }

    public void register(IEventBus eventBus) {
        items.register(eventBus);
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (creativeTabs.containsKey(event.getTabKey().location())) {
            creativeTabs.get(event.getTabKey().location()).forEach(event::accept);
        }
    }

    public DeferredItem<Item> addItem(String name, Item.Properties prop, ResourceKey<CreativeModeTab> tab) {
        return addItemToTab(tab, items.registerSimpleItem(name, prop == null ? new Item.Properties() : prop));
    }

    public <T extends Item> DeferredItem<T> addItem(String name, Supplier<T> item, ResourceKey<CreativeModeTab> tab) {
        return addItemToTab(tab, items.register(name, item));
    }

    public <T extends Block> void addBlockItem(String name, DeferredBlock<T> block, Item.Properties prop) {
        items.register(name, () -> new ModBlockItem(block.get(), prop == null ? new Item.Properties() : prop));
    }

    private <T extends Item> DeferredItem<T> addItemToTab(ResourceKey<CreativeModeTab> tab, DeferredItem<T> item) {
        creativeTabs.computeIfAbsent(tab == null ? defaultTab : tab.location(), k -> new ArrayList<>()).add(item);
        return item;
    }
}
