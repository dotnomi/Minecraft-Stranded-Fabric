package com.dotnomi.stranded.network;

import com.dotnomi.stranded.network.packet.ExampleS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ModS2CPayloads {
  public static void initialize() {
    ClientPlayNetworking.registerGlobalReceiver(ExampleS2CPacket.IDENTIFIER, ExampleS2CPacket::handlePacket);
  }
}
