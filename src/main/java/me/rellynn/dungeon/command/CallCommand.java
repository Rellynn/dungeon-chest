package me.rellynn.dungeon.command;

import me.rellynn.dungeon.manager.DungeonsManager;
import me.rellynn.dungeon.manager.object.Command;
import me.rellynn.dungeon.manager.object.Dungeon;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CallCommand extends Command {

    public CallCommand() {
        super("call", "dch.call", "/dc call [id]", "faire apparaitre tout les coffres ou un seul coffre par son id (expirer le délai)");
    }

    @Override
    public boolean execute(Player player, String[] args) {
        if (args.length == 0) {
            int size = 0;
            for (Dungeon dungeon : DungeonsManager.findAll()) {
                BukkitRunnable task = dungeon.getTask();
                if (task != null) {
                    task.cancel();
                    task.run();
                }
                size++;
            }
            player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + size + " " + (size != 1 ? "coffres ont été affichés" : "coffre a été affiché"));
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
                    task.run();
                }
                player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "1 coffre a été affiché");
            }
        }
        return true;
    }
}
