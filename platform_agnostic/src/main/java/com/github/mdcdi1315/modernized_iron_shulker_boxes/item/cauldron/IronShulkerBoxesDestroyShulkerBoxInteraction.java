package com.github.mdcdi1315.modernized_iron_shulker_boxes.item.cauldron;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxItem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.block.state.BlockState;

public final class IronShulkerBoxesDestroyShulkerBoxInteraction
    implements CauldronInteraction
{
    private static void UnpackContainerContents(ItemStack container, Level level, Player actor)
    {
        ItemEntity dropped;
        Inventory iv = actor.getInventory();
        var contents = container.get(DataComponents.CONTAINER);
        if (contents != null)
        {
            for (ItemStack is : contents.nonEmptyItems())
            {
                if (!iv.add(is) && (dropped = actor.drop(is , false)) != null) {
                    level.addFreshEntity(dropped);
                }
            }
            if (level.random.nextInt(3) == 0) {
                // This is applied with a cauldron filled with lava.
                // I want to indicate that the operation is very dangerous, so I will ignite the player!
                if (!actor.fireImmune()) {
                    actor.igniteForSeconds(4F);
                }
                actor.hurt(actor.damageSources().inFire() , 0.992F);
            }
        }
    }

    @Override
    public ItemInteractionResult interact(BlockState state, Level level, BlockPos position, Player actor, InteractionHand hand, ItemStack stack)
    {
        if (stack.getItem() instanceof IronShulkerBoxItem) {
            if (!level.isClientSide) {
                UnpackContainerContents(stack, level, actor);
                BlockState apply = Blocks.CAULDRON.defaultBlockState();
                if (level.setBlockAndUpdate(position, apply)) {
                    level.gameEvent(GameEvent.BLOCK_CHANGE , position , GameEvent.Context.of(actor , apply));
                }
            }

            stack.consume(1, actor);

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
    }
}
