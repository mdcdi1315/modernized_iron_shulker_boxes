package com.github.mdcdi1315.modernized_iron_shulker_boxes.item.cauldron;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxItem;

import net.minecraft.stats.Stats;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LayeredCauldronBlock;

public final class IronShulkerBoxesClearBoxCauldronInteraction
    implements CauldronInteraction
{
    private static void ApplyStackByInteractionType(Player actor, IronShulkerBoxItem box, InteractionHand hand, int original_count)
    {
        // Create empty variants of the given shulker box type, and apply them to the hand
        actor.setItemInHand(hand, new ItemStack(box, original_count));
        // Give to the player the relevant vanilla stat.
        actor.awardStat(Stats.CLEAN_SHULKER_BOX);
    }

    @Override
    public ItemInteractionResult interact(BlockState state, Level level, BlockPos position, Player actor, InteractionHand hand, ItemStack stack)
    {
        if (stack.getItem() instanceof IronShulkerBoxItem itb) {
            if (!level.isClientSide) {
                ApplyStackByInteractionType(actor, itb, hand, stack.getCount());
                LayeredCauldronBlock.lowerFillLevel(state, level, position);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
    }
}
