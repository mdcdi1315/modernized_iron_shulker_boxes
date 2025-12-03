package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.ObsidianShulkerBoxBlockEntity;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ObsidianShulkerBoxBlock
        extends AbstractIronShulkerBoxBlock
{
    public ObsidianShulkerBoxBlock(Properties properties) {
        super(properties, IronShulkerBoxesTypes.OBSIDIAN, () -> IronShulkerBoxesBlockEntities.OBSIDIAN_SHULKER_BOX);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new ObsidianShulkerBoxBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CreateMapCodecForIronShulkerBlock(ObsidianShulkerBoxBlock::new);
    }
}
