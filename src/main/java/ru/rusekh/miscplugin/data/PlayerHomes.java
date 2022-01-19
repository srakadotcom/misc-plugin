package ru.rusekh.miscplugin.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import pl.memexurer.srakadb.sql.mapper.serializer.TableColumnValueDeserializer;
import ru.rusekh.miscplugin.util.LocationUtil;

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
    if (number > homeMap.size() || number < 0) {
      return false;
    }

    homeMap.put(number, location);
    return true;
  }

  public Location getHome(int number) {
    if (number > homeMap.size() || number < 0) {
      return null;
    }

    return homeMap.get(number);
  }

  @Override
  public String toString() {
    return String.valueOf(homeMap);
  }

  public static class Deserializer implements TableColumnValueDeserializer<PlayerHomes> {

    public Deserializer() {
    }

    @Override
    public PlayerHomes deserialize(ResultSet resultSet, String s) throws SQLException {
      String[] splitted = resultSet.getString(s).split(",");
      PlayerHomes homes = new PlayerHomes();

      for (String str : splitted) {
        String[] splittedAgain = str.split(";");
        homes.homeMap.put(Integer.parseInt(splittedAgain[0]),
            LocationUtil.locFromString(splittedAgain[1]));
      }

      return homes;
    }

    @Override
    public Object serialize(PlayerHomes playerHomes) {
      StringBuilder builder = new StringBuilder();
      for (Map.Entry<Integer, Location> entry : playerHomes.homeMap.entrySet()) {
        builder.append(entry.getKey()).append(";").append(LocationUtil.locToString(entry.getValue()))
            .append(',');
      }

      return builder.substring(0, builder.length() - 1);
    }

    @Override
    public String getDataType() {
      return "text";
    }
  }
}
