package me.rellynn.dungeon.manager.object;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Dungeon {
    public enum State {
	NEW, DELETE, INFO, NORMAL
    }

    protected int id;
    protected State state;
    protected List<ItemStack> items;
    protected int size;
    protected Location location;
    protected long delay;
    protected BukkitRunnable task;

    public Dungeon(final int id, final State state, final List<ItemStack> items, final int size, final Location location, final long delay) {
	this.id = id;
	this.state = state;
	this.items = items;
	this.size = size;
	this.location = location;
	this.delay = delay;
    }

    public long getDelay() {
	return this.delay;
    }

    public int getId() {
	return this.id;
    }

    public List<ItemStack> getItems() {
	return this.items;
    }

    public Location getLocation() {
	return this.location;
    }

    public int getSize() {
	return this.size;
    }

    public State getState() {
	return this.state;
    }

    public BukkitRunnable getTask() {
	return this.task;
    }

    public void setDelay(final long delay) {
	this.delay = delay;
    }

    public void setId(final int id) {
	this.id = id;
    }

    public void setItems(final List<ItemStack> items) {
	this.items = items;
    }

    public void setLocation(final Location location) {
	this.location = location;
    }

    public void setSize(final int size) {
	this.size = size;
    }

    public void setState(final State state) {
	this.state = state;
    }

    public void setTask(final BukkitRunnable task) {
	this.task = task;
    }
}
