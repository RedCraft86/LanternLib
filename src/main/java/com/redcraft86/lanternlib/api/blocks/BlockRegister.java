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

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public final class BlockRegister {
    private final ItemRegister items;
    private final DeferredRegister<Block> blocks;
    private final ResourceKey<CreativeModeTab> defaultTab;
    private final Object2ObjectOpenHashMap<ResourceKey<CreativeModeTab>, ObjectArrayList<RegistryObject<? extends ItemLike>>> creativeTabs;

    public BlockRegister(String modId, ResourceKey<CreativeModeTab> creativeTab, ItemRegister itemRegister) {
        items = itemRegister;
        blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
        defaultTab = creativeTab;
        creativeTabs = new Object2ObjectOpenHashMap<>();
    }

    public DeferredRegister<Block> getDeferredRegister() {
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

    public RegistryObject<Block> addItemlessBlock(String name, Block.Properties blockProp, ResourceKey<CreativeModeTab> tab) {
        return addBlockToTab(tab, blocks.register(name, () -> new Block(blockProp == null ? Block.Properties.of() : blockProp)));
    }

    public <T extends Block> RegistryObject<T> addItemlessBlock(String name, Supplier<T> block, ResourceKey<CreativeModeTab> tab) {
        return addBlockToTab(tab, blocks.register(name, block));
    }

    public RegistryObject<Block> addBlock(String name, Block.Properties blockProp, Item.Properties itemProp, ResourceKey<CreativeModeTab> tab) {
        RegistryObject<Block> blockObj = addItemlessBlock(name, blockProp, tab);
        items.addBlockItem(name, blockObj, itemProp);
        return blockObj;
    }

    public <T extends Block> RegistryObject<T> addBlock(String name, Supplier<T> block, Item.Properties itemProp, ResourceKey<CreativeModeTab> tab) {
        RegistryObject<T> blockObj = addItemlessBlock(name, block, tab);
        items.addBlockItem(name, blockObj, itemProp);
        return blockObj;
    }

    private <T extends Block> RegistryObject<T> addBlockToTab(ResourceKey<CreativeModeTab> tab, RegistryObject<T> block) {
        creativeTabs.computeIfAbsent(tab == null ? defaultTab : tab,
                k -> new ObjectArrayList<>()
        ).add(block);
        return block;
    }
}
