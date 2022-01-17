package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Syntax;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import ru.rusekh.miscplugin.inventory.ClickableInventory;
import ru.rusekh.miscplugin.util.ItemBuilder;

@CommandAlias("warp")
public class WarpCommand extends BaseCommand {

  private final List<Warp> warps = new ArrayList<>();

  @CommandPermission("miscplugin.setwarp")
  @CommandAlias("set")
  @Syntax("(material) (nazwa warpu)")
  public void setWarp(Player player, Material material, String name) {
    warps.add(new Warp(name, player.getLocation(), material));
    player.sendMessage(ChatColor.GREEN + "Pomyslnie ustawiono warpidło!");
  }

  @CommandPermission("miscplugin.delwarp")
  @CommandAlias("delete")
  @Syntax("(nazwa warpu)")
  public void deleteWarp(Player player, String name) {
    if (this.warps.removeIf(warp -> warp.name.equalsIgnoreCase(name))) {
      player.sendMessage(ChatColor.GREEN + "Pomyslnie usunieto warpidło!");
    } else {
      player.sendMessage(ChatColor.RED + "Nie znaleziono takiego warpidła.");
    }
  }

  @Default
  public void defaultCommand(Player player, @Optional String name) {
    if (name != null) {
      warps.stream()
          .filter(x -> x.name.equalsIgnoreCase(name))
          .findAny().ifPresentOrElse(warp -> {
            player.teleport(warp.location);
            player.sendMessage(ChatColor.GREEN + "Przeteleportowano!");
          }, () -> {
            player.sendMessage(ChatColor.RED + "Nie znaleziono warpidła!");
          });
      return;
    }

    player.openInventory(new WarpInventory().getInventory());
  }

  public record Warp(String name, Location location, Material icon) {

  }

  private class WarpInventory implements ClickableInventory {

    @Override
    public Inventory getInventory() {
      Inventory inventory = Bukkit.createInventory(this, InventoryType.HOPPER, "Warpy");

      int i = 0;
      for (Warp warp : warps) {
        inventory.setItem(i++, new ItemBuilder(warp.icon, 1)
            .setName("&7" + warp.name)
            .build());
      }
      return inventory;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
      if (event.getRawSlot() >= warps.size() || event.getRawSlot() < 0) {
        return;
      }

      Warp warp = warps.get(event.getRawSlot());
      if (warp == null) {
        return;
      }

      event.getWhoClicked().closeInventory();
      event.getWhoClicked().teleport(warp.location);
      event.getWhoClicked().sendMessage(ChatColor.GREEN + "Przeteleportowano!");
    }

  }

}
