package com.iafenvoy.thinkbeforedrop.mixin;

import com.iafenvoy.thinkbeforedrop.DropManager;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin extends AbstractClientPlayer {
    @Shadow
    @Final
    protected Minecraft minecraft;

    public LocalPlayerMixin(ClientLevel world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "drop(Z)Z", at = @At("HEAD"), cancellable = true)
    public void beforeDropItem(boolean dropEntireStack, CallbackInfoReturnable<Boolean> cir) {
        if (DropManager.shouldThrow(this.getInventory()/*? >=1.21.5 {*/.getSelectedItem()/*?} else {*//*.getSelected()*//*?}*/, this.getInventory()/*? >=1.21.5 {*/.getSelectedSlot()/*?} else {*//*.selected*//*?}*/))
            return;
        assert this.minecraft.player != null;
        this.minecraft.player.displayClientMessage(DropManager.getWarningText(), true);
        cir.setReturnValue(false);
    }
}
