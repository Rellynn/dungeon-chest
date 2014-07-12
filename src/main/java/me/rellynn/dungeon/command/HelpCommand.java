package me.rellynn.dungeon.command;

import me.rellynn.dungeon.manager.CommandsManager;
import me.rellynn.dungeon.manager.object.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand extends Command {

    public HelpCommand() {
	super("help", "dch.help", "/dc help", "afficher les commandes du plugin");
    }

    @Override
    public boolean execute(final Player player, final String[] args) {
	player.sendMessage(ChatColor.GOLD + "DCH : Commandes disponibles :");
	for (final Command command : CommandsManager.findAll())
	    player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + command.getUsage() + ChatColor.YELLOW + " - " + command.getDescription());
	return true;
    }
}
