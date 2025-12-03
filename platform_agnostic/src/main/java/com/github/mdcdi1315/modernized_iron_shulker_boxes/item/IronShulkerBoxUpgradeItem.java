package com.github.mdcdi1315.modernized_iron_shulker_boxes.item;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.AbstractIronShulkerBoxBlock;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.AbstractIronShulkerBoxBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public final class IronShulkerBoxUpgradeItem
    extends Item
{
    private final AbstractIronShulkerBoxBlock source, target;

    public IronShulkerBoxUpgradeItem(AbstractIronShulkerBoxBlock source, AbstractIronShulkerBoxBlock target)
    {
        super(new Properties());
        this.source = source;
        this.target = target;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag)
    {
        super.appendHoverText(stack, context, components, flag);
        components.add(Component.translatable("modernized_iron_shulker_boxes.upgrading.shulker_box_upgrade.upgrade", source.getName().getString(), target.getName().getString()).withColor(0xff808080));
        components.add(Component.translatable("modernized_iron_shulker_boxes.upgrading.shulker_box_upgrade.color").withColor(0xff808080)); // Gray color
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level lv = context.getLevel();
        BlockPos position = context.getClickedPos();
        if (lv.getBlockEntity(position) instanceof AbstractIronShulkerBoxBlockEntity source_data)
        {
            BlockState bs = lv.getBlockState(position);
            if (bs.is(source)) {
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

    private InteractionResult UseItem_Server(UseOnContext context, BlockState original, AbstractIronShulkerBoxBlockEntity source_entity)
    {
        Level lv = context.getLevel();
        BlockPos position = context.getClickedPos();
        // OK. We are on the server's side. Get the block entity inventory, read the color, read the rotation and then fabricate the block state and the block entity.
        CompoundTag ct = source_entity.saveWithoutMetadata(lv.registryAccess());
        BlockState placing = target.defaultBlockState()
                .setValue(AbstractIronShulkerBoxBlock.COLOR, original.getValue(AbstractIronShulkerBoxBlock.COLOR))
                .setValue(AbstractIronShulkerBoxBlock.FACING, original.getValue(AbstractIronShulkerBoxBlock.FACING));
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
}
