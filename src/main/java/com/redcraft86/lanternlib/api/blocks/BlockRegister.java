package com.redcraft86.lanternlib.api.blocks;

import java.util.function.Supplier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import com.redcraft86.lanternlib.api.items.ItemRegister;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class BlockRegister {
    private final ItemRegister items;
    private final DeferredRegister.Blocks blocks;
    private final ResourceKey<CreativeModeTab> defaultTab;
    private final Object2ObjectOpenHashMap<ResourceKey<CreativeModeTab>, ObjectArrayList<DeferredBlock<? extends ItemLike>>> creativeTabs;

    public BlockRegister(String modId, ResourceKey<CreativeModeTab> creativeTab, ItemRegister itemRegister) {
        items = itemRegister;
        blocks = DeferredRegister.createBlocks(modId);
        defaultTab = creativeTab;
        creativeTabs = new Object2ObjectOpenHashMap<>();
    }

    public DeferredRegister.Blocks getDeferredRegister() {
        return blocks;
    }

    public void register(IEventBus eventBus) {
        blocks.register(eventBus);
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (creativeTabs.containsKey(event.getTabKey())) {
            creativeTabs.get(event.getTabKey()).forEach(event::accept);
        }
    }

    public DeferredBlock<Block> addItemlessBlock(String name, Block.Properties blockProp, ResourceKey<CreativeModeTab> tab) {
        return addBlockToTab(tab, blocks.registerSimpleBlock(name, blockProp == null ? Block.Properties.of() : blockProp));
    }

    public <T extends Block> DeferredBlock<T> addItemlessBlock(String name, Supplier<T> block, ResourceKey<CreativeModeTab> tab) {
        return addBlockToTab(tab, blocks.register(name, block));
    }

    public DeferredBlock<Block> addBlock(String name, Block.Properties blockProp, Item.Properties itemProp, ResourceKey<CreativeModeTab> tab) {
        DeferredBlock<Block> blockObj = addBlockToTab(tab, blocks.registerSimpleBlock(name, blockProp == null ? Block.Properties.of() : blockProp));
        items.addBlockItem(name, blockObj, itemProp);
        return blockObj;
    }

    public <T extends Block> DeferredBlock<T> addBlock(String name, Supplier<T> block, Item.Properties itemProp, ResourceKey<CreativeModeTab> tab) {
        DeferredBlock<T> blockObj = addBlockToTab(tab, blocks.register(name, block));
        items.addBlockItem(name, blockObj, itemProp);
        return blockObj;
    }

    private <T extends Block> DeferredBlock<T> addBlockToTab(ResourceKey<CreativeModeTab> tab, DeferredBlock<T> block) {
        creativeTabs.computeIfAbsent(tab == null ? defaultTab : tab,
                k -> new ObjectArrayList<>()
        ).add(block);
        return block;
    }
}
