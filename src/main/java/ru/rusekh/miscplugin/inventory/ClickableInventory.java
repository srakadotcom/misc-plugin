package ru.rusekh.miscplugin.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface ClickableInventory extends InventoryHolder {

  @Override
  default Inventory getInventory() {
    return null;
  }

  void handleClick(InventoryClickEvent event);
}
