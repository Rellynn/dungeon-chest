package me.rellynn.dungeon.command;

import me.rellynn.dungeon.ChestTask;
import me.rellynn.dungeon.manager.DungeonsManager;
import me.rellynn.dungeon.manager.object.Command;
import me.rellynn.dungeon.manager.object.Dungeon;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeresetCommand extends Command {

    public TimeresetCommand() {
        super("timereset", "dch.timereset", "/dc timereset [id]", "permet de relancer tout les délais (comme si tout les dungeon chest avaient été ouverts en même temps)");
    }

    @Override
    public boolean execute(Player player, String[] args) {
        if (args.length == 0) {
            int size = 0;
            for (Dungeon dungeon : DungeonsManager.findAll()) {
                BukkitRunnable task = dungeon.getTask();
                if (task != null) {
                    task.cancel();
                } else {
                    Block block = dungeon.getLocation().getBlock();
                    if (block.getState() instanceof Chest) {
                        ((Chest) block.getState()).getInventory().clear();
                    }
                    block.setType(Material.AIR);
                }
                new ChestTask(dungeon, dungeon.getDelay());
                size++;
            }
            player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + size + " " + (size != 1 ? "coffres ont expirés" : "coffre a expiré"));
        } else {
            if (!args[0].matches("[0-9]+")) {
                player.sendMessage(ChatColor.RED + "L'id doit être un entier");
                return false;
            }
            int id = Integer.parseInt(args[0]);
            Dungeon dungeon = DungeonsManager.find(id);
            if (dungeon == null) {
                player.sendMessage(ChatColor.RED + "DCH : Le coffre d'id " + id + " n'existe pas");
            } else {
                BukkitRunnable task = dungeon.getTask();
                if (task != null) {
                    task.cancel();
                } else {
                    Block block = dungeon.getLocation().getBlock();
                    if (block.getState() instanceof Chest) {
                        ((Chest) block.getState()).getInventory().clear();
                    }
                    block.setType(Material.AIR);
                }
                new ChestTask(dungeon, dungeon.getDelay());
                player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "1 coffre a expiré");
            }
        }
        return true;
    }
}
