package ru.rusekh.miscplugin.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.object.Shop;

public class ShopManager
{
  private final List<Shop> shopSell = new ArrayList<>();
  private final List<Shop> shopBuy = new ArrayList<>();

  public ShopManager(ToolsPlugin toolsPlugin) {
    for (String string : toolsPlugin.getConfig().getConfigurationSection("shopSell").getKeys(false)) {
      String path = "shopSell." + string;
      String title = toolsPlugin.getConfig().getString(path + ".title");
      double cost = toolsPlugin.getConfig().getDouble(path + ".cost");
      Material material = Material.getMaterial(toolsPlugin.getConfig().getString(path + ".type"));
      List<ItemStack> items = toolsPlugin.getConfig().getStringList(path + ".items").stream() //napraw to memexur
          .map(this::deserializeItemStack)
          .toList();
      List<String> commands = toolsPlugin.getConfig().getStringList(path + ".commands");
      shopSell.add(new Shop(title, cost, material, items, commands));
    }
    for (String string : toolsPlugin.getConfig().getConfigurationSection("shopBuy").getKeys(false)) {
      String path = "shopBuy." + string;
      String title = toolsPlugin.getConfig().getString(path + ".title");
      double cost = toolsPlugin.getConfig().getDouble(path + ".cost");
      Material material = Material.getMaterial(toolsPlugin.getConfig().getString(path + ".type"));
      List<ItemStack> items = toolsPlugin.getConfig().getStringList(path + ".items").stream()
          .map(this::deserializeItemStack)
          .toList();
      List<String> commands = toolsPlugin.getConfig().getStringList(path + ".commands");
      shopBuy.add(new Shop(title, cost, material, items, commands));
    }
  }

  private ItemStack deserializeItemStack(String string) {
    String material = string;
    int amount = 1;
    if(string.contains(" ")) {
      amount = Integer.parseInt(string.split(" ")[1]);
      material = string.split(" ")[0];
    }

    if(material.contains(":")) {
      String[] split = material.split(":");
      return new ItemStack(Material.getMaterial(split[0]), amount, Short.parseShort(split[1]));
    } else {
      return new ItemStack(Material.getMaterial(material), amount);
    }
  }

  public List<Shop> getShopSell() {
    return shopSell;
  }

  public List<Shop> getShopBuy() {
    return shopBuy;
  }
}
