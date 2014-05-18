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
    private Dungeon dungeon;

    public ChestTask(Dungeon dungeon, long delay) {
        this.dungeon = dungeon;
        runTaskLater(DungeonPlugin.instance, delay);
        dungeon.setTask(this);
    }

    public void run() {
        Random random = new Random();
        List<ItemStack> contents = new ArrayList<ItemStack>();
        List<ItemStack> items = new ArrayList<ItemStack>(dungeon.getItems());
        Block block = dungeon.getLocation().getBlock();
        block.setType(Material.CHEST);
        for (int i = 0; i < dungeon.getSize(); i++) {
            if (items.size() == 0) {
                break;
            }
            ItemStack item = items.get(random.nextInt(items.size()));
            item.setAmount(random.nextInt(item.getAmount()) + 1);
            items.remove(item);
            contents.add(item);
        }
        Chest chest = (Chest) block.getState();
        chest.getInventory().setContents(contents.toArray(new ItemStack[contents.size()]));
        chest.update();
        dungeon.setTask(null);
    }
}
