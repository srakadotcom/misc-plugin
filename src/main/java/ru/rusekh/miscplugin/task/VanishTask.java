package ru.rusekh.miscplugin.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.manager.VanishManager;

public class VanishTask implements Runnable
{

  @Override
  public void run() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
        if (VanishManager.hasVanish(player.getUniqueId())) {
          onlinePlayer.hidePlayer(player);
        } else {
          onlinePlayer.showPlayer(player);
        }
      }
    }
  }
}
