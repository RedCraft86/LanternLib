package com.redcraft86.lanternlib.common.registry;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.function.Supplier;

import com.redcraft86.lanternlib.util.ITooltipProvider;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.resources.ResourceKey;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class BlockRegister {
    private final ItemRegister ITEMS;
    private final DeferredRegister.Blocks BLOCKS;
    private final Map<ResourceKey<CreativeModeTab>, List<DeferredBlock<? extends ItemLike>>> creativeEntries;

    public BlockRegister(String modId, ItemRegister itemRegister) {
        ITEMS = itemRegister;
        BLOCKS = DeferredRegister.createBlocks(modId);
        creativeEntries = new HashMap<>();
    }

    /** WARNING: This passes the actual object, not a copy! */
    public DeferredRegister.Blocks getDeferredRegister() {
        return BLOCKS;
    }

    public DeferredBlock<Block> addBlock(String name, BlockBehaviour.Properties props) {
        DeferredBlock<Block> blockObj = BLOCKS.registerSimpleBlock(name, props);
        ITEMS.addSimpleBlockItem(name, blockObj);
        return blockObj;
    }

    public <T extends Block> DeferredBlock<T> addBlock(String name, Supplier<T> block) {
        DeferredBlock<T> blockObj = BLOCKS.register(name, block);
        ITEMS.addBlockItem(name, blockObj);
        return blockObj;
    }

    public DeferredBlock<Block> addBlock(String name, BlockBehaviour.Properties props, ResourceKey<CreativeModeTab> creativeTab) {
        return addBlockToTab(addBlock(name, props), creativeTab);
    }

    public <T extends Block> DeferredBlock<T> addBlock(String name, Supplier<T> block, ResourceKey<CreativeModeTab> creativeTab) {
        return addBlockToTab(addBlock(name, block), creativeTab);
    }

    public void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (creativeEntries.containsKey(event.getTabKey())) {
            creativeEntries.get(event.getTabKey()).forEach(event::accept);
        }
    }

    private <T extends Block> DeferredBlock<T> addBlockToTab(DeferredBlock<T> block, ResourceKey<CreativeModeTab> tab) {
        creativeEntries.computeIfAbsent(tab, k -> new ArrayList<>()).add(block);
        return block;
    }
}
