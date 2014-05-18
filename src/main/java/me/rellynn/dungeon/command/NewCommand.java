package me.rellynn.dungeon.command;

import me.rellynn.dungeon.manager.DungeonsManager;
import me.rellynn.dungeon.manager.PlayersManager;
import me.rellynn.dungeon.manager.object.Command;
import me.rellynn.dungeon.manager.object.Dungeon;
import me.rellynn.dungeon.manager.object.Dungeon.State;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class NewCommand extends Command {

    public NewCommand() {
        super("newdc", "dch.new", "/dc newdc <id> <nb-item-max> <delai>", "permet de définir par un clic droit un coffre en tant que dungeon chest, son nombre de type d'item max, et son délai entre deux apparitions");
    }

    @Override
    public boolean execute(Player player, String[] args) {
        if (args.length != 3) {
            return false;
        } else if (!args[0].matches("[0-9]+") || !args[1].matches("[0-9]+") || !args[2].matches("[0-9]+")) {
            player.sendMessage(ChatColor.RED + "DCH : L'id, le nombre d'item max et le délai doit être des entiers");
            return false;
        }
        int id = Integer.parseInt(args[0]);
        if (DungeonsManager.find(id) != null) {
            player.sendMessage(ChatColor.RED + "DCH : Un coffre avec cet id existe déjà");
        } else {
            int size = Integer.parseInt(args[1]);
            int delay = Integer.parseInt(args[2]);
            PlayersManager.add(player.getName(), new Dungeon(id, State.NEW, null, size, null, delay * 20));
            player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "Cliquez droit sur le coffre que vous voulez transformer");
            player.sendMessage(ChatColor.GRAY + "/dc cancel pour annuler");
        }
        return true;
    }
}
