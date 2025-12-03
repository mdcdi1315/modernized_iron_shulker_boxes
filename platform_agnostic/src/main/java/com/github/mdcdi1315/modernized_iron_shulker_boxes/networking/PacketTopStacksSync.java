package com.github.mdcdi1315.modernized_iron_shulker_boxes.networking;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.ICrystalShulkerBoxBlockEntityDetails;

import com.google.common.collect.Lists;

import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;

public final class PacketTopStacksSync
    implements CustomPacketPayload
{
    public static final Type<PacketTopStacksSync> TYPE = new Type<>(IronShulkerBoxesModInstance.ID("top_stacks_syncronizer"));

    private final BlockPos blockPos;
    private final List<ItemStack> topItemStacks;

    public PacketTopStacksSync(BlockPos blockPos, List<ItemStack> topItemStacks) {
        this.blockPos = blockPos;
        this.topItemStacks = topItemStacks;
    }

    public static final class NetCodec
        implements StreamCodec<RegistryFriendlyByteBuf, PacketTopStacksSync>
    {
        @Override
        public PacketTopStacksSync decode(RegistryFriendlyByteBuf buffer)
        {
            BlockPos blockPos = buffer.readBlockPos();
            int size = buffer.readInt();
            List<ItemStack> topItemStacks = Lists.newArrayListWithExpectedSize(size);

            for (int item = 0; item < size; item++) {
                topItemStacks.add(buffer.readJsonWithCodec(ItemStack.CODEC));
            }

            return new PacketTopStacksSync(blockPos, topItemStacks);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buffer, PacketTopStacksSync packet)
        {
            buffer.writeBlockPos(packet.blockPos);
            int size = packet.topItemStacks.size();
            buffer.writeInt(size);

            for (ItemStack is : packet.topItemStacks) {
                buffer.writeJsonWithCodec(ItemStack.CODEC , is);
            }
        }
    }

    public static void Handle(Player player, PacketTopStacksSync packet)
    {
        Level level = player.level();

        BlockEntity blockEntity = level.getBlockEntity(packet.blockPos);

        if (blockEntity instanceof ICrystalShulkerBoxBlockEntityDetails csb) {
            csb.UpdateTopStacks(packet.topItemStacks);

            Minecraft.getInstance().levelRenderer.blockChanged(level, packet.blockPos, null, null, 0);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
