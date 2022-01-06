package ru.rusekh.miscplugin.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.object.Region;
import ru.rusekh.miscplugin.util.ChatUtil;

public class RegionManager
{
  private static final Region spawn = new Region(Bukkit.getWorlds().get(0).getSpawnLocation(), 200);

  public static Region getSpawnRegion() {
    return spawn;
  }

  public static boolean isSpawnRegion(Location location) {
    return spawn.isRegion(location);
  }

  public static boolean build(Player player, Block block, boolean build) {
    if (player != null && block != null) {
      if (player.hasPermission("miscplugin.break")) return false;
      if (player.hasPermission("miscplugin.place")) return false;
      Location location = block.getLocation();
      if (isSpawnRegion(location)) {
        if (block.getType() == Material.ENCHANTMENT_TABLE) return false;
          ChatUtil.sendMessage(player, "&4Błąd: &cNie możesz niszczyć na terenie!");
        return true;
      }
    }
    return false;
  }

}
