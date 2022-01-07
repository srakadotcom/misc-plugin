package ru.rusekh.miscplugin.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.inventory.ClickableInventory;

public class InventoryClickListener implements Listener
{

  @EventHandler
  private void onGuiClick(InventoryClickEvent event) {
    if (event.getClickedInventory() != null && event.getClickedInventory().getHolder() instanceof ClickableInventory)  {
      event.setCancelled(true);
      ((ClickableInventory) event.getClickedInventory().getHolder()).handleClick(event);
    }
  }
}
