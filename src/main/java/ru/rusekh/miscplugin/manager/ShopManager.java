package ru.rusekh.miscplugin.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
      List<ItemStack> items = Collections.singletonList(toolsPlugin.getConfig().getItemStack(path + ".items"));
      List<String> commands = toolsPlugin.getConfig().getStringList(path + ".commands");
      shopSell.add(new Shop(title, cost, material, items, commands));
    }
    for (String string : toolsPlugin.getConfig().getConfigurationSection("shopBuy").getKeys(false)) {
      String path = "shopBuy." + string;
      String title = toolsPlugin.getConfig().getString(path + ".title");
      double cost = toolsPlugin.getConfig().getDouble(path + ".cost");
      Material material = Material.getMaterial(toolsPlugin.getConfig().getString(path + ".type"));
      List<ItemStack> items = Collections.singletonList(toolsPlugin.getConfig().getItemStack(path + ".items"));
      List<String> commands = toolsPlugin.getConfig().getStringList(path + ".commands");
      shopBuy.add(new Shop(title, cost, material, items, commands));
    }
  }

  public List<Shop> getShopSell() {
    return shopSell;
  }

  public List<Shop> getShopBuy() {
    return shopBuy;
  }
}
