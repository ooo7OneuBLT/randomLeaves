package me.ooo7Oneu.ramdomLeaves;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class EventListener implements Listener {

    private final JavaPlugin plugin;
    private final Random random = new Random();
    private final List<Material> validItems = new ArrayList<>();
    private final Map<Material, Material> leafToSapling = new HashMap<>();

    public EventListener(JavaPlugin plugin) {
        this.plugin = plugin;
        setupValidItemsFromConfig();
        setupLeafToSaplingMap();
    }


private void setupValidItemsFromConfig() {

    FileConfiguration config = plugin.getConfig();
    List<String> excludedNames = config.getStringList("excluded-materials");
    Set<Material> excludedMaterials = new HashSet<>();

    for (String name : excludedNames) {
        try {
            excludedMaterials.add(Material.valueOf(name.toUpperCase()));
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("除外対象に無効なMaterial名があります" + name);
        }
    }

    for (Material material : Material.values()) {
        if (material.isItem() && !material.isLegacy() && !excludedMaterials.contains(material)) {
            validItems.add(material);
        }
    }
    plugin.getLogger().info("有効アイテム数:" + validItems.size());
}

    private void setupLeafToSaplingMap() {
        leafToSapling.put(Material.OAK_LEAVES, Material.OAK_SAPLING);
        leafToSapling.put(Material.BIRCH_LEAVES, Material.BIRCH_SAPLING);
        leafToSapling.put(Material.SPRUCE_LEAVES, Material.SPRUCE_SAPLING);
        leafToSapling.put(Material.JUNGLE_LEAVES, Material.JUNGLE_SAPLING);
        leafToSapling.put(Material.ACACIA_LEAVES, Material.ACACIA_SAPLING);
        leafToSapling.put(Material.DARK_OAK_LEAVES, Material.DARK_OAK_SAPLING);
        leafToSapling.put(Material.CHERRY_LEAVES, Material.CHERRY_SAPLING);
        leafToSapling.put(Material.MANGROVE_LEAVES, Material.MANGROVE_PROPAGULE);
        leafToSapling.put(Material.AZALEA_LEAVES, Material.AZALEA);
        leafToSapling.put(Material.FLOWERING_AZALEA_LEAVES, Material.FLOWERING_AZALEA);
    }

    @EventHandler
    public void getLeavesDecay(LeavesDecayEvent event) {
        Block block = event.getBlock();
        Material leafType = block.getType();
    /*    String string = block.toString();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(string);
        } */

        event.setCancelled(true);
        block.setType(Material.AIR);

        if (!validItems.isEmpty()) {
            Material randomItem = validItems.get(random.nextInt(validItems.size()));
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(randomItem));
        }

        if (random.nextDouble() < 0.05) {
            Material sapling = leafToSapling.get(leafType);
            if (sapling != null) {
                block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(sapling));
            }
        }

    }
}
