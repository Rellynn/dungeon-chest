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
    public boolean execute(final Player player, final String[] args) {
	if (args.length == 0) {
	    int size = 0;
	    for (final Dungeon dungeon : DungeonsManager.findAll()) {
		final BukkitRunnable task = dungeon.getTask();
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
	    final int id = Integer.parseInt(args[0]);
	    final Dungeon dungeon = DungeonsManager.find(id);
	    if (dungeon == null) player.sendMessage(ChatColor.RED + "DCH : Le coffre d'id " + id + " n'existe pas");
	    else {
		final BukkitRunnable task = dungeon.getTask();
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
