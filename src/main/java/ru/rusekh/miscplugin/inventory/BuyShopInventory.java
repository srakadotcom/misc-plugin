package ru.rusekh.miscplugin.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.object.Shop;
import ru.rusekh.miscplugin.util.ItemBuilder;

public class BuyShopInventory
{
  private final ToolsPlugin toolsPlugin;

  public BuyShopInventory(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  public void openInventory(Player player) {
    Inventory inventory = Bukkit.createInventory(null, 54, "Kupno");

    for (Shop shop : toolsPlugin.getShopManager().getShopBuy()) {
      ItemStack shopBuy =
          new ItemBuilder(shop.getIcon(), 1)
              .setName(shop.getName())
              .setLore("&8>> &7Kup za &6&n" + shop.getCost())
              .build();
      inventory.addItem(shopBuy);
    }

    player.openInventory(inventory);
  }
}
