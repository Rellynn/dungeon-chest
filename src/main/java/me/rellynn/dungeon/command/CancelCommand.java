package me.rellynn.dungeon.command;

import me.rellynn.dungeon.manager.PlayersManager;
import me.rellynn.dungeon.manager.object.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CancelCommand extends Command {

    public CancelCommand() {
	super("cancel", "dch.cancel", "/dc cancel", "annuler la s�l�ction du coffre");
    }

    @Override
    public boolean execute(final Player player, final String[] args) {
	if (PlayersManager.remove(player.getName()) == null) player.sendMessage(ChatColor.RED + "DCH : Aucune s�l�ction en cours");
	else player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "La s�l�ction du coffre a �t� annul�");
	return true;
    }
}
