package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxBlockEntity;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class IronShulkerBoxBlock
        extends AbstractIronShulkerBoxBlock
{
    public IronShulkerBoxBlock(Properties properties) { super(properties, IronShulkerBoxesTypes.IRON); }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new IronShulkerBoxBlockEntity(pos, state); }

    @Override
    protected MapCodec<IronShulkerBoxBlock> codec() { return CreateMapCodecForIronShulkerBlock(IronShulkerBoxBlock::new); }

    @Override
    public BlockEntityType<IronShulkerBoxBlockEntity> GetBlockEntityType() { return IronShulkerBoxesBlockEntities.IRON_SHULKER_BOX; }
}
