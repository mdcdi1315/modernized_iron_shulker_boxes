package com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.DiamondShulkerBoxMenu;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;

public final class DiamondShulkerBoxBlockEntity
        extends AbstractIronShulkerBoxBlockEntity
{
    public DiamondShulkerBoxBlockEntity(BlockPos pPos, BlockState pState) {
        super(IronShulkerBoxesBlockEntities.DIAMOND_SHULKER_BOX, pPos, pState, IronShulkerBoxesTypes.DIAMOND);
    }

    @Override
    protected DiamondShulkerBoxMenu createMenu(int pContainerId, Inventory pInventory) {
        return new DiamondShulkerBoxMenu(pContainerId, pInventory, this);
    }
}
