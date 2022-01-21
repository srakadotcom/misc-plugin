package ru.rusekh.miscplugin.data.discord;

import java.util.UUID;
import pl.memexurer.srakadb.sql.mapper.SerializableTableColumn;
import pl.memexurer.srakadb.sql.mapper.TableColumnInfo;
import pl.memexurer.srakadb.sql.mapper.TypedTableColumn;
import pl.memexurer.srakadb.sql.mapper.serializer.UuidValueDeserializer;

public class DiscordUser {
  @TableColumnInfo(primary = true, name = "DiscordUserUniqueId", serialized = @SerializableTableColumn(UuidValueDeserializer.class))
  private UUID uuid;
  @TableColumnInfo(name = "DiscordUserId", typed = @TypedTableColumn("varchar(36)"))
  private String id;
  @TableColumnInfo(name = "PlayerName", typed = @TypedTableColumn("varchar(16)"))
  private String name;

  public DiscordUser(UUID uuid, String id, String name) {
    this.uuid = uuid;
    this.id = id;
    this.name = name;
  }

  private DiscordUser() {
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
