package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.NetheriteIronShulkerBoxBlockEntity;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class NetheriteShulkerBoxBlock
    extends AbstractIronShulkerBoxBlock
{
    public NetheriteShulkerBoxBlock(Properties properties) { super(properties, IronShulkerBoxesTypes.NETHERITE); }

    @Override
    protected MapCodec<NetheriteShulkerBoxBlock> codec() { return CreateMapCodecForIronShulkerBlock(NetheriteShulkerBoxBlock::new); }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new NetheriteIronShulkerBoxBlockEntity(pos, state); }

    @Override
    public BlockEntityType<NetheriteIronShulkerBoxBlockEntity> GetBlockEntityType() { return IronShulkerBoxesBlockEntities.NETHERITE_SHULKER_BOX; }
}
