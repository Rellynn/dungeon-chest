package me.rellynn.dungeon.manager.object;

import org.bukkit.entity.Player;

public abstract class Command {
    protected String name;
    protected String permission;
    protected String usage;
    protected String description;

    public Command(final String name, final String permission, final String usage, final String description) {
	this.name = name;
	this.permission = permission;
	this.usage = usage;
	this.description = description;
    }

    public abstract boolean execute(Player player, String[] args);

    public String getDescription() {
	return this.description;
    }

    public String getName() {
	return this.name;
    }

    public String getPermission() {
	return this.permission;
    }

    public String getUsage() {
	return this.usage;
    }

    public void setDescription(final String description) {
	this.description = description;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public void setPermission(final String permission) {
	this.permission = permission;
    }

    public void setUsage(final String usage) {
	this.usage = usage;
    }
}
