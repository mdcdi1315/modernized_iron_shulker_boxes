package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.NetheriteIronShulkerBoxBlockEntity;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NetheriteShulkerBoxBlock
    extends AbstractIronShulkerBoxBlock
{
    public NetheriteShulkerBoxBlock(Properties properties) {
        super(properties, IronShulkerBoxesTypes.NETHERITE, () -> IronShulkerBoxesBlockEntities.NETHERITE_SHULKER_BOX);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CreateMapCodecForIronShulkerBlock(NetheriteShulkerBoxBlock::new);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NetheriteIronShulkerBoxBlockEntity(pos, state);
    }
}
