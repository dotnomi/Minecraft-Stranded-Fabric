package com.dotnomi.stranded.screenhandler;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.network.packet.FabricatorPayload;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlerTypes {
  public static final ScreenHandlerType<FabricatorScreenHandler> FABRICATOR = register("fabricator", FabricatorScreenHandler::new, FabricatorPayload.CODEC);

  public static void initialize() {}

  public static <T extends ScreenHandler, D extends CustomPayload> ExtendedScreenHandlerType<T, D> register (
    String id,
    ExtendedScreenHandlerType.ExtendedFactory<T, D> factory,
    PacketCodec<? super RegistryByteBuf, D> codec
  ) {
    return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Stranded.MOD_ID, id), new ExtendedScreenHandlerType<>(factory, codec));
  }
}
