package io.github.samipourquoi.endtech.mixin;

import io.github.samipourquoi.endtech.helpers.PerfectTradeHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.Tag;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TradeOffer.class)
public class MixinTradeOffer implements PerfectTradeHelper {
    private TradeOffer mixinThis;

    @Shadow @Final private ItemStack firstBuyItem;

    @Shadow @Final private ItemStack sellItem;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void test(CallbackInfo info) {
        mixinThis = ((TradeOffer) (Object) this);
    }

    @Override
    public boolean isPerfectTrade() {
        ItemStack sellItemStack = mixinThis.getSellItem();
        Item sellItem = sellItemStack.getItem();

        switch (sellItem.getClass().getSimpleName()) {
            case "EnchantedBookItem":
                // Get the first (and only) enchantment of the sell item
                Enchantment enchantment = (Enchantment) EnchantmentHelper.get(sellItemStack).keySet().toArray()[0];

                int level = EnchantmentHelper.get(sellItemStack).get(enchantment);
                int maxLevel = enchantment.getMaxLevel();
                int treasureMultiplier = enchantment.isTreasure() ? 2 : 1;
                int perfectPrice = (2 + 3 * level) * treasureMultiplier;

                if (mixinThis.getOriginalFirstBuyItem().getCount() == perfectPrice && level == maxLevel)
                    return true;
                break;
        }


        return false;
    }
}
