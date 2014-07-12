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
	DungeonsManager.chests = new ArrayList<>();
	DungeonsManager.file = new File(DungeonPlugin.instance.getDataFolder(), "chests.yml");
	DungeonsManager.config = YamlConfiguration.loadConfiguration(DungeonsManager.file);
    }

    public static boolean add(final Dungeon e) {
	return DungeonsManager.chests.add(e);
    }

    public static Dungeon find(final int id) {
	for (final Dungeon e : DungeonsManager.chests)
	    if (e.getId() == id) return e;
	return null;
    }

    public static Dungeon find(final Location location) {
	for (final Dungeon e : DungeonsManager.chests)
	    if (e.getLocation().equals(location)) return e;
	return null;
    }

    public static List<Dungeon> findAll() {
	if (DungeonsManager.chests.isEmpty()) {
	    final Set<String> keys = DungeonsManager.config.getKeys(false);
	    for (final String key : keys) {
		final ConfigurationSection section = DungeonsManager.config.getConfigurationSection(key);
		final List<ItemStack> items = new ArrayList<ItemStack>();
		final String[] locations = section.getString("location").split("_");
		final ConfigurationSection section2 = section.getConfigurationSection("items");
		final Set<String> keys2 = section2.getKeys(false);
		for (final String key2 : keys2)
		    items.add(section2.getItemStack(key2));
		DungeonsManager.chests.add(new Dungeon(section.getInt("id"), State.NORMAL, items, section.getInt("size"), new Location(Bukkit.getWorld(locations[0]), Integer.parseInt(locations[1]), Integer.parseInt(locations[2]), Integer.parseInt(locations[3])), section.getLong("delay")));
		DungeonsManager.config.set(key, null);
	    }
	    try {
		DungeonsManager.config.save(DungeonsManager.file);
	    } catch (final IOException e) {
		e.printStackTrace();
	    }
	}
	return DungeonsManager.chests;
    }

    public static boolean remove(final int id) {
	final Dungeon e = DungeonsManager.find(id);
	if (e != null) return DungeonsManager.chests.remove(e);
	return false;
    }

    public static void saveAll() {
	for (int i = 0; i < DungeonsManager.chests.size(); i++) {
	    final Dungeon dungeon = DungeonsManager.chests.get(i);
	    final Location location = dungeon.getLocation();
	    DungeonsManager.config.set(i + ".id", dungeon.getId());
	    DungeonsManager.config.set(i + ".location", location.getWorld().getName() + "_" + location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ());
	    DungeonsManager.config.set(i + ".size", dungeon.getSize());
	    DungeonsManager.config.set(i + ".delay", dungeon.getDelay());
	    for (int j = 0, c = dungeon.getItems().size(); j < c; j++)
		DungeonsManager.config.set(i + ".items." + j, dungeon.getItems().get(j));
	}
	try {
	    DungeonsManager.config.save(DungeonsManager.file);
	} catch (final IOException e) {
	    e.printStackTrace();
	}
    }
}
