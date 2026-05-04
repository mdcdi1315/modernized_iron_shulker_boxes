package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.CopperShulkerBoxBlockEntity;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CopperShulkerBoxBlock
        extends AbstractIronShulkerBoxBlock
{
    public CopperShulkerBoxBlock(Properties properties) { super(properties, IronShulkerBoxesTypes.COPPER); }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new CopperShulkerBoxBlockEntity(pos, state); }

    @Override
    protected MapCodec<CopperShulkerBoxBlock> codec() { return CreateMapCodecForIronShulkerBlock(CopperShulkerBoxBlock::new); }

    @Override
    public BlockEntityType<CopperShulkerBoxBlockEntity> GetBlockEntityType() { return IronShulkerBoxesBlockEntities.COPPER_SHULKER_BOX; }
}
