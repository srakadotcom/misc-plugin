package ru.rusekh.miscplugin.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.rusekh.miscplugin.util.ItemBuilder;

public class ShopInventory
{
  public static void openInventory(Player player) {
    Inventory inventory = Bukkit.createInventory(null, 27, "Menu sklepu");

    ItemStack sellShop = new ItemBuilder(Material.HARD_CLAY, 1, (short)5)
        .setName("&cSprzedaż przedmiotów")
        .build();

    ItemStack buyShop = new ItemBuilder(Material.HARD_CLAY, 1, (short)14)
        .setName("&aKupno przedmiotów")
        .build();

    inventory.setItem(10, buyShop);
    inventory.setItem(14, sellShop);

    player.openInventory(inventory);
  }
}
