package com.github.drakescraft_labs.infinityexpansion.items.quarries;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

import com.github.drakescraft_labs.infinityexpansion.categories.Groups;
import dev.drake.infinitylib.common.StackUtils;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItem;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;

/**
 * Modular Focus Lenses for the Laser Excavator.
 * Each lens alters the excavation frequency, target mineral distribution, or mining speed.
 *
 * @author JackStar6677-1
 */
public final class LaserLens extends SlimefunItem {

    public enum LensType {
        NETHER,
        PRECIOUS,
        OVERCLOCK,
        VOID
    }

    private static final Map<String, LaserLens> LENSES = new HashMap<>();

    public final LensType lensType;

    @Nullable
    public static LaserLens getLens(@Nullable ItemStack item) {
        if (item == null) {
            return null;
        }
        return LENSES.get(StackUtils.getId(item));
    }

    public LaserLens(SlimefunItemStack item, LensType lensType, RecipeType recipeType, ItemStack[] recipe) {
        super(Groups.MAIN_MATERIALS, item, recipeType, recipe);
        this.lensType = lensType;
        LENSES.put(getId(), this);
    }
}
