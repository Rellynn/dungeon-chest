package me.rellynn.dungeon.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import me.rellynn.dungeon.ChestTask;
import me.rellynn.dungeon.manager.DungeonsManager;
import me.rellynn.dungeon.manager.PlayersManager;
import me.rellynn.dungeon.manager.object.Dungeon;
import me.rellynn.dungeon.manager.object.Dungeon.State;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Dungeon dungeon = PlayersManager.find(player.getName());
            if (dungeon != null) {
                Block clickedBlock = event.getClickedBlock();
                if (dungeon.getState() == State.INFO) {
                    event.setCancelled(true);
                    if (clickedBlock.getType() != Material.CHEST) {
                        player.sendMessage(ChatColor.RED + "DCH : Vous devez cliquer sur " + ChatColor.BOLD + "un coffre");
                        return;
                    }
                    dungeon = DungeonsManager.find(clickedBlock.getLocation());
                    if (dungeon == null) {
                        player.sendMessage(ChatColor.RED + "DCH : Le coffre cliqué n'est pas un Dungeon Chest");
                        return;
                    }
                    Location location = dungeon.getLocation();
                    player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "Informations :");
                    player.sendMessage(ChatColor.GRAY + "ID : " + dungeon.getId());
                    player.sendMessage(ChatColor.GRAY + "X : " + location.getBlockX() + " | " + "Y : " + location.getBlockY() + " | " + "Z : " + location.getBlockZ());
                    player.sendMessage(ChatColor.GRAY + "Délai (en secondes) : " + dungeon.getDelay() / 20);
                    player.sendMessage(ChatColor.GRAY + "Affiché : " + (dungeon.getTask() == null ? "oui" : "non"));
                    player.sendMessage(ChatColor.GRAY + "Maximum d'items : " + dungeon.getSize());
                } else if (dungeon.getState() == State.NEW) {
                    event.setCancelled(true);
                    if (clickedBlock.getType() != Material.CHEST) {
                        player.sendMessage(ChatColor.RED + "DCH : Vous devez cliquer sur " + ChatColor.BOLD + "un coffre");
                        return;
                    }
                    Chest chest = (Chest) clickedBlock.getState();
                    List<ItemStack> originals = Arrays.asList(chest.getInventory().getContents());
                    List<ItemStack> copies = new ArrayList<ItemStack>();
                    Iterator<ItemStack> iterator = originals.iterator();
                    while (iterator.hasNext()) {
                        ItemStack original = iterator.next();
                        if (original != null && original.getType() != Material.AIR) {
                            ItemStack copy = new ItemStack(original);
                            copies.add(copy);
                        }
                    }
                    dungeon.setLocation(clickedBlock.getLocation());
                    dungeon.setItems(copies);
                    DungeonsManager.add(dungeon);
                    player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "Le coffre a été transformé");
                    player.sendMessage(ChatColor.GRAY + "id : " + dungeon.getId() + " | delai : " + dungeon.getDelay() / 20 + " seconde" + (dungeon.getDelay() / 20 > 1 ? "s" : ""));
                    chest.getInventory().clear();
                    clickedBlock.setType(Material.AIR);
                    new ChestTask(dungeon, dungeon.getDelay());
                }
                PlayersManager.remove(player.getName());
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.CHEST) {
            Dungeon dungeon = DungeonsManager.find(block.getLocation());
            if (dungeon != null) {
                Player player = event.getPlayer();
                Dungeon playerDungeon = PlayersManager.find(player.getName());
                if (playerDungeon != null && playerDungeon.getState() == State.DELETE) {
                    BukkitRunnable task = dungeon.getTask();
                    if (task != null) {
                        task.cancel();
                    }
                    ((Chest) block.getState()).getInventory().clear();
                    player.sendMessage(ChatColor.GOLD + "DCH : " + ChatColor.WHITE + "Coffre d'id " + dungeon.getId() + " supprimé");
                    DungeonsManager.remove(dungeon.getId());
                    PlayersManager.remove(player.getName());
                } else {
                    if (player.hasPermission("dch.break")) {
                        player.sendMessage(ChatColor.RED + "DCH : Utilisez la commande /dc delete pour supprimer le coffre");
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getType() == InventoryType.CHEST) {
            InventoryHolder holder = inventory.getHolder();
            if (holder instanceof Chest) {
                Chest chest = (Chest) holder;
                Dungeon dungeon = DungeonsManager.find(chest.getLocation());
                if (dungeon != null) {
                    BukkitRunnable task = dungeon.getTask();
                    if (task != null) {
                        task.cancel();
                    }
                    chest.getBlock().setType(Material.AIR);
                    new ChestTask(dungeon, dungeon.getDelay());
                }
            }
        }
    }
}
