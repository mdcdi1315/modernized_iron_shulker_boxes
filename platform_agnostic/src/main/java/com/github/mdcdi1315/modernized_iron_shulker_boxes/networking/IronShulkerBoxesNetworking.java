package com.github.mdcdi1315.modernized_iron_shulker_boxes.networking;

import com.github.mdcdi1315.DotNetLayer.System.Version;

import com.github.mdcdi1315.basemodslib.network.NetworkManager;
import com.github.mdcdi1315.basemodslib.network.INetworkBuilder;
import com.github.mdcdi1315.basemodslib.network.ClientSideNetworkPacketRegistrationInfo;

public final class IronShulkerBoxesNetworking
{
    private IronShulkerBoxesNetworking() {}

    public static NetworkManager MANAGER;

    public static void Initialize(NetworkManager manager)
    {
        INetworkBuilder builder = manager.GetBuilder();
        builder.DefineNetworkVersion(new Version(1, 0));
        builder.RegisterClientBoundPacket(new ClientSideNetworkPacketRegistrationInfo<>(PacketTopStacksSync.class, PacketTopStacksSync.TYPE, new PacketTopStacksSync.NetCodec(), PacketTopStacksSync::Handle));
        MANAGER = manager;
    }
}
