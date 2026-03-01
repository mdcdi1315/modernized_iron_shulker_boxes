package com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.NotNull;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.CrystalShulkerBoxMenu;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public final class CrystalShulkerBoxBlockEntity
        extends AbstractIronShulkerBoxBlockEntity
        implements ICrystalShulkerBoxBlockEntityDetails
{
    public static final int TOP_STACKS_COUNT = 8;

    private boolean inventory_touched;
    private final NonNullList<ItemStack> top_stacks;

    public CrystalShulkerBoxBlockEntity(BlockPos pPos, BlockState pState)
    {
        super(IronShulkerBoxesBlockEntities.CRYSTAL_SHULKER_BOX, pPos, pState, IronShulkerBoxesTypes.CRYSTAL);

        inventory_touched = false;
        this.top_stacks = NonNullList.withSize(TOP_STACKS_COUNT, ItemStack.EMPTY);
    }

    public static void tick_crystal(Level pLevel, BlockPos pPos, BlockState pState, AbstractIronShulkerBoxBlockEntity p_entity)
    {
        AbstractIronShulkerBoxBlockEntity.tick(pLevel, pPos, pState, p_entity);

        if (!pLevel.isClientSide && p_entity instanceof CrystalShulkerBoxBlockEntity csb && csb.inventory_touched) {
            csb.inventory_touched = false;
            csb.SortAndDispatchStacksToClient();
        }
    }

    @Override
    protected CrystalShulkerBoxMenu createMenu(int pContainerId, Inventory inventory) {
        return new CrystalShulkerBoxMenu(pContainerId, inventory, this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        // Overriding getUpdateTag and specifying the inventory_touched field to true makes the update when a player loads the block entity.
        // We do not specify any data as we just want to send an upstream request to the client for an update.
        // (Any data will be subsequently sent on the packet that will be dispatched to the client)
        inventory_touched = true;
        return new CompoundTag();
    }

    @Override
    public void setLevel(Level level) {
        inventory_touched = true;
        super.setLevel(level);
    }

    @Override
    protected void OnLoad() { inventory_touched = true; }

    @Override
    protected void OnClosing() { inventory_touched = true; }

    @Override
    public List<ItemStack> GetTopStacks() { return top_stacks; }

    @Override
    public void UpdateTopStacks(List<ItemStack> top_stacks)
    {
        int I = 0;
        while (I < TOP_STACKS_COUNT) {
            this.top_stacks.set(I , (I < top_stacks.size()) ? top_stacks.get(I) : ItemStack.EMPTY);
            I++;
        }
    }

    @Override
    public void SortAndDispatchStacksToClient()
    {
        if (remove || level == null) { return; } // If it is to be removed, do not perform updates, or while on the BEWLR.

        if (HasTransparentSides()) {
            DispatchTopStacksPacket(getLevel() , getBlockPos() , GetTopItemStacks());
        }
    }

    @NotNull
    private List<ItemStack> GetTopItemStacks()
    {
        // We will put the stacks to render in a non-collisible set, then we will create it as a list and dispatch the packet.
        Set<ItemStack> set = new HashSet<>(TOP_STACKS_COUNT);

        List<ItemStack> items = getItems();
        int size = items.size(), count = Math.min(size, TOP_STACKS_COUNT);
        for (int C = 0, I = 0; C < count && I < size; ) // Run 8 or fewer times, depending on the items contained in the shulker box.
        {
            for (; I < size; I++) {
                // Check, this item type has been already pushed to the top stacks list?
                ItemStack o = items.get(I);
                if (o != ItemStack.EMPTY && set.add(o)) { C++; } // If yes, continue finding the next element.
            }
        }
        return List.copyOf(set);
    }

    @Override
    public boolean HasTransparentSides() { return GetShulkerBoxType().IsTransparent(); }
}
