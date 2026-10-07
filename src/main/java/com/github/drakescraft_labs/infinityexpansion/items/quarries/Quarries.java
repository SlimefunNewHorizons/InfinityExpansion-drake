package com.github.drakescraft_labs.infinityexpansion.items.quarries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import com.github.drakescraft_labs.infinityexpansion.InfinityExpansion;
import com.github.drakescraft_labs.infinityexpansion.categories.Groups;
import com.github.drakescraft_labs.infinityexpansion.items.SlimefunExtension;
import com.github.drakescraft_labs.infinityexpansion.items.blocks.InfinityWorkbench;
import com.github.drakescraft_labs.infinityexpansion.items.gear.Gear;
import com.github.drakescraft_labs.infinityexpansion.items.materials.Materials;
import dev.drake.infinitylib.machines.MachineLore;
import com.github.drakescraft_labs.slimefun4.api.MinecraftVersion;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.implementation.Slimefun;
import com.github.drakescraft_labs.slimefun4.implementation.SlimefunItems;

@UtilityClass
public final class Quarries {

    public static final SlimefunItemStack BASIC_QUARRY = new SlimefunItemStack(
            "BASIC_QUARRY",
            Material.CHISELED_SANDSTONE,
            "&9Basic Quarry",
            "&7Automatically mines overworld ores",
            "",
            MachineLore.speed(1),
            MachineLore.energyPerSecond(300)
    );
    public static final SlimefunItemStack ADVANCED_QUARRY = new SlimefunItemStack(
            "ADVANCED_QUARRY",
            Material.CHISELED_RED_SANDSTONE,
            "&cAdvanced Quarry",
            "&7Automatically mines overworld and nether ores",
            "",
            MachineLore.speed(2),
            MachineLore.energyPerSecond(900)
    );
    public static final SlimefunItemStack VOID_QUARRY = new SlimefunItemStack(
            "VOID_QUARRY",
            Material.CHISELED_NETHER_BRICKS,
            "&8Void Quarry",
            "&7Automatically mines overworld and nether ores",
            "",
            MachineLore.speed(6),
            MachineLore.energyPerSecond(3600)
    );
    public static final SlimefunItemStack INFINITY_QUARRY = new SlimefunItemStack(
            "INFINITY_QUARRY",
            Material.CHISELED_POLISHED_BLACKSTONE,
            "&bInfinity Quarry",
            "&7Automatically mines overworld and nether ores",
            "",
            MachineLore.speed(64),
            MachineLore.energyPerSecond(36000)
    );

    public static final SlimefunItemStack LASER_EXCAVATOR = new SlimefunItemStack(
            "LASER_EXCAVATOR",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMzNDE1MTdmNGQxODY1YmViY2UzM2Y0YTFhZmI1MmYxM2FhYjhkMjZlZDY0ZTdlMjI5YThiNThiMWY1YjNkIn19fQ==",
            "&c&lLaser Excavator",
            "&7High-tech subterranean beam excavator",
            "&7Mines deep resources without terrain destruction",
            "&7Supports modular &dFocus Lenses&7 for specialized yields",
            "",
            MachineLore.speed(4),
            MachineLore.energyPerSecond(2400)
    );

    public static final SlimefunItemStack NETHER_FOCUS_LENS = new SlimefunItemStack(
            "LASER_LENS_NETHER",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTlhOTJlNGNjOWE4NWI0YjBhODNhM2U3ZTE0YTE5ZGQzODE0ZGFlNjIzZTAwY2Y5YTM3ZThkYjRkZGM5Yjc1NyJ9fX0=",
            "&cLaser Focus Lens: &4Nether",
            "&7Modular upgrade for the Laser Excavator",
            "&7Calibrates thermal beam frequency to extract",
            "&cAncient Debris&7, &cNetherite Scrap&7, and &cQuartz"
    );

    public static final SlimefunItemStack PRECIOUS_FOCUS_LENS = new SlimefunItemStack(
            "LASER_LENS_PRECIOUS",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDlmNjEzZDIwYjQ2ZjBjYjg5ZWMzNzRmYWY0ZjQ3MzBmYTVkYzEwOTBhMGY1MmRmZmJjM2MwY2Q1OTQ2Mjg5NCJ9fX0=",
            "&aLaser Focus Lens: &bPrecious Gems",
            "&7Modular upgrade for the Laser Excavator",
            "&7Refracts subterranean pulses to focus on",
            "&bDiamonds&7, &aEmeralds&7, and &6Gold"
    );

    public static final SlimefunItemStack ENERGY_CONVERTER_LENS = new SlimefunItemStack(
            "LASER_LENS_OVERCLOCK",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2E3Y2RhOTAwNGZjMTk3ZDY2YWZiYzJiMDAzYTViOWVmMTNjZjQ2MDBiMWZjNzQ5MDA2NzU5MGYwNDcxODFlIn19fQ==",
            "&eLaser Overclock Lens: &6High Frequency",
            "&7Modular upgrade for the Laser Excavator",
            "&7Overclocks laser pulse frequency to",
            "&eDouble excavation speed and yield",
            "&cConsumes 2x energy per cycle"
    );

    public static final SlimefunItemStack VOID_FOCUS_LENS = new SlimefunItemStack(
            "LASER_LENS_VOID",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIwMWFlMWE4YTA0ZGY1MjY1NmY1ZTQ4MTNlMWZiY2Y5Nzg3N2RiYmZiYzQyNjhkMDQzMTZkNmY5Zjc1MyJ9fX0=",
            "&5Laser Focus Lens: &dVoid & Cosmic",
            "&7Modular upgrade for the Laser Excavator",
            "&7Tunnels through spacetime to extract",
            "&5Void Bits&7, &5Void Dust&7, and &dEnder Pearls"
    );
    public static final double DIAMOND_CHANCE = getOscillatorChance("diamond");
    public static final double REDSTONE_CHANCE = getOscillatorChance("redstone");
    public static final double LAPIS_CHANCE = getOscillatorChance("lapis");
    public static final double EMERALD_CHANCE = getOscillatorChance("emerald");
    public static final double QUARTZ_CHANCE = getOscillatorChance("quartz");
    public static final SlimefunItemStack DIAMOND_OSCILLATOR = Oscillator.create(Material.DIAMOND, DIAMOND_CHANCE);
    public static final SlimefunItemStack REDSTONE_OSCILLATOR = Oscillator.create(Material.REDSTONE, REDSTONE_CHANCE);
    public static final SlimefunItemStack LAPIS_OSCILLATOR = Oscillator.create(Material.LAPIS_LAZULI, LAPIS_CHANCE);
    public static final SlimefunItemStack QUARTZ_OSCILLATOR = Oscillator.create(Material.QUARTZ, QUARTZ_CHANCE);
    public static final SlimefunItemStack EMERALD_OSCILLATOR = Oscillator.create(Material.EMERALD, EMERALD_CHANCE);

    private static double getOscillatorChance(String type) {
        return InfinityExpansion.config().getDouble("quarry-options.oscillators." + type, 0, 1);
    }

    public static void setup(InfinityExpansion plugin) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("quarry-options.resources");
        Objects.requireNonNull(section);
        List<Material> outputs = new ArrayList<>();

        boolean coal = section.getBoolean("coal");

        if (coal) {
            outputs.add(Material.COAL);
            outputs.add(Material.COAL);
        }

        if (section.getBoolean("iron")) {
            outputs.add(Material.IRON_INGOT);
        }

        if (section.getBoolean("gold")) {
            outputs.add(Material.GOLD_INGOT);
        }

        if (Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_17) && section.getBoolean("copper")) {
            outputs.add(Material.COPPER_INGOT);
            outputs.add(Material.COPPER_INGOT);
        }

        if (section.getBoolean("redstone")) {
            new Oscillator(REDSTONE_OSCILLATOR, REDSTONE_CHANCE).register(plugin);
            outputs.add(Material.REDSTONE);
        }

        if (section.getBoolean("lapis")) {
            new Oscillator(LAPIS_OSCILLATOR, LAPIS_CHANCE).register(plugin);
            outputs.add(Material.LAPIS_LAZULI);
        }

        if (section.getBoolean("emerald")) {
            new Oscillator(EMERALD_OSCILLATOR, EMERALD_CHANCE).register(plugin);
            outputs.add(Material.EMERALD);
        }

        if (section.getBoolean("diamond")) {
            new Oscillator(DIAMOND_OSCILLATOR, DIAMOND_CHANCE).register(plugin);
            outputs.add(Material.DIAMOND);
        }

        new Quarry(Groups.ADVANCED_MACHINES, BASIC_QUARRY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MAGSTEEL_PLATE, SlimefunItems.CARBONADO_EDGED_CAPACITOR, Materials.MAGSTEEL_PLATE,
                new ItemStack(Material.IRON_PICKAXE), SlimefunItems.GEO_MINER, new ItemStack(Material.IRON_PICKAXE),
                Materials.MACHINE_CIRCUIT, Materials.MACHINE_CORE, Materials.MACHINE_CIRCUIT
        }, 1, 6, outputs.toArray(new Material[0])).energyPerTick(300).register(plugin);

        if (section.getBoolean("quartz")) {
            new Oscillator(QUARTZ_OSCILLATOR, QUARTZ_CHANCE).register(plugin);

            outputs.add(Material.QUARTZ);
        }

        if (section.getBoolean("netherite")) {
            outputs.add(Material.NETHERITE_INGOT);
        }

        if (section.getBoolean("netherrack")) {
            outputs.add(Material.NETHERRACK);
            outputs.add(Material.NETHERRACK);
        }

        new Quarry(Groups.ADVANCED_MACHINES, ADVANCED_QUARRY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MACHINE_PLATE, SlimefunItems.ENERGIZED_CAPACITOR, Materials.MACHINE_PLATE,
                new ItemStack(Material.DIAMOND_PICKAXE), BASIC_QUARRY, new ItemStack(Material.DIAMOND_PICKAXE),
                Materials.MACHINE_CIRCUIT, Materials.MACHINE_CORE, Materials.MACHINE_CIRCUIT
        }, 2, 4, outputs.toArray(new Material[0])).energyPerTick(900).register(plugin);

        if (coal) {
            outputs.add(Material.COAL);
        }

        new Quarry(Groups.ADVANCED_MACHINES, VOID_QUARRY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.VOID_INGOT, SlimefunExtension.VOID_CAPACITOR, Materials.VOID_INGOT,
                new ItemStack(Material.NETHERITE_PICKAXE), ADVANCED_QUARRY, new ItemStack(Material.NETHERITE_PICKAXE),
                Materials.MACHINE_CIRCUIT, Materials.MACHINE_CORE, Materials.MACHINE_CIRCUIT
        }, 6, 2, outputs.toArray(new Material[0])).energyPerTick(3600).register(plugin);

        if (coal) {
            outputs.add(Material.COAL);
        }

        new Quarry(Groups.INFINITY_CHEAT, INFINITY_QUARRY, InfinityWorkbench.TYPE, new ItemStack[] {
                null, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, Materials.MACHINE_PLATE, null,
                Materials.MACHINE_PLATE, Gear.PICKAXE, Materials.INFINITE_CIRCUIT, Materials.INFINITE_CIRCUIT, Gear.PICKAXE, Materials.MACHINE_PLATE,
                Materials.MACHINE_PLATE, VOID_QUARRY, Materials.INFINITE_CORE, Materials.INFINITE_CORE, VOID_QUARRY, Materials.MACHINE_PLATE,
                Materials.VOID_INGOT, null, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, null, Materials.VOID_INGOT,
                Materials.VOID_INGOT, null, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, null, Materials.VOID_INGOT,
                Materials.VOID_INGOT, null, Materials.INFINITE_INGOT, Materials.INFINITE_INGOT, null, Materials.VOID_INGOT
        }, 64, 1, outputs.toArray(new Material[0])).energyPerTick(36000).register(plugin);

        // Register Modular Focus Lenses for Laser Excavator
        new LaserLens(NETHER_FOCUS_LENS, LaserLens.LensType.NETHER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MAGSTEEL_PLATE, new ItemStack(Material.RED_STAINED_GLASS), Materials.MAGSTEEL_PLATE,
                new ItemStack(Material.NETHERITE_INGOT), new ItemStack(Material.ANCIENT_DEBRIS), new ItemStack(Material.NETHERITE_INGOT),
                Materials.MACHINE_CIRCUIT, new ItemStack(Material.RED_STAINED_GLASS), Materials.MACHINE_CIRCUIT
        }).register(plugin);

        new LaserLens(PRECIOUS_FOCUS_LENS, LaserLens.LensType.PRECIOUS, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MACHINE_PLATE, new ItemStack(Material.LIME_STAINED_GLASS), Materials.MACHINE_PLATE,
                new ItemStack(Material.DIAMOND_BLOCK), new ItemStack(Material.EMERALD_BLOCK), new ItemStack(Material.DIAMOND_BLOCK),
                Materials.MACHINE_CIRCUIT, new ItemStack(Material.LIME_STAINED_GLASS), Materials.MACHINE_CIRCUIT
        }).register(plugin);

        new LaserLens(ENERGY_CONVERTER_LENS, LaserLens.LensType.OVERCLOCK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MACHINE_PLATE, new ItemStack(Material.YELLOW_STAINED_GLASS), Materials.MACHINE_PLATE,
                SlimefunItems.ENERGIZED_CAPACITOR, Materials.MACHINE_CORE, SlimefunItems.ENERGIZED_CAPACITOR,
                Materials.MACHINE_CIRCUIT, new ItemStack(Material.YELLOW_STAINED_GLASS), Materials.MACHINE_CIRCUIT
        }).register(plugin);

        new LaserLens(VOID_FOCUS_LENS, LaserLens.LensType.VOID, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.VOID_INGOT, new ItemStack(Material.PURPLE_STAINED_GLASS), Materials.VOID_INGOT,
                SlimefunExtension.VOID_CAPACITOR, Materials.VOID_BIT, SlimefunExtension.VOID_CAPACITOR,
                Materials.INFINITE_CIRCUIT, new ItemStack(Material.PURPLE_STAINED_GLASS), Materials.INFINITE_CIRCUIT
        }).register(plugin);

        // Register Laser Excavator
        int laserSpeed = plugin.getConfig().getInt("quarry-options.laser-excavator.base-speed", 4);
        int laserEnergy = plugin.getConfig().getInt("quarry-options.laser-excavator.energy-per-tick", 1200);
        int laserCapacity = plugin.getConfig().getInt("quarry-options.laser-excavator.energy-capacity", 10000);

        LaserExcavator laserExcavator = new LaserExcavator(
                Groups.ADVANCED_MACHINES,
                LASER_EXCAVATOR,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        Materials.MACHINE_PLATE, SlimefunItems.ENERGIZED_CAPACITOR, Materials.MACHINE_PLATE,
                        Materials.VOID_INGOT, VOID_QUARRY, Materials.VOID_INGOT,
                        Materials.MACHINE_CIRCUIT, Materials.MACHINE_CORE, Materials.MACHINE_CIRCUIT
                },
                laserSpeed
        );
        laserExcavator.energyPerTick(laserEnergy);
        laserExcavator.energyCapacity(laserCapacity);
        laserExcavator.register(plugin);
    }

}
