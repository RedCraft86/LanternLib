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

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class BlockRegister {
    private final ItemRegister ITEMS;
    private final DeferredRegister<Block> BLOCKS;
    private final Map<ResourceKey<CreativeModeTab>, List<RegistryObject<? extends ItemLike>>> creativeEntries;

    public BlockRegister(String modId, ItemRegister itemRegister) {
        ITEMS = itemRegister;
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
        creativeEntries = new HashMap<>();
    }

    /** WARNING: This passes the actual object, not a copy! */
    public DeferredRegister<Block> getDeferredRegister() {
        return BLOCKS;
    }

    public void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public RegistryObject<Block> addBlock(String name, BlockBehaviour.Properties props) {
        RegistryObject<Block> blockObj = BLOCKS.register(name, () -> new Block(props));
        ITEMS.addSimpleBlockItem(name, blockObj);
        return blockObj;
    }

    public <T extends Block> RegistryObject<T> addBlock(String name, Supplier<T> block) {
        RegistryObject<T> blockObj = BLOCKS.register(name, block);
        ITEMS.addBlockItem(name, blockObj);
        return blockObj;
    }

    public RegistryObject<Block> addBlock(String name, BlockBehaviour.Properties props, ResourceKey<CreativeModeTab> creativeTab) {
        return addBlockToTab(addBlock(name, props), creativeTab);
    }

    public <T extends Block> RegistryObject<T> addBlock(String name, Supplier<T> block, ResourceKey<CreativeModeTab> creativeTab) {
        return addBlockToTab(addBlock(name, block), creativeTab);
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (creativeEntries.containsKey(event.getTabKey())) {
            creativeEntries.get(event.getTabKey()).forEach(event::accept);
        }
    }

    private <T extends Block> RegistryObject<T> addBlockToTab(RegistryObject<T> block, ResourceKey<CreativeModeTab> tab) {
        creativeEntries.computeIfAbsent(tab, k -> new ArrayList<>()).add(block);
        return block;
    }
}
