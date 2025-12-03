package com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.networking.PacketTopStacksSync;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.networking.IronShulkerBoxesNetworking;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public interface ICrystalShulkerBoxBlockEntityDetails
{
    List<ItemStack> GetTopStacks();

    void UpdateTopStacks(List<ItemStack> top_stacks);

    default void DispatchTopStacksPacket(Level level, BlockPos world_pos, List<ItemStack> item_stacks_to_dispatch)
    {
        if (level instanceof ServerLevel lvl) {
            IronShulkerBoxesNetworking.MANAGER.SendToTracking(lvl , world_pos , new PacketTopStacksSync(world_pos, item_stacks_to_dispatch));
            // IronShulkerBoxesNetworking.INSTANCE.send(new PacketTopStacksSync(this.getChestWorldPosition(), stacks), PacketDistributor.TRACKING_CHUNK.with(this.getChestLevel().getChunkAt(this.getChestWorldPosition())));
        }
    }

    void SortAndDispatchStacksToClient();

    // A value whether it is meaningful to dispatch the top stacks sync packet, and if the renderer should render those top stacks.
    boolean HasTransparentSides();
}
