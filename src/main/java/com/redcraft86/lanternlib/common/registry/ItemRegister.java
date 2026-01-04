package com.redcraft86.lanternlib.common.registry;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.function.Supplier;

import com.redcraft86.lanternlib.common.items.ModBlockItem;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.resources.ResourceKey;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class ItemRegister {
    private final DeferredRegister<Item> ITEMS;
    private final Map<ResourceKey<CreativeModeTab>, List<RegistryObject<? extends ItemLike>>> creativeEntries;

    public ItemRegister(String modId) {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modId);
        creativeEntries = new HashMap<>();
    }

    /** WARNING: This passes the actual object, not a copy! */
    public DeferredRegister<Item> getDeferredRegister() {
        return ITEMS;
    }

    public RegistryObject<Item> addItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    public RegistryObject<Item> addItem(String name, Item.Properties props) {
        return ITEMS.register(name, () -> new Item(props));
    }

    public <T extends Item> RegistryObject<T> addItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public RegistryObject<Item> addItem(String name, ResourceKey<CreativeModeTab> creativeTab) {
        return addItemToTab(addItem(name), creativeTab);
    }

    public RegistryObject<Item> addItem(String name, Item.Properties props, ResourceKey<CreativeModeTab> creativeTab) {
        return addItemToTab(addItem(name, props), creativeTab);
    }

    public <T extends Item> RegistryObject<T> addItem(String name, Supplier<T> item, ResourceKey<CreativeModeTab> creativeTab) {
        return addItemToTab(addItem(name, item), creativeTab);
    }

    public void addSimpleBlockItem(String name, RegistryObject<Block> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public <T extends Block> void addBlockItem(String name, RegistryObject<T> block) {
        ITEMS.register(name, () -> new ModBlockItem(block.get(), new Item.Properties()));
    }

    public <T extends Block> void addBlockItem(String name, RegistryObject<T> block, Item.Properties props) {
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
    
    private <T extends Item> RegistryObject<T> addItemToTab(RegistryObject<T> item, ResourceKey<CreativeModeTab> tab) {
        creativeEntries.computeIfAbsent(tab, k -> new ArrayList<>()).add(item);
        return item;
    }
}
