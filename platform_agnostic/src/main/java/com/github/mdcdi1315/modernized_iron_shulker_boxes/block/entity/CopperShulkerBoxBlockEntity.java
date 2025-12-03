package com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.CopperShulkerBoxMenu;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;

public final class CopperShulkerBoxBlockEntity
        extends AbstractIronShulkerBoxBlockEntity
{
    public CopperShulkerBoxBlockEntity(BlockPos pPos, BlockState pState) {
        super(IronShulkerBoxesBlockEntities.COPPER_SHULKER_BOX, pPos, pState, IronShulkerBoxesTypes.COPPER);
    }

    @Override
    protected CopperShulkerBoxMenu createMenu(int pContainerId, Inventory pInventory) {
        return new CopperShulkerBoxMenu(pContainerId, pInventory, this);
    }
}
