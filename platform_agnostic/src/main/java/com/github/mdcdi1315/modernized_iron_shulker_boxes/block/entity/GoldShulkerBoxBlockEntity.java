package com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.GoldShulkerBoxMenu;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;

public final class GoldShulkerBoxBlockEntity
        extends AbstractIronShulkerBoxBlockEntity
{
    public GoldShulkerBoxBlockEntity(BlockPos pPos, BlockState pState) {
        super(IronShulkerBoxesBlockEntities.GOLD_SHULKER_BOX, pPos, pState, IronShulkerBoxesTypes.GOLD);
    }

    @Override
    protected GoldShulkerBoxMenu createMenu(int pContainerId, Inventory pInventory) {
        return new GoldShulkerBoxMenu(pContainerId, pInventory, this);
    }
}
