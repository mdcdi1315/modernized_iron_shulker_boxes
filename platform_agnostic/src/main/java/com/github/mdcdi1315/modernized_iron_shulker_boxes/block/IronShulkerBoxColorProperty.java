package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.NotNull;

import net.minecraft.world.level.block.state.properties.Property;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Collection;

/**
 * A block state property for handling iron shulker boxes color variants.
 */
public final class IronShulkerBoxColorProperty
    extends Property<IronShulkerBoxColor>
{
    private final List<IronShulkerBoxColor> values;

    public IronShulkerBoxColorProperty(String name) {
        super(name , IronShulkerBoxColor.class);
        values = List.of(IronShulkerBoxColor.values());
    }

    @NotNull
    @Override
    public Collection<IronShulkerBoxColor> getPossibleValues() { return values; }

    @NotNull
    @Override
    public String getName(@NotNull IronShulkerBoxColor clr) { return clr.name().toLowerCase(Locale.ROOT); }

    @NotNull
    @Override
    public Optional<IronShulkerBoxColor> getValue(@NotNull String s) {
        for (IronShulkerBoxColor color : values)
        {
            if (s.equalsIgnoreCase(color.name())) {
                return Optional.of(color);
            }
        }
        return Optional.empty();
    }
}
