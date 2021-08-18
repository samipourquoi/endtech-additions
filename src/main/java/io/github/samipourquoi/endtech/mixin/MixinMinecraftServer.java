package io.github.samipourquoi.endtech.mixin;

import io.github.samipourquoi.endtech.ETAdditionsSettings;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void loadConf(CallbackInfo ci) {
        ETAdditionsSettings.loadConfigSettings((MinecraftServer) (Object) this);
    }
}
