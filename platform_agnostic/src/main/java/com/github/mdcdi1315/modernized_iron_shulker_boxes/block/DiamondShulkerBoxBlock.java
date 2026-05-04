package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.DiamondShulkerBoxBlockEntity;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class DiamondShulkerBoxBlock
        extends AbstractIronShulkerBoxBlock
{
    public DiamondShulkerBoxBlock(Properties properties) { super(properties, IronShulkerBoxesTypes.DIAMOND); }

    @Override
    protected MapCodec<DiamondShulkerBoxBlock> codec() { return CreateMapCodecForIronShulkerBlock(DiamondShulkerBoxBlock::new); }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new DiamondShulkerBoxBlockEntity(pos, state); }

    @Override
    public BlockEntityType<DiamondShulkerBoxBlockEntity> GetBlockEntityType() { return IronShulkerBoxesBlockEntities.DIAMOND_SHULKER_BOX; }
}
