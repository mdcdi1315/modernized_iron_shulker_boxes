package com.github.mdcdi1315.modernized_iron_shulker_boxes.item;

import com.github.mdcdi1315.DotNetLayer.System.Func1;
import com.github.mdcdi1315.DotNetLayer.System.ArgumentNullException;
import com.github.mdcdi1315.DotNetLayer.System.InvalidOperationException;

import com.github.mdcdi1315.basemodslib.item.IBlockEntityItem;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.AbstractIronShulkerBoxBlock;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.datacomponent.IronShulkerBoxColorDataComponentType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Locale;

public final class IronShulkerBoxItem
    extends BlockItem
    implements IBlockEntityItem
{
    private final Func1<BlockEntity> block_ent_to_render_getter;

    public static ItemStack CreateItemStack(IronShulkerBoxColor color, AbstractIronShulkerBoxBlock block) {
        var is = new ItemStack(IronShulkerBoxesItems.ResolveShulkerBoxItemByBlock(block), 1);
        is.applyComponents(DataComponentMap.builder().set(IronShulkerBoxColorDataComponentType.INSTANCE , color).build());
        return is;
    }

    public static final class Builder
    {
        private AbstractIronShulkerBoxBlock underlying;
        private Item.Properties properties;
        private Func1<BlockEntity> renderer_getter;

        public Builder() {
            underlying = null;
            properties = null;
            renderer_getter = null;
        }

        public Builder WithUnderlyingBlock(AbstractIronShulkerBoxBlock b)
            throws ArgumentNullException
        {
            ArgumentNullException.ThrowIfNull(b, "b");
            underlying = b;
            return this;
        }

        public Builder WithProperties(Item.Properties props)
            throws ArgumentNullException
        {
            ArgumentNullException.ThrowIfNull(props, "props");
            properties = props;
            return this;
        }

        public Builder WithBlockEntityRenderingFunction(Func1<BlockEntity> b_provider)
            throws ArgumentNullException
        {
            ArgumentNullException.ThrowIfNull(b_provider, "b_provider");
            renderer_getter = b_provider;
            return this;
        }

        public IronShulkerBoxItem Build() {
            if (underlying == null || properties == null) {
                throw new InvalidOperationException("Cannot build the item before all required data are set!");
            }
            if (renderer_getter == null) {
                return new IronShulkerBoxItem(underlying, properties);
            } else {
                return new IronShulkerBoxItem(underlying, properties, renderer_getter);
            }
        }
    }

    private static BlockEntity DefaultBlockEntity() {
        return IronShulkerBoxesBlockEntities.ITEM_REND_IRON_SHULKER_BOX;
    }

    public IronShulkerBoxItem(AbstractIronShulkerBoxBlock block, Properties properties) {
        super(block, properties);
        block_ent_to_render_getter = IronShulkerBoxItem::DefaultBlockEntity;
    }

    public IronShulkerBoxItem(AbstractIronShulkerBoxBlock block, Properties properties, Func1<BlockEntity> block_ent_rend_getter) {
        super(block, properties);
        block_ent_to_render_getter = block_ent_rend_getter;
    }

    @Override
    public boolean canFitInsideContainerItems()
    {
        Block b = this.getBlock();
        return !((b instanceof AbstractIronShulkerBoxBlock) || (b instanceof ShulkerBoxBlock));
    }

    @Override
    public BlockEntity GetBlockEntity() {
        return block_ent_to_render_getter.function();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(stack, context, components, tooltipFlag);
        IronShulkerBoxColor color;
        if ((color = stack.getOrDefault(IronShulkerBoxColorDataComponentType.INSTANCE, IronShulkerBoxColor.NONE)) != IronShulkerBoxColor.NONE) {
            components.add(Component.translatable(String.format("modernized_iron_shulker_boxes.box_color_variant.%s" , color.GetNamespacedID())).withColor(0xff808080));
        }
    }

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
