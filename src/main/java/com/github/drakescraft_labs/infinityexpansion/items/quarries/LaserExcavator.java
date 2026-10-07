package com.github.drakescraft_labs.infinityexpansion.items.quarries;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import com.github.drakescraft_labs.infinityexpansion.InfinityExpansion;
import com.github.drakescraft_labs.infinityexpansion.items.materials.Materials;
import dev.drake.infinitylib.machines.AbstractMachineBlock;
import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.core.attributes.RecipeDisplayItem;
import com.github.drakescraft_labs.slimefun4.libraries.dough.items.CustomItemStack;
import com.github.drakescraft_labs.slimefun4.utils.ChestMenuUtils;
import com.github.drakescraft_labs.slimefun4.legacy.api.inventory.BlockMenu;
import com.github.drakescraft_labs.slimefun4.legacy.api.inventory.BlockMenuPreset;
import com.github.drakescraft_labs.slimefun4.legacy.api.inventory.DirtyChestMenu;

/**
 * High-tech Laser Excavator inspired by modular quarry mechanics.
 * Features chunk-safe subterranean laser mining with modular Focus Lenses.
 *
 * @author JackStar6677-1
 */
@ParametersAreNonnullByDefault
public final class LaserExcavator extends AbstractMachineBlock implements RecipeDisplayItem {

    private static final int INTERVAL =
            InfinityExpansion.config().getInt("quarry-options.laser-excavator.ticks-per-output", 1, 100);

    private static final ItemStack DRILLING = new CustomItemStack(Material.LIME_STAINED_GLASS_PANE, "&aLaser Drilling...");
    private static final ItemStack LENS_INFO = new CustomItemStack(
            Material.PURPLE_STAINED_GLASS_PANE,
            "&dFocus Lens Slot",
            "&7Insert a modular Laser Lens to",
            "&7alter excavation focus and beam speed!",
            "",
            "&8• &cNether Lens&7: Ancient Debris, Scrap & Quartz",
            "&8• &aPrecious Lens&7: Diamonds, Emeralds & Gold",
            "&8• &eOverclock Lens&7: 2x Speed & Yield",
            "&8• &5Void Lens&7: Void Bits & Cosmic Materials"
    );

    private static final int[] OUTPUT_SLOTS = {
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44
    };
    private static final int LENS_SLOT = 49;
    private static final int STATUS_SLOT = 4;

    private static final Material[] STANDARD_MATERIALS = {
            Material.COBBLESTONE, Material.DEEPSLATE, Material.COAL,
            Material.IRON_INGOT, Material.COPPER_INGOT, Material.GOLD_INGOT,
            Material.REDSTONE, Material.LAPIS_LAZULI, Material.DIAMOND,
            Material.EMERALD, Material.AMETHYST_SHARD
    };

    private static final Material[] PRECIOUS_MATERIALS = {
            Material.DIAMOND, Material.DIAMOND, Material.EMERALD,
            Material.GOLD_INGOT, Material.AMETHYST_CLUSTER, Material.RAW_GOLD
    };

    private static final Material[] NETHER_MATERIALS = {
            Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP, Material.QUARTZ,
            Material.QUARTZ, Material.NETHER_GOLD_ORE, Material.GLOWSTONE_DUST,
            Material.CRYING_OBSIDIAN, Material.NETHERRACK, Material.BLACKSTONE
    };

    private final int baseSpeed;

    public LaserExcavator(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe,
                          int baseSpeed) {
        super(category, item, type, recipe);
        this.baseSpeed = baseSpeed;
    }

    public LaserExcavator energyPerTick(int energy) {
        this.energyPerTick = energy;
        return this;
    }

    public LaserExcavator energyCapacity(int capacity) {
        this.energyCapacity = capacity;
        return this;
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset blockMenuPreset) {
        blockMenuPreset.drawBackground(new int[] {
                0, 1, 2, 3, 5, 6, 7, 8, 45, 46, 47, 51, 52, 53
        });
        blockMenuPreset.addItem(48, LENS_INFO, ChestMenuUtils.getEmptyClickHandler());
        blockMenuPreset.addItem(50, LENS_INFO, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    protected int[] getInputSlots(DirtyChestMenu menu, ItemStack item) {
        return new int[0];
    }

    @Override
    protected int[] getInputSlots() {
        return new int[] { LENS_SLOT };
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

    }

    @Override
    protected boolean process(Block b, BlockMenu inv) {
        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, DRILLING);
        }

        if (InfinityExpansion.slimefunTickCount() % INTERVAL != 0) {
            return true;
        }

        LaserLens lens = LaserLens.getLens(inv.getItemInSlot(LENS_SLOT));
        int speed = this.baseSpeed;

        if (lens != null && lens.lensType == LaserLens.LensType.OVERCLOCK) {
            if (getCharge(b.getLocation()) < this.energyPerTick * 2) {
                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, NO_ENERGY_ITEM);
                }
                return false;
            }
            removeCharge(b.getLocation(), this.energyPerTick);
            speed *= 2;
        }

        ItemStack outputItem = rollExcavationItem(lens, speed);

        if (!inv.fits(outputItem, OUTPUT_SLOTS)) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, NO_ROOM_ITEM);
            }
            return false;
        }

        inv.pushItem(outputItem, OUTPUT_SLOTS);

        spawnLaserParticles(b, lens);

        if (inv.hasViewer()) {
            String mode = (lens == null) ? "Standard" : lens.lensType.name();
            inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(
                    Material.LIME_STAINED_GLASS_PANE,
                    "&aLaser Excavating...",
                    "&7Active Lens: &f" + mode,
                    "&7Output Yield: &e" + outputItem.getAmount() + "x " + outputItem.getType().name()
            ));
        }

        return true;
    }

    private ItemStack rollExcavationItem(LaserLens lens, int speed) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        if (lens != null) {
            switch (lens.lensType) {
                case NETHER: {
                    Material mat = NETHER_MATERIALS[random.nextInt(NETHER_MATERIALS.length)];
                    return new ItemStack(mat, Math.max(1, (mat == Material.ANCIENT_DEBRIS || mat == Material.NETHERITE_SCRAP) ? 1 : speed));
                }
                case PRECIOUS: {
                    Material mat = PRECIOUS_MATERIALS[random.nextInt(PRECIOUS_MATERIALS.length)];
                    return new ItemStack(mat, Math.max(1, (mat == Material.DIAMOND || mat == Material.EMERALD) ? Math.min(speed, 2) : speed));
                }
                case VOID: {
                    int roll = random.nextInt(100);
                    if (roll < 35) {
                        ItemStack bit = Materials.VOID_BIT.clone();
                        bit.setAmount(Math.max(1, speed / 2));
                        return bit;
                    } else if (roll < 55) {
                        ItemStack dust = Materials.VOID_DUST.clone();
                        dust.setAmount(1);
                        return dust;
                    } else if (roll < 80) {
                        return new ItemStack(Material.ENDER_PEARL, Math.max(1, speed / 2));
                    } else {
                        return new ItemStack(Material.OBSIDIAN, speed);
                    }
                }
                case OVERCLOCK:
                default:
                    break;
            }
        }

        Material mat = STANDARD_MATERIALS[random.nextInt(STANDARD_MATERIALS.length)];
        return new ItemStack(mat, speed);
    }

    private void spawnLaserParticles(Block b, LaserLens lens) {
        try {
            if (b.getWorld().getNearbyPlayers(b.getLocation(), 16).isEmpty()) {
                return;
            }

            double x = b.getX() + 0.5;
            double y = b.getY() - 0.2;
            double z = b.getZ() + 0.5;

            if (lens != null && lens.lensType == LaserLens.LensType.VOID) {
                b.getWorld().spawnParticle(Particle.PORTAL, x, y, z, 3, 0.1, 0.2, 0.1, 0.05);
            } else if (lens != null && lens.lensType == LaserLens.LensType.OVERCLOCK) {
                Particle.DustOptions dust = new Particle.DustOptions(Color.YELLOW, 1.2F);
                b.getWorld().spawnParticle(Particle.DUST, x, y, z, 2, 0.05, 0.2, 0.05, dust);
            } else if (lens != null && lens.lensType == LaserLens.LensType.PRECIOUS) {
                Particle.DustOptions dust = new Particle.DustOptions(Color.AQUA, 1.2F);
                b.getWorld().spawnParticle(Particle.DUST, x, y, z, 2, 0.05, 0.2, 0.05, dust);
            } else {
                Particle.DustOptions dust = new Particle.DustOptions(Color.RED, 1.2F);
                b.getWorld().spawnParticle(Particle.DUST, x, y, z, 2, 0.05, 0.2, 0.05, dust);
            }
        } catch (Throwable ignored) {
            // Safe fallback across environments
        }
    }

    @Override
    protected int getStatusSlot() {
        return STATUS_SLOT;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();
        for (Material mat : STANDARD_MATERIALS) {
            items.add(new ItemStack(mat, this.baseSpeed));
        }
        for (Material mat : PRECIOUS_MATERIALS) {
            items.add(new ItemStack(mat, this.baseSpeed));
        }
        for (Material mat : NETHER_MATERIALS) {
            items.add(new ItemStack(mat, this.baseSpeed));
        }
        items.add(Materials.VOID_BIT.clone());
        items.add(Materials.VOID_DUST.clone());
        return items;
    }
}
