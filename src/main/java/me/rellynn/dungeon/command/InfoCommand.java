package me.rellynn.dungeon.command;

import me.rellynn.dungeon.manager.DungeonsManager;
import me.rellynn.dungeon.manager.PlayersManager;
import me.rellynn.dungeon.manager.object.Command;
import me.rellynn.dungeon.manager.object.Dungeon;
import me.rellynn.dungeon.manager.object.Dungeon.State;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class InfoCommand extends Command {

    public InfoCommand() {
	super("info", "dch.info", "/dc info [id]", "avoir des informations sur le dungeon chest demandé par clic droit ou par id");
    }

    @Override
    public boolean execute(final Player player, final String[] args) {
	if (args.length == 0) {
	    PlayersManager.add(player.getName(), new Dungeon(-1, State.INFO, null, -1, null, -1));
	    player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "Cliquez droit sur le coffre dont vous voulez des informations");
	    player.sendMessage(ChatColor.GRAY + "/dc cancel pour annuler");
	} else {
	    final int id = Integer.parseInt(args[0]);
	    final Dungeon dungeon = DungeonsManager.find(id);
	    if (dungeon == null) player.sendMessage(ChatColor.RED + "DCH : Le coffre d'id " + id + " n'existe pas");
	    else {
		final Location location = dungeon.getLocation();
		player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "Informations :");
		player.sendMessage(ChatColor.GRAY + "ID : " + id);
		player.sendMessage(ChatColor.GRAY + "X : " + location.getBlockX() + " | " + "Y : " + location.getBlockY() + " | " + "Z : " + location.getBlockZ());
		player.sendMessage(ChatColor.GRAY + "Délai (en secondes) : " + dungeon.getDelay() / 20);
		player.sendMessage(ChatColor.GRAY + "Affiché : " + (dungeon.getTask() == null ? "oui" : "non"));
		player.sendMessage(ChatColor.GRAY + "Maximum d'items : " + dungeon.getSize());
	    }
	}
	return true;
    }
}
