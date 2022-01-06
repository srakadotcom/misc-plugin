package ru.rusekh.miscplugin.manager;

import java.util.Map;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class VanishManager
{
  public static Map<UUID, Player> vanish = new HashMap<>();

  public static void addPlayer(UUID uuid, Player player) {
    vanish.put(uuid, player);
  }

  public static Player getPlayerVanish(UUID uuid) {
    return vanish.get(uuid);
  }

  public static void removePlayer(UUID uuid) {
    vanish.remove(uuid);
  }

  public static boolean hasVanish(UUID uuid) {
    return vanish.containsKey(uuid);
  }
}
