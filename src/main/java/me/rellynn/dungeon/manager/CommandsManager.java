package me.rellynn.dungeon.manager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import me.rellynn.dungeon.manager.object.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandsManager implements CommandExecutor {
    protected static Set<Command> commands = new HashSet<>();

    public static boolean add(final Command e) {
	return CommandsManager.commands.add(e);
    }

    public static Set<Command> findAll() {
	return CommandsManager.commands;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command cmd, final String label, final String[] args) {
	if (args.length == 0) {
	    sender.sendMessage(ChatColor.YELLOW + "Plugin par Rellynn | v1.0");
	    sender.sendMessage(ChatColor.GRAY + "Pour afficher l'aide tapez /dc help");
	    return true;
	} else if (!(sender instanceof Player)) {
	    sender.sendMessage(ChatColor.RED + "DCH : Vous devez être " + ChatColor.BOLD + "un joueur");
	    return true;
	}
	final Player player = (Player) sender;
	for (final Command command : CommandsManager.commands)
	    if (command.getName().equalsIgnoreCase(args[0])) {
		if (!player.hasPermission(command.getPermission())) sender.sendMessage(ChatColor.RED + "DCH : Vous n'avez pas la permission");
		else {
		    final String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
		    if (!command.execute(player, newArgs)) sender.sendMessage(command.getUsage());
		}
		return true;
	    }
	sender.sendMessage(ChatColor.RED + "DCH : La commande n'existe pas");
	sender.sendMessage(ChatColor.RED + "Tapez /dc help pour de l'aide");
	return true;
    }
}
