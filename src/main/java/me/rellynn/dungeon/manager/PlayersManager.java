package me.rellynn.dungeon.manager;

import java.util.HashMap;
import java.util.Map;

import me.rellynn.dungeon.manager.object.Dungeon;

public class PlayersManager {
    protected static Map<String, Dungeon> players;

    static {
        players = new HashMap<String, Dungeon>();
    }

    public static Map<String, Dungeon> findAll() {
        return players;
    }

    public static Dungeon find(String key) {
        return players.get(key);
    }

    public static Dungeon remove(String key) {
        return players.remove(key);
    }

    public static Dungeon add(String key, Dungeon value) {
        return players.put(key, value);
    }
}
