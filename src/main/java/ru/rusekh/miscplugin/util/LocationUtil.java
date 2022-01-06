package ru.rusekh.miscplugin.util;


import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

  public static boolean isInLoc(Location location, Location center, int size) {
    return location.getX() < (center.getBlockX() + size)
        && location.getX() >=  (center.getBlockX() - size)
        && location.getZ() <  (center.getBlockZ() + size)
        && location.getZ() >= (center.getBlockZ() - size);
  }

  public static boolean isLoc(Location location, Location center, int size) {
    return Math.abs(location.getBlockX() - center.getBlockX()) <= size && Math.abs(location.getBlockZ() - center.getBlockZ()) <= size;
  }

  public static boolean equals(Location location, Location to) {
    return location.getBlockX() == to.getBlockX() && location.getBlockY() == to.getBlockY() && location.getBlockZ() == to.getBlockZ();
  }

  public static boolean equalsFlat(Location location, Location to) {
    return location.getBlockX() == to.getBlockX() && location.getBlockZ() == to.getBlockZ();
  }

  public static Location locFromString(String str) {
    String[] str2loc = str.split(":");
    return new Location(Bukkit.getWorlds().get(0), Double.parseDouble(str2loc[0]), Double.parseDouble(str2loc[1]), Double.parseDouble(str2loc[2]), Float.parseFloat(str2loc[3]), Float.parseFloat(str2loc[4]));
  }

  public static String locToString(Location loc) {
    return loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ() + ":" + loc.getPitch() + ":" + loc.getYaw();
  }
}
