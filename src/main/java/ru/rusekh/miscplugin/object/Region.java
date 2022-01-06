package ru.rusekh.miscplugin.object;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import ru.rusekh.miscplugin.util.LocationUtil;

public class Region
{
  private final Location center;
  private int size;

  public Region(Location center, int size) {
    this.center = center;
    this.size = size;
  }

  public int getSize() {
    return this.size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public Location getCenter() {
    return this.center;
  }

  public boolean isInRegion(Location location) {
    return LocationUtil.isInLoc(location, this.center, this.size);
  }

  public boolean isCenter(Location l) {
    Vector u = new Vector(this.center.getBlockX() + 3, this.center.getBlockY() + 5, this.center.getBlockZ() + 3);
    Vector d = new Vector(this.center.getBlockX() - 3, this.center.getBlockY() - 2, this.center.getBlockZ() - 3);
    Location centerUp = u.toLocation(this.center.getWorld());
    Location centerDown = d.toLocation(this.center.getWorld());
    return l.getBlockX() > centerDown.getBlockX() && l.getBlockX() < centerUp.getBlockX() && l.getBlockY() > centerDown.getBlockY() && l.getBlockY() < centerUp.getBlockY() && l.getBlockZ() > centerDown.getBlockZ() && l.getBlockZ() < centerUp.getBlockZ();
  }

  public boolean isRegion(Location location) {
    return LocationUtil.isLoc(location, this.center, this.size);
  }

}
