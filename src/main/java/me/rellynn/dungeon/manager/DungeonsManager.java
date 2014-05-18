package me.rellynn.dungeon.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.rellynn.dungeon.DungeonPlugin;
import me.rellynn.dungeon.manager.object.Dungeon;
import me.rellynn.dungeon.manager.object.Dungeon.State;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class DungeonsManager {
    protected static List<Dungeon> chests;
    protected static FileConfiguration config;
    protected static File file;

    static {
        chests = new ArrayList<Dungeon>();
        file = new File(DungeonPlugin.instance.getDataFolder(), "chests.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static List<Dungeon> findAll() {
        if (chests.isEmpty()) {
            Set<String> keys = config.getKeys(false);
            for (String key : keys) {
                ConfigurationSection section = config.getConfigurationSection(key);
                List<ItemStack> items = new ArrayList<ItemStack>();
                String[] locations = section.getString("location").split("_");
                ConfigurationSection section2 = section.getConfigurationSection("items");
                Set<String> keys2 = section2.getKeys(false);
                for (String key2 : keys2) {
                    items.add(section2.getItemStack(key2));
                }
                chests.add(new Dungeon(section.getInt("id"), State.NORMAL, items, section.getInt("size"), new Location(Bukkit.getWorld(locations[0]), Integer.parseInt(locations[1]), Integer.parseInt(locations[2]), Integer.parseInt(locations[3])), section.getLong("delay")));
                config.set(key, null);
            }
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return chests;
    }

    public static Dungeon find(int id) {
        for (Dungeon e : chests) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public static Dungeon find(Location location) {
        for (Dungeon e : chests) {
            if (e.getLocation().equals(location)) {
                return e;
            }
        }
        return null;
    }

    public static boolean remove(int id) {
        Dungeon e = find(id);
        if (e != null) {
            return chests.remove(e);
        }
        return false;
    }

    public static void saveAll() {
        for (int i = 0; i < chests.size(); i++) {
            Dungeon dungeon = chests.get(i);
            Location location = dungeon.getLocation();
            config.set(i + ".id", dungeon.getId());
            config.set(i + ".location", location.getWorld().getName() + "_" + location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ());
            config.set(i + ".size", dungeon.getSize());
            config.set(i + ".delay", dungeon.getDelay());
            for (int j = 0, c = dungeon.getItems().size(); j < c; j++) {
                config.set(i + ".items." + j, dungeon.getItems().get(j));
            }
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean add(Dungeon e) {
        return chests.add(e);
    }
}
