package com.github.mdcdi1315.modernized_iron_shulker_boxes.item;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.AbstractIronShulkerBoxBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;

import java.util.List;

public final class IronShulkerBoxVanillaUpgradeItem
    extends Item
{
    private final AbstractIronShulkerBoxBlock target;

    public IronShulkerBoxVanillaUpgradeItem(AbstractIronShulkerBoxBlock target)
    {
        super(new Properties());
        this.target = target;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag)
    {
        super.appendHoverText(stack, context, components, flag);
        components.add(Component.translatable("modernized_iron_shulker_boxes.upgrading.vanilla_shulker_box_upgrade.upgrade", target.getName().getString()).withColor(0xff808080));
        components.add(Component.translatable("modernized_iron_shulker_boxes.upgrading.shulker_box_upgrade.color").withColor(0xff808080)); // Gray color
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level lv = context.getLevel();
        BlockPos position = context.getClickedPos();
        if (lv.getBlockEntity(position) instanceof ShulkerBoxBlockEntity source_data)
        {
            BlockState bs = lv.getBlockState(position);
            if (bs.getBlock() instanceof ShulkerBoxBlock) {
                if (lv.isClientSide) {
                    // We are on client-side, send success instead.
                    // The code to execute is otherwise server-side only.
                    Player actor = context.getPlayer();
                    if (actor != null) {
                        context.getItemInHand().consume(1 , actor);
                    }
                    return InteractionResult.SUCCESS;
                } else {
                    return UseItem_Server(context, bs, source_data);
                }
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            return InteractionResult.FAIL;
        }
    }

    private InteractionResult UseItem_Server(UseOnContext context, BlockState original, ShulkerBoxBlockEntity source_entity)
    {
        Level lv = context.getLevel();
        BlockPos position = context.getClickedPos();
        // OK. We are on the server's side. Get the block entity inventory, read the color, read the rotation and then fabricate the block state and the block entity.
        CompoundTag ct = source_entity.saveWithoutMetadata(lv.registryAccess());
        BlockState placing = GenerateBlockState(original, target.defaultBlockState());
        if (lv.setBlockAndUpdate(position, placing)) {
            BlockEntity new_entity = target.newBlockEntity(position, placing);
            if (new_entity == null) {
                DispatchMessageToPlayer(context, "Cannot execute the action because the block entity could not be created.");
                return InteractionResult.FAIL;
            } else {
                new_entity.loadWithComponents(ct , lv.registryAccess());
                lv.setBlockEntity(new_entity);
                Player actor = context.getPlayer();
                // Notify the game that the block has been changed - just let the game know this!
                lv.gameEvent(GameEvent.BLOCK_CHANGE, position, GameEvent.Context.of(actor , placing));
                // Consume the item if we have a valid actor.
                if (actor != null) {
                    context.getItemInHand().consume(1 , actor);
                }
                return InteractionResult.CONSUME;
            }
        } else {
            DispatchMessageToPlayer(context, "Cannot execute the action because the new block state could not be placed.");
            return InteractionResult.FAIL;
        }
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) { return 0; }

    private static void DispatchMessageToPlayer(UseOnContext cxt, String message)
    {
        Player p = cxt.getPlayer();
        if (p != null) {
            p.displayClientMessage(Component.literal(message), true);
        }
    }

    private static BlockState GenerateBlockState(BlockState source_block, BlockState target_default)
    {
        BlockState ret = target_default.setValue(AbstractIronShulkerBoxBlock.FACING , source_block.getValue(ShulkerBoxBlock.FACING));
        Block b = source_block.getBlock();
        if (b == Blocks.SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.NONE);
        } else if (b == Blocks.WHITE_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.WHITE);
        } else if (b == Blocks.ORANGE_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.ORANGE);
        } else if (b == Blocks.MAGENTA_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.MAGENTA);
        } else if (b == Blocks.LIGHT_BLUE_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.LIGHT_BLUE);
        } else if (b == Blocks.YELLOW_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.YELLOW);
        } else if (b == Blocks.LIME_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.LIME);
        } else if (b == Blocks.PINK_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.PINK);
        } else if (b == Blocks.GRAY_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.GRAY);
        } else if (b == Blocks.LIGHT_GRAY_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.LIGHT_GRAY);
        } else if (b == Blocks.CYAN_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.CYAN);
        } else if (b == Blocks.PURPLE_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.PURPLE);
        } else if (b == Blocks.BLUE_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.BLUE);
        } else if (b == Blocks.BROWN_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.BROWN);
        } else if (b == Blocks.GREEN_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.GREEN);
        } else if (b == Blocks.RED_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.RED);
        } else if (b == Blocks.BLACK_SHULKER_BOX) {
            ret = ret.setValue(AbstractIronShulkerBoxBlock.COLOR , IronShulkerBoxColor.BLACK);
        }
        return ret;
    }
}
