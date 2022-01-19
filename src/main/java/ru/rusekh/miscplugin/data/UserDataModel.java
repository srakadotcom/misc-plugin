package ru.rusekh.miscplugin.data;

import java.util.UUID;
import pl.memexurer.srakadb.sql.mapper.SerializableTableColumn;
import pl.memexurer.srakadb.sql.mapper.TableColumnInfo;
import pl.memexurer.srakadb.sql.mapper.serializer.UuidValueDeserializer;

public class UserDataModel {

  @TableColumnInfo(primary = true, name = "PlayerUniqueId", serialized = @SerializableTableColumn(UuidValueDeserializer.class))
  private UUID uuid;
  @TableColumnInfo(nullable = false, name = "PlayerHomes", serialized = @SerializableTableColumn(PlayerHomes.Deserializer.class))
  private PlayerHomes homes;

  public UserDataModel(UUID uuid) {
    this.uuid = uuid;
    this.homes = new PlayerHomes();
  }

  private UserDataModel() {
  }

  public UUID getUuid() {
    return uuid;
  }

  public PlayerHomes getHomes() {
    return homes;
  }
}
