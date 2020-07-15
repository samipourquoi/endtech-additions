package io.github.samipourquoi.endtech.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.samipourquoi.endtech.helpers.PerfectTradeHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MerchantScreen.class)
public class MixinMerchantScreen extends Screen {
    @Shadow @Final private static Identifier TEXTURE;
    @Shadow private int selectedIndex;

    private static final Identifier PERFECT_TRADES_TEXTURE = new Identifier("textures/gui/container/perfect_trades.png");

    // Just to avoid IDE errors
    protected MixinMerchantScreen(Text title) {
        super(title);
    }

    /**
     * @author samipourquoi
     * @reason Shows a yellow arrow instead of a white one for perfect trades.
     */
    @Overwrite
    private void method_20223(MatrixStack matrixStack, TradeOffer tradeOffer, int i, int j) {
        RenderSystem.enableBlend();

        if (((PerfectTradeHelper) tradeOffer).isPerfectTrade()) {
            this.client.getTextureManager().bindTexture(PERFECT_TRADES_TEXTURE);

            if (!tradeOffer.isDisabled()) {
                drawTexture(matrixStack, i + 5 + 35 + 20, j + 3, this.getZOffset(), 0.0F, 0.0F, 10, 9, 9, 20);
            } else {
                drawTexture(matrixStack, i + 5 + 35 + 20, j + 3, this.getZOffset(), 10.0F, 0.0F, 10, 9, 9, 20);
            }
        } else {
            this.client.getTextureManager().bindTexture(TEXTURE);

            if (tradeOffer.isDisabled()) {
                drawTexture(matrixStack, i + 5 + 35 + 20, j + 3, this.getZOffset(), 25.0F, 171.0F, 10, 9, 256, 512);
            } else {
                drawTexture(matrixStack, i + 5 + 35 + 20, j + 3, this.getZOffset(), 15.0F, 171.0F, 10, 9, 256, 512);
            }
        }
    }
}
