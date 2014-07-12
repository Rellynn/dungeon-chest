package me.rellynn.dungeon.manager;

import java.util.HashMap;
import java.util.Map;

import me.rellynn.dungeon.manager.object.Dungeon;

public class PlayersManager {
    protected static Map<String, Dungeon> players = new HashMap<>();

    public static Dungeon add(final String key, final Dungeon value) {
	return PlayersManager.players.put(key, value);
    }

    public static Dungeon find(final String key) {
	return PlayersManager.players.get(key);
    }

    public static Map<String, Dungeon> findAll() {
	return PlayersManager.players;
    }

    public static Dungeon remove(final String key) {
	return PlayersManager.players.remove(key);
    }
}
