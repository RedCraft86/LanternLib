package com.redcraft86.lanternlib.common.blocks;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.function.Supplier;

import com.redcraft86.lanternlib.common.items.ItemRegister;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

public class BlockRegister {
    private final ItemRegister items;
    private final ResourceLocation defaultTab;
    private final DeferredRegister.Blocks blocks;
    private final Map<ResourceLocation, List<DeferredBlock<? extends ItemLike>>> creativeTabs;

    public BlockRegister(String modId, ResourceKey<CreativeModeTab> creativeTab, ItemRegister itemRegister) {
        items = itemRegister;
        blocks = DeferredRegister.createBlocks(modId);
        defaultTab = creativeTab.location();
        creativeTabs = new HashMap<>();
    }

    public DeferredRegister.Blocks getDeferredRegister() {
        return blocks;
    }

    public void register(IEventBus eventBus) {
        blocks.register(eventBus);
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (creativeTabs.containsKey(event.getTabKey().location())) {
            creativeTabs.get(event.getTabKey().location()).forEach(event::accept);
        }
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
        creativeTabs.computeIfAbsent(tab == null ? defaultTab : tab.location(), k -> new ArrayList<>()).add(block);
        return block;
    }
}
