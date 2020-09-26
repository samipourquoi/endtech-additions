package io.github.samipourquoi.endtech.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.CommandSuggestor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(CommandSuggestor.SuggestionWindow.class)
public class MixinSuggestionWindow {
    @Shadow
    private CommandSuggestor super$;

    @Inject(method = "complete", at = @At("TAIL"))
    private void autoRefresh(CallbackInfo ci) {
        super$.showSuggestions(true);
    }
}
