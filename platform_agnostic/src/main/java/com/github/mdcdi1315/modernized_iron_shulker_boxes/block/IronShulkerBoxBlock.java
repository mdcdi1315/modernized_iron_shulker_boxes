package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxBlockEntity;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

public class IronShulkerBoxBlock
        extends AbstractIronShulkerBoxBlock
{
    public IronShulkerBoxBlock(Properties properties) {
        super(properties, IronShulkerBoxesTypes.IRON, () -> IronShulkerBoxesBlockEntities.IRON_SHULKER_BOX);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new IronShulkerBoxBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CreateMapCodecForIronShulkerBlock(IronShulkerBoxBlock::new);
    }
}
