package ru.rusekh.miscplugin.object;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shop
{
  private String name;
  private double cost;
  private Material icon;
  private List<ItemStack> items;
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

  public List<ItemStack> getItems() {
    return items;
  }

  public Material getIcon() {
    return icon;
  }

  public List<String> getCommands() {
    return commands;
  }

  public void setCommands(List<String> commands) {
    this.commands = commands;
  }

  public void executeCommand(Player player) {
    commands.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("{PLAYER}", player.getName())));
  }

  public void setIcon(Material icon) {
    this.icon = icon;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public void setItems(List<ItemStack> items) {
    this.items = items;
  }

  public void setName(String name) {
    this.name = name;
  }
}
