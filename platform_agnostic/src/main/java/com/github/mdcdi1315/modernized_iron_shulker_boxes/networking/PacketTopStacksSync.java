package com.github.mdcdi1315.modernized_iron_shulker_boxes.networking;

import com.github.mdcdi1315.basemodslib.network.NetworkHelpers;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.ICrystalShulkerBoxBlockEntityDetails;

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
import java.util.ArrayList;

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
            BlockPos blockPos = NetworkHelpers.ReadDerivedVec3iUnsafe(buffer, BlockPos::new);
            int size = NetworkHelpers.Read7BitEncodedIntUnsafe(buffer);
            List<ItemStack> topItemStacks = new ArrayList<>(size);

            for (int item = 0; item < size; item++) {
                topItemStacks.add(buffer.readJsonWithCodec(ItemStack.CODEC));
            }

            return new PacketTopStacksSync(blockPos, topItemStacks);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buffer, PacketTopStacksSync packet)
        {
            NetworkHelpers.WriteVec3iUnsafe(buffer, packet.blockPos);
            NetworkHelpers.Write7BitEncodedIntUnsafe(buffer, packet.topItemStacks.size());

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
