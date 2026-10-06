package com.github.drakescraft_labs.infinityexpansion.items.generators;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.github.drakescraft_labs.slimefun4.core.attributes.ProtectionType;
import com.github.drakescraft_labs.slimefun4.api.player.PlayerProfile;
import com.github.drakescraft_labs.infinityexpansion.utils.Util;
import com.github.drakescraft_labs.infinityexpansion.items.materials.Materials;
import dev.drake.infinitylib.common.Scheduler;
import dev.drake.infinitylib.common.StackUtils;
import dev.drake.infinitylib.machines.MenuBlock;
import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.core.attributes.EnergyNetProvider;
import com.github.drakescraft_labs.slimefun4.core.attributes.RecipeDisplayItem;
import com.github.drakescraft_labs.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import com.github.drakescraft_labs.slimefun4.legacy.api.BlockStorage;
import com.github.drakescraft_labs.slimefun4.legacy.api.inventory.BlockMenu;
import com.github.drakescraft_labs.slimefun4.legacy.api.inventory.BlockMenuPreset;
import com.github.drakescraft_labs.slimefun4.legacy.api.inventory.DirtyChestMenu;

/**
 * A reactor that generates huge power but costs infinity ingots and void ingots
 *
 * @author Mooy1
 */
@ParametersAreNonnullByDefault
public final class InfinityReactor extends MenuBlock implements EnergyNetProvider, RecipeDisplayItem {

    // Exponentially accelerated fuel burn: 2 minutes for Infinity Ingot, 30 seconds for Void Ingot
    private static final int INFINITY_INTERVAL = 2400;
    private static final int VOID_INTERVAL = 600;
    private static final int[] INPUT_SLOTS = { 10, 16 };
    private static final int STATUS_SLOT = 13;

    private final int gen;

    public InfinityReactor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int gen) {
        super(category, item, recipeType, recipe);
        this.gen = gen;
    }

    @Override
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (BlockStorage.getLocationInfo(b.getLocation(), "progress") == null) {
            BlockStorage.addBlockInfo(b, "progress", "0");
        }
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset blockMenuPreset) {
        blockMenuPreset.drawBackground(new CustomItemStack(Material.WHITE_STAINED_GLASS_PANE,
                "&fInfinity Ingot Input"), new int[] {
                0, 1, 2,
                9, 11,
                18, 19, 20
        });
        blockMenuPreset.drawBackground(new int[] {
                3, 4, 5,
                12, 13, 14,
                21, 22, 23
        });
        blockMenuPreset.drawBackground(new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE,
                "&8Void Ingot Input"), new int[] {
                6, 7, 8,
                15, 17,
                24, 25, 26
        });
    }

    @Nonnull
    @Override
    public int[] getInputSlots(DirtyChestMenu menu, ItemStack item) {
        String input = StackUtils.getId(item);
        if (Materials.VOID_INGOT.getItemId().equals(input)) {
            return new int[] { INPUT_SLOTS[1] };
        }
        else if (Materials.INFINITE_INGOT.getItemId().equals(input)) {
            return new int[] { INPUT_SLOTS[0] };
        }
        else {
            return new int[0];
        }
    }

    @Override
    protected int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    protected int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config config) {
        BlockMenu inv = BlockStorage.getInventory(l);

        // getIntData en vez de parseInt directo: si "progress" falta, lo repone a 0 en vez de
        // lanzar. Importa mas de lo que parece, porque getGeneratedOutput corre dentro de
        // EnergyNet.tickAllGenerators: una NumberFormatException aqui aborta el tick de la RED
        // ELECTRICA COMPLETA, y todas las maquinas conectadas se quedan sin corriente ese ciclo.
        // Visto en error-reports/2026-08-18-17-52.err (INFINITY_REACTOR en world 4961,-52,-5950).
        int progress = Util.getIntData("progress", l);
        ItemStack infinityInput = inv.getItemInSlot(INPUT_SLOTS[0]);
        ItemStack voidInput = inv.getItemInSlot(INPUT_SLOTS[1]);

        if (progress == 0) { //need infinity + void

            if (infinityInput == null || !Materials.INFINITE_INGOT.getItemId().equals(StackUtils.getId(infinityInput))) { //wrong input

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&cInput more &fInfinity Ingots"));
                }
                return 0;

            }

            if (voidInput == null || !Materials.VOID_INGOT.getItemId().equals(StackUtils.getId(voidInput))) { //wrong input

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&cInput more &8Void Ingots"));
                }
                return 0;

            }

            //correct input
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,
                        "&aStarting Generation",
                        "&aTime until infinity ingot needed: " + INFINITY_INTERVAL,
                        "&aTime until void ingot needed: " + VOID_INTERVAL
                ));
            }
            inv.consumeItem(INPUT_SLOTS[0]);
            inv.consumeItem(INPUT_SLOTS[1]);
            inv.markDirty();
            BlockStorage.addBlockInfo(l, "progress", "1");
            return this.gen;

        }

        if (progress >= INFINITY_INTERVAL) { //done

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.LIME_STAINED_GLASS_PANE, "&aFinished Generation"));
            }
            BlockStorage.addBlockInfo(l, "progress", "0");
            return this.gen;

        }

        if (Math.floorMod(progress, VOID_INTERVAL) == 0) { //need void

            if (voidInput == null || !Materials.VOID_INGOT.getItemId().equals(StackUtils.getId(voidInput))) { //wrong input

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&cInput more &8Void Ingots"));
                }
                return 0;

            }

            //right input
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,
                        "&aGenerating...",
                        "&aTime until infinity ingot needed: " + (INFINITY_INTERVAL - progress),
                        "&aTime until void ingot needed: " + (VOID_INTERVAL - Math.floorMod(progress, VOID_INTERVAL))
                ));
            }
            BlockStorage.addBlockInfo(l, "progress", String.valueOf(progress + 1));
            inv.consumeItem(INPUT_SLOTS[1]);
            inv.markDirty();
            return this.gen;

        }

        //generate

        // Emit active cosmic radiation pulse to nearby unprotected players
        if (l.getWorld() != null && Math.floorMod(progress, 10) == 0) {
            for (Player p : l.getWorld().getPlayers()) {
                if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) continue;
                if (p.getLocation().distanceSquared(l) <= 64.0) { // 8 blocks
                    PlayerProfile.get(p, profile -> {
                        if (profile != null && !profile.hasFullProtectionAgainst(ProtectionType.RADIATION)) {
                            // TickerTask and PlayerProfile callbacks can be asynchronous. Entity mutations
                            // must return to the server thread before touching Bukkit's player state.
                            Scheduler.run(() -> {
                                if (!p.isOnline()) return;
                                p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 80, 1));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 0));
                                p.damage(2.0); // 1 heart environmental radiation damage
                            });
                        }
                    });
                }
            }
        }

        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,
                            "&aGenerating...",
                            "&aTime until infinity ingot needed: " + (INFINITY_INTERVAL - progress),
                            "&aTime until void ingot needed: " + (VOID_INTERVAL - Math.floorMod(progress, VOID_INTERVAL))
                    )
            );
        }
        BlockStorage.addBlockInfo(l, "progress", String.valueOf(progress + 1));
        return this.gen;
    }

    @Override
    public int getCapacity() {
        return this.gen * 1000;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        ItemStack item = new CustomItemStack(Materials.INFINITE_INGOT, Materials.INFINITE_INGOT.getDisplayName(),
                "", ChatColor.GOLD + "Lasts for 2 minutes (2,400 ticks)",
                ChatColor.RED + "Emits deadly cosmic radiation!");
        items.add(item);
        items.add(null);

        item = new CustomItemStack(Materials.VOID_INGOT, Materials.VOID_INGOT.getDisplayName(),
                "", ChatColor.GOLD + "Lasts for 30 seconds (600 ticks)",
                ChatColor.RED + "Continuous injection required");
        items.add(item);
        items.add(null);

        return items;
    }

}
