package com.github.mdcdi1315.modernized_iron_shulker_boxes.item;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.AbstractIronShulkerBoxBlock;

import net.minecraft.world.item.BlockItem;

public final class IronShulkerBoxItem
    extends BlockItem
{
    public IronShulkerBoxItem(AbstractIronShulkerBoxBlock block, Properties properties) { super(block, properties); }

    @Override
    public boolean canFitInsideContainerItems() { return false; }

    public AbstractIronShulkerBoxBlock getBlock() { return (AbstractIronShulkerBoxBlock) super.getBlock(); }

    // Note that the color tooltip append that was handled here is no longer needed,
    // since we now have a customizable renderer that can display the color of the shulker box.

    /*

    // See boilerplate code in AbstractIronShulkerBoxBlock class.

    import net.minecraft.world.level.block.state.BlockState;
    import net.minecraft.world.item.context.BlockPlaceContext;

    import java.util.Objects;

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state)
    {
        return context.getLevel().setBlock(context.getClickedPos() , state.setValue(
                AbstractIronShulkerBoxBlock.COLOR,
                Objects.requireNonNullElse(
                        context.getItemInHand().get(IronShulkerBoxColorDataComponentType.INSTANCE),
                        IronShulkerBoxColor.NONE
                )
        ), 11);
    }
     */
}
