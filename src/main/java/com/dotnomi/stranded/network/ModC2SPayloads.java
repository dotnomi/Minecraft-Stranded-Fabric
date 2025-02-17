package com.dotnomi.stranded.network;

import com.dotnomi.stranded.network.packet.ExampleC2SPacket;
import com.dotnomi.stranded.network.packet.FabricatorCraftingFinishedC2SPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModC2SPayloads {
  public static void initialize() {
    ServerPlayNetworking.registerGlobalReceiver(ExampleC2SPacket.IDENTIFIER, ExampleC2SPacket::handlePacket);
    ServerPlayNetworking.registerGlobalReceiver(FabricatorCraftingFinishedC2SPayload.IDENTIFIER, FabricatorCraftingFinishedC2SPayload::handlePacket);
  }
}
