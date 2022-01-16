package ru.rusekh.miscplugin.data;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;

public class PlayerHomes {

  private final Map<Integer, Location> homeMap = new HashMap<>();

  public boolean createHome(Location location, int limit) {
    if (homeMap.size() == limit) {
      return false;
    }

    homeMap.put(homeMap.size(), location);
    return true;
  }

  public boolean hasHome(int number) {
    return homeMap.containsKey(number);
  }

  public boolean setHome(Location location, int number) {
    if(number > homeMap.size() || number < 0)
      return false;

    homeMap.put(number, location);
    return true;
  }

  public Location getHome(int number) {
    if(number > homeMap.size() || number < 0)
      return null;

    return homeMap.get(number);
  }
}
