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

    public Dungeon(int id, State state, List<ItemStack> items, int size, Location location, long delay) {
        this.id = id;
        this.state = state;
        this.items = items;
        this.size = size;
        this.location = location;
        this.delay = delay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public BukkitRunnable getTask() {
        return task;
    }

    public void setTask(BukkitRunnable task) {
        this.task = task;
    }
}
