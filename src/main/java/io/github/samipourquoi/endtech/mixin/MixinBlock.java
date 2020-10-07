package io.github.samipourquoi.endtech.mixin;

import io.github.samipourquoi.endtech.helpers.StatsAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void incrementDigScoreboards(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo info) {
        // stat farms!
        if (!this.equals(Blocks.ICE)) {
            player.incrementStat(StatsAccessor.DIG);

            if (stack.getItem() instanceof PickaxeItem) {
                player.incrementStat(StatsAccessor.PICKS);
            } else if (stack.getItem() instanceof AxeItem) {
                player.incrementStat(StatsAccessor.AXES);
            } else if (stack.getItem() instanceof ShovelItem) {
                player.incrementStat(StatsAccessor.SHOVELS);
            } else if (stack.getItem() instanceof HoeItem) {
                player.incrementStat(StatsAccessor.HOES);
            }
        }
    }
}
