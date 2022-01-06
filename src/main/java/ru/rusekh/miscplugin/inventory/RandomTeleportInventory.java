package ru.rusekh.miscplugin.inventory;

import java.util.stream.IntStream;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.rusekh.miscplugin.util.ItemBuilder;

public class RandomTeleportInventory
{
  public static void openInventory(Player player) {
    Inventory inventory = Bukkit.createInventory(null, 27, "Menu randomowej teleportacji");

    ItemStack gracz = new ItemBuilder(Material.COMPASS, 1)
        .setName("&fZwykła teleportacja")
        .setLore(" ", "&8• &7Zakres teleportacji od X: &f1000 kratek &7do Z: &f2000 kratek", " ", "&aKliknij, aby się teleportowć")
        .build();
    ItemStack vip = new ItemBuilder(Material.STONE_PICKAXE, 1)
        .setName("&fTeleportacja &6VIP")
        .setLore(" ", "&8• &7Zakres teleportacji od X: &f2000 kratek &7do Z: &f4000 kratek", " ", "&aKliknij, aby się teleportowć")
        .build();
    ItemStack svip = new ItemBuilder(Material.IRON_PICKAXE, 1)
        .setName("&fTeleportacja &2SVIP")
        .setLore(" ", "&8• &7Zakres teleportacji od X: &f4000 kratek &7do Z: &f6000 kratek", " ", "&aKliknij, aby się teleportowć")
        .build();
    ItemStack sponsor = new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
        .setName("&fTeleportacja &9SPONSOR")
        .setLore(" ", "&8• &7Zakres teleportacji od X: &f6000 kratek &7do Z: &f8000 kratek", " ", "&aKliknij, aby się teleportowć")
        .build();
    ItemStack elita = new ItemBuilder(Material.DIAMOND, 1)
        .setName("&fTeleportacja &dELITA")
        .setLore(" ", "&8• &7Zakres teleportacji od X: &f8000 kratek &7do Z: &f11000 kratek", " ", "&aKliknij, aby się teleportowć")
        .build();
    ItemStack miner = new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
        .setName("&fTeleportacja &eMINER")
        .setLore(" ", "&8• &7Zakres teleportacji od X: &f11000 kratek &7do Z: &f15000 kratek", " ", "&aKliknij, aby się teleportowć")
        .build();
    ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)15).build();
    IntStream.range(0, 27).forEach(i -> inventory.setItem(i, glass));


    inventory.setItem(10, gracz);
    inventory.setItem(11, vip);
    inventory.setItem(12, svip);
    inventory.setItem(13, sponsor);
    inventory.setItem(14, elita);
    inventory.setItem(15, miner);
    player.openInventory(inventory);
  }
}
