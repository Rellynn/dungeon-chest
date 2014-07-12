package me.rellynn.dungeon.command;

import me.rellynn.dungeon.manager.PlayersManager;
import me.rellynn.dungeon.manager.object.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CancelCommand extends Command {

    public CancelCommand() {
	super("cancel", "dch.cancel", "/dc cancel", "annuler la séléction du coffre");
    }

    @Override
    public boolean execute(final Player player, final String[] args) {
	if (PlayersManager.remove(player.getName()) == null) player.sendMessage(ChatColor.RED + "DCH : Aucune séléction en cours");
	else player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "La séléction du coffre a été annulé");
	return true;
    }
}
