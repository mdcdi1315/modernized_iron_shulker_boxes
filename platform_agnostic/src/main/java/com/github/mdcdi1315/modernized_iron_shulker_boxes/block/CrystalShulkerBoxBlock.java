package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.MaybeNull;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.CrystalShulkerBoxBlockEntity;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public class CrystalShulkerBoxBlock
        extends AbstractIronShulkerBoxBlock
{
    public CrystalShulkerBoxBlock(Properties properties) {
        super(properties, IronShulkerBoxesTypes.CRYSTAL, () -> IronShulkerBoxesBlockEntities.CRYSTAL_SHULKER_BOX);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrystalShulkerBoxBlockEntity(pos, state);
    }

    @Override
    @MaybeNull
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, this.block_ent_type.function(), CrystalShulkerBoxBlockEntity::tick);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CreateMapCodecForIronShulkerBlock(CrystalShulkerBoxBlock::new);
    }
}
