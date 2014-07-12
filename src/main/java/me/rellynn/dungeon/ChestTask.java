package me.rellynn.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.rellynn.dungeon.manager.object.Dungeon;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestTask extends BukkitRunnable {
    private final Dungeon dungeon;

    public ChestTask(final Dungeon dungeon, final long delay) {
	this.dungeon = dungeon;
	this.runTaskLater(DungeonPlugin.instance, delay);
	dungeon.setTask(this);
    }

    @Override
    public void run() {
	final Random random = new Random();
	final List<ItemStack> contents = new ArrayList<>();
	final List<ItemStack> items = new ArrayList<>(this.dungeon.getItems());
	final Block block = this.dungeon.getLocation().getBlock();
	block.setType(Material.CHEST);
	if (items.size() > 0) for (int i = 0; i < this.dungeon.getSize(); i++) {
	    final ItemStack item = items.get(random.nextInt(items.size()));
	    item.setAmount(random.nextInt(item.getAmount()) + 1);
	    items.remove(item);
	    contents.add(item);
	}
	final Chest chest = (Chest) block.getState();
	chest.getInventory().setContents(contents.toArray(new ItemStack[contents.size()]));
	chest.update();
	this.dungeon.setTask(null);
    }
}
