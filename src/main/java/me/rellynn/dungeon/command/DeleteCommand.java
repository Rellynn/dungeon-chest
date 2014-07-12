package me.rellynn.dungeon.command;

import me.rellynn.dungeon.manager.DungeonsManager;
import me.rellynn.dungeon.manager.PlayersManager;
import me.rellynn.dungeon.manager.object.Command;
import me.rellynn.dungeon.manager.object.Dungeon;
import me.rellynn.dungeon.manager.object.Dungeon.State;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeleteCommand extends Command {

    public DeleteCommand() {
	super("delete", "dch.delete", "/dc delete [id]", "permet de supprimer le coffre ciblé par un clic gauche ou son id");
    }

    @Override
    public boolean execute(final Player player, final String[] args) {
	if (args.length == 0) {
	    PlayersManager.add(player.getName(), new Dungeon(-1, State.DELETE, null, -1, null, -1));
	    player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "Cliquez gauche sur le coffre que vous voulez supprimer");
	    player.sendMessage(ChatColor.GRAY + "/dc cancel pour annuler");
	} else {
	    final int id = Integer.parseInt(args[0]);
	    final Dungeon dungeon = DungeonsManager.find(id);
	    if (dungeon == null) player.sendMessage(ChatColor.RED + "DCH : Le coffre d'id " + id + " n'existe pas");
	    else {
		final Block block = dungeon.getLocation().getBlock();
		final BukkitRunnable task = dungeon.getTask();
		if (task != null) task.cancel();
		else {
		    if (block.getState() instanceof Chest) ((Chest) block.getState()).getInventory().clear();
		    block.setType(Material.AIR);
		}
		player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "Le coffre d'id " + id + " a été supprimé");
		DungeonsManager.remove(id);
	    }
	}
	return true;
    }
}
