package com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.ObsidianShulkerBoxMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;

public final class ObsidianShulkerBoxBlockEntity
        extends AbstractIronShulkerBoxBlockEntity
{
    public ObsidianShulkerBoxBlockEntity(BlockPos pPos, BlockState pState) {
        super(IronShulkerBoxesBlockEntities.OBSIDIAN_SHULKER_BOX, pPos, pState, IronShulkerBoxesTypes.OBSIDIAN);
    }

    @Override
    protected ObsidianShulkerBoxMenu createMenu(int pContainerId, Inventory pInventory) {
        return new ObsidianShulkerBoxMenu(pContainerId, pInventory, this);
    }
}
