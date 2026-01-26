package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.GoldShulkerBoxBlockEntity;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class GoldShulkerBoxBlock
        extends AbstractIronShulkerBoxBlock
{
    public GoldShulkerBoxBlock(Properties properties) { super(properties, IronShulkerBoxesTypes.GOLD); }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new GoldShulkerBoxBlockEntity(pos, state); }

    @Override
    protected MapCodec<GoldShulkerBoxBlock> codec() { return CreateMapCodecForIronShulkerBlock(GoldShulkerBoxBlock::new); }

    @Override
    public BlockEntityType<GoldShulkerBoxBlockEntity> GetBlockEntityType() { return IronShulkerBoxesBlockEntities.GOLD_SHULKER_BOX; }
}
