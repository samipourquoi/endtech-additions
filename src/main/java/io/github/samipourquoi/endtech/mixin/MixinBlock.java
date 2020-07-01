package io.github.samipourquoi.endtech.mixin;

import io.github.samipourquoi.endtech.helpers.DigCriteriasAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void incrementDigScoreboards(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo info) {
        player.incrementStat(
            DigCriteriasAccessor
                .getDig()
                .getOrCreateStat(new Identifier("endtech", "all"))
        );

        String toolName = Registry.ITEM
                .getId(player.getMainHandStack().getItem())
                .getPath();

        if (toolName.contains("pickaxe")) {
            player.incrementStat(
                DigCriteriasAccessor
                    .getDig()
                    .getOrCreateStat(new Identifier("endtech", "picks"))
            );
        }

        if (toolName.contains("shovel")) {
            player.incrementStat(
                DigCriteriasAccessor
                    .getDig()
                    .getOrCreateStat(new Identifier("endtech", "shovels"))
            );
        }

        if (toolName.contains("axe")) {
            player.incrementStat(
                DigCriteriasAccessor
                    .getDig()
                    .getOrCreateStat(new Identifier("endtech", "axes"))
            );
        }
    }
}
