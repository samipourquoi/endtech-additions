package io.github.samipourquoi.endtech.mixin;

import io.github.samipourquoi.endtech.helpers.StatsAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.StateManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.http.impl.io.IdentityInputStream;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Map;

@Mixin(Block.class)
public abstract class MixinBlock {
    @Shadow @Final protected StateManager<Block, BlockState> stateManager;

    @Shadow protected abstract Block asBlock();

    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void incrementMinedStats(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo info) {
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

        //noinspection MethodCallSideOnly
        Collection<Identifier> tag = BlockTags.getTagGroup().getTagsFor((Block)(Object) this);
        for (Identifier keys: tag) {
            Identifier statTagID = StatsAccessor.CUSTOM_TAGS.get("mined_" + keys.getPath());
            player.incrementStat(statTagID);
        }
    }

    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void incrementUsedStats(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        //noinspection MethodCallSideOnly
        Collection<Identifier> tag = BlockTags.getTagGroup().getTagsFor((Block)(Object) this);
        for (Identifier keys: tag) {
            Identifier statTagID = StatsAccessor.CUSTOM_TAGS.get("used_" + keys.getPath());
            if (placer instanceof PlayerEntity) {
                ((PlayerEntity) placer).incrementStat(statTagID);
            }
        }
    }
}
