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
    protected static Set<Command> commands;

    static {
        commands = new HashSet<Command>();
    }

    public static Set<Command> findAll() {
        return commands;
    }

    public static boolean add(Command e) {
        return commands.add(e);
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "Plugin par Rellynn | v1.0");
            sender.sendMessage(ChatColor.GRAY + "Pour afficher l'aide tapez /dc help");
            return true;
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "DHC : Vous devez être " + ChatColor.BOLD + "un joueur");
            return true;
        }
        Player player = (Player) sender;
        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(args[0])) {
                if (!player.hasPermission(command.getPermission())) {
                    sender.sendMessage(ChatColor.RED + "DCH : Vous n'avez pas la permission");
                } else {
                    String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                    if (!command.execute(player, newArgs)) {
                        sender.sendMessage(command.getUsage());
                    }
                }
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "DCH : La commande n'existe pas");
        sender.sendMessage(ChatColor.RED + "Tapez /dc help pour de l'aide");
        return true;
    }
}
