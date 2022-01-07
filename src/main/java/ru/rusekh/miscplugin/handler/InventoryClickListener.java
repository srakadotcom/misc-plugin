package ru.rusekh.miscplugin.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.rusekh.miscplugin.ToolsPlugin;

public class InventoryClickListener implements Listener
{
  private final ToolsPlugin toolsPlugin;

  public InventoryClickListener(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  @EventHandler
  private void onGuiClick(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();
    if (event.getClickedInventory() == null) return;
    if (event.getClickedInventory().getTitle().equalsIgnoreCase("Menu sklepu")) {
      event.setCancelled(true);
      switch (event.getSlot()) {
        case 10 -> toolsPlugin.getBuyShopInventory().openInventory(player);
        case 14 -> toolsPlugin.getSellShopInventory().openInventory(player);
        default -> player.closeInventory();
      }
      return;
    }
    if (event.getClickedInventory().getTitle().equalsIgnoreCase("Kupno")) {
      event.setCancelled(true);
      toolsPlugin.getShopManager().getShopBuy().forEach(shop -> {
        
      });
    }
  }
}
