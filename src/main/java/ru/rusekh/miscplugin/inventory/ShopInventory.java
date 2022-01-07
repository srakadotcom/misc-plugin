package ru.rusekh.miscplugin.inventory;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.util.ItemBuilder;

public class ShopInventory implements ClickableInventory {
  private final ToolsPlugin plugin;

  public ShopInventory(ToolsPlugin plugin) {
    this.plugin = plugin;
  }

  public void openInventory(Player player) {
    Inventory inventory = Bukkit.createInventory(this, 27, "Menu sklepu");

    ItemStack sellShop = new ItemBuilder(Material.HARD_CLAY, 1, DyeColor.LIME.getWoolData())
        .setName("&cSprzedaż przedmiotów")
        .build();

    ItemStack buyShop = new ItemBuilder(Material.HARD_CLAY, 1, DyeColor.RED.getWoolData())
        .setName("&aKupno przedmiotów")
        .build();

    inventory.setItem(10, buyShop);
    inventory.setItem(14, sellShop);

    player.openInventory(inventory);
  }


  @Override
  public void handleClick(InventoryClickEvent event) {
    Player player = (Player)event.getWhoClicked();
    event.setCancelled(true);
    switch (event.getSlot()) {
      case 10 -> new BuyShopInventory(plugin).openInventory(player);
      case 14 -> new SellShopInventory(plugin).openInventory(player);
      default -> event.getWhoClicked().closeInventory();
    }
  }
}
