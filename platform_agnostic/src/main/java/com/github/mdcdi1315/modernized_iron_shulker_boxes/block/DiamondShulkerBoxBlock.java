package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.DiamondShulkerBoxBlockEntity;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DiamondShulkerBoxBlock
        extends AbstractIronShulkerBoxBlock
{
    public DiamondShulkerBoxBlock(Properties properties) {
        super(properties, IronShulkerBoxesTypes.DIAMOND, () -> IronShulkerBoxesBlockEntities.DIAMOND_SHULKER_BOX);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CreateMapCodecForIronShulkerBlock(DiamondShulkerBoxBlock::new);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DiamondShulkerBoxBlockEntity(pos, state);
    }
}
