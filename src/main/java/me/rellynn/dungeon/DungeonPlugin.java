package me.rellynn.dungeon;

import me.rellynn.dungeon.command.CallCommand;
import me.rellynn.dungeon.command.CancelCommand;
import me.rellynn.dungeon.command.DeleteCommand;
import me.rellynn.dungeon.command.HelpCommand;
import me.rellynn.dungeon.command.InfoCommand;
import me.rellynn.dungeon.command.NewCommand;
import me.rellynn.dungeon.command.TimeresetCommand;
import me.rellynn.dungeon.listener.PlayerListener;
import me.rellynn.dungeon.manager.CommandsManager;
import me.rellynn.dungeon.manager.DungeonsManager;
import me.rellynn.dungeon.manager.object.Dungeon;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.plugin.java.JavaPlugin;

public class DungeonPlugin extends JavaPlugin {
    public static DungeonPlugin instance;

    @Override
    public void onDisable() {
	for (final Dungeon dungeon : DungeonsManager.findAll()) {
	    final Block block = dungeon.getLocation().getBlock();
	    if (block.getState() instanceof Chest) ((Chest) block.getState()).getInventory().clear();
	    dungeon.getLocation().getBlock().setType(Material.AIR);
	}
	DungeonsManager.saveAll();
    }

    @Override
    public void onEnable() {
	DungeonPlugin.instance = this;
	for (final Dungeon dungeon : DungeonsManager.findAll())
	    new ChestTask(dungeon, 1L);
	this.registerCommands();
	this.getCommand("dc").setExecutor(new CommandsManager());
	this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    private void registerCommands() {
	CommandsManager.add(new CallCommand());
	CommandsManager.add(new CancelCommand());
	CommandsManager.add(new DeleteCommand());
	CommandsManager.add(new HelpCommand());
	CommandsManager.add(new InfoCommand());
	CommandsManager.add(new NewCommand());
	CommandsManager.add(new TimeresetCommand());
    }
}
