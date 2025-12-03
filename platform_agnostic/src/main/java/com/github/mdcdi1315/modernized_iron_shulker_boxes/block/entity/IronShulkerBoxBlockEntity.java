package com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.IronShulkerBoxMenu;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;

public final class IronShulkerBoxBlockEntity
        extends AbstractIronShulkerBoxBlockEntity
{
    public IronShulkerBoxBlockEntity(BlockPos pPos, BlockState pState) {
        super(IronShulkerBoxesBlockEntities.IRON_SHULKER_BOX, pPos, pState, IronShulkerBoxesTypes.IRON);
    }

    @Override
    protected IronShulkerBoxMenu createMenu(int pContainerId, Inventory pInventory) {
        return new IronShulkerBoxMenu(pContainerId, pInventory, this);
    }
}
