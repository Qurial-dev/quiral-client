package com.quiral.client.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
abstract class AbstractClientPlayerMixin extends Player {

  @Unique
  ResourceLocation testResourceLocation;

  AbstractClientPlayerMixin(Level level, BlockPos pos, float f, GameProfile profile) {
    super(level, pos, f, profile);
  }

  @Inject(at = @At("HEAD"), method = "getCloakTextureLocation", cancellable = true)
  void getCloakTextureLocation(CallbackInfoReturnable<ResourceLocation> info) {
    if (uuid.version() != 2) {
      if (testResourceLocation == null) {
        testResourceLocation = Minecraft.getInstance().getSkinManager().registerTexture(
            new MinecraftProfileTexture("https://skinmc.net/img/capes/Minecon-2016.png", null),
            MinecraftProfileTexture.Type.CAPE);
      }
      info.setReturnValue(testResourceLocation);
    }
  }

}