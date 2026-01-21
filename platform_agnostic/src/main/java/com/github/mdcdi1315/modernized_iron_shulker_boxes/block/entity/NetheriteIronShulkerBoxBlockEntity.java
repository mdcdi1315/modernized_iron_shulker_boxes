package com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.NetheriteShulkerBoxMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.inventory.AbstractContainerMenu;

public final class NetheriteIronShulkerBoxBlockEntity
    extends AbstractIronShulkerBoxBlockEntity
{
    public NetheriteIronShulkerBoxBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(IronShulkerBoxesBlockEntities.NETHERITE_SHULKER_BOX, blockPos, blockState, IronShulkerBoxesTypes.NETHERITE);
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new NetheriteShulkerBoxMenu(i, inventory, this);
    }
}
