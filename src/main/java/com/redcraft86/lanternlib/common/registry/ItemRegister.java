package com.redcraft86.lanternlib.common.registry;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.function.Supplier;

import com.redcraft86.lanternlib.common.items.ModBlockItem;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.resources.ResourceKey;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class ItemRegister {
    private final DeferredRegister.Items ITEMS;
    private final Map<ResourceKey<CreativeModeTab>, List<DeferredItem<? extends ItemLike>>> creativeEntries;

    public ItemRegister(String modId) {
        ITEMS = DeferredRegister.createItems(modId);
        creativeEntries = new HashMap<>();
    }

    /** WARNING: This passes the actual object, not a copy! */
    public DeferredRegister.Items getDeferredRegister() {
        return ITEMS;
    }

    public DeferredItem<Item> addItem(String name) {
        return ITEMS.registerSimpleItem(name);
    }

    public DeferredItem<Item> addItem(String name, Item.Properties props) {
        return ITEMS.registerSimpleItem(name, props);
    }

    public <T extends Item> DeferredItem<T> addItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public DeferredItem<Item> addItem(String name, ResourceKey<CreativeModeTab> creativeTab) {
        return addItemToTab(addItem(name), creativeTab);
    }

    public DeferredItem<Item> addItem(String name, Item.Properties props, ResourceKey<CreativeModeTab> creativeTab) {
        return addItemToTab(addItem(name, props), creativeTab);
    }

    public <T extends Item> DeferredItem<T> addItem(String name, Supplier<T> item, ResourceKey<CreativeModeTab> creativeTab) {
        return addItemToTab(addItem(name, item), creativeTab);
    }

    public void addSimpleBlockItem(String name, DeferredBlock<Block> block) {
        ITEMS.registerSimpleBlockItem(name, block);
    }

    public <T extends Block> void addBlockItem(String name, DeferredBlock<T> block) {
        ITEMS.register(name, () -> new ModBlockItem(block.get(), new Item.Properties()));
    }

    public <T extends Block> void addBlockItem(String name, DeferredBlock<T> block, Item.Properties props) {
        ITEMS.register(name, () -> new ModBlockItem(block.get(), props));
    }

    public void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (creativeEntries.containsKey(event.getTabKey())) {
            creativeEntries.get(event.getTabKey()).forEach(event::accept);
        }
    }

    private <T extends Item> DeferredItem<T> addItemToTab(DeferredItem<T> item, ResourceKey<CreativeModeTab> tab) {
        creativeEntries.computeIfAbsent(tab, k -> new ArrayList<>()).add(item);
        return item;
    }
}
