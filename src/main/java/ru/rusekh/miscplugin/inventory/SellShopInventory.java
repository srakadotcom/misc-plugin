package ru.rusekh.miscplugin.inventory;

import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.object.Shop;
import ru.rusekh.miscplugin.util.ItemBuilder;

public class SellShopInventory implements ClickableInventory {

  private final ToolsPlugin toolsPlugin;

  public SellShopInventory(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  public void openInventory(Player player) {
    Inventory inventory = Bukkit.createInventory(this, 54, "Sprzedaż");

    for (Shop shop : toolsPlugin.getShopManager().getShopSell()) {
      ItemStack shopSell =
          new ItemBuilder(shop.getIcon(), 1)
              .setName(shop.getName())
              .setLore("&8>> &7Sprzedaj za &6&n" + shop.getCost())
              .build();
      inventory.addItem(shopSell);
    }

    player.openInventory(inventory);
  }

  @Override
  public void handleClick(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();
    if (event.getRawSlot() >= toolsPlugin.getShopManager().getShopBuy().size()) {
      return;
    }

    Shop shop = toolsPlugin.getShopManager().getShopBuy().get(event.getRawSlot());

    if(!shop.sell(player)) {
      player.sendMessage(ChatColor.RED + "Nie posiadasz wystarczających itemów do sprzedania!");
      return;
    }

    EconomyResponse response = toolsPlugin.getEconomy().depositPlayer(player, shop.getCost());
    if (response.type == ResponseType.FAILURE) {
      player.sendMessage(ChatColor.RED + "Transakcja nieudana: " + response.errorMessage);
    } else {
      player.sendMessage(ChatColor.GREEN + "Pomyślnie sprzedałeś przedmiot!");
    }
  }
}
