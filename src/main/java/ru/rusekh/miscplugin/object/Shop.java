package ru.rusekh.miscplugin.object;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shop {

  private final String name;
  private final double cost;
  private final Material icon;
  private final List<ItemStack> items;
  private List<String> commands;

  public Shop(String name, double cost, Material icon,
      List<ItemStack> items, List<String> commands) {
    this.name = name;
    this.cost = cost;
    this.icon = icon;
    this.items = items;
    this.commands = commands;
  }

  public String getName() {
    return name;
  }

  public double getCost() {
    return cost;
  }

  public Material getIcon() {
    return icon;
  }

  public void setCommands(List<String> commands) {
    this.commands = commands;
  }

  public void execute(Player player) {
    if (items != null) {
      for (ItemStack itemStack : items) {
        player.getInventory().addItem(new ItemStack(itemStack));
      }
    }

    if (commands != null) {
      commands.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
          cmd.replace("{PLAYER}", player.getName())));
    }
  }

  public boolean sell(Player player) {
    for (ItemStack itemStack : items) {
      if (!player.getInventory().containsAtLeast(itemStack, itemStack.getAmount())) {
        return false;
      }
    }

    for (ItemStack itemStack : items) {
      player.getInventory().removeItem(itemStack);
    }

    if (commands != null) {
      commands.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
          cmd.replace("{PLAYER}", player.getName())));
    }
    return true;
  }
}
