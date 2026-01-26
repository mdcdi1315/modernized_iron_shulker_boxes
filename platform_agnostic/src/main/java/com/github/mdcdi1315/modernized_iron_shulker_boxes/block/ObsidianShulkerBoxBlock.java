package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.ObsidianShulkerBoxBlockEntity;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ObsidianShulkerBoxBlock
        extends AbstractIronShulkerBoxBlock
{
    public ObsidianShulkerBoxBlock(Properties properties) { super(properties, IronShulkerBoxesTypes.OBSIDIAN); }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new ObsidianShulkerBoxBlockEntity(pos, state); }

    @Override
    protected MapCodec<ObsidianShulkerBoxBlock> codec() { return CreateMapCodecForIronShulkerBlock(ObsidianShulkerBoxBlock::new); }

    @Override
    public BlockEntityType<ObsidianShulkerBoxBlockEntity> GetBlockEntityType() { return IronShulkerBoxesBlockEntities.OBSIDIAN_SHULKER_BOX; }
}
