package ru.rusekh.miscplugin.data.chat;

import java.util.UUID;
import pl.memexurer.srakadb.sql.mapper.SerializableTableColumn;
import pl.memexurer.srakadb.sql.mapper.TableColumnInfo;
import pl.memexurer.srakadb.sql.mapper.TypedTableColumn;
import pl.memexurer.srakadb.sql.mapper.serializer.UuidValueDeserializer;

public class SettingDataModel {

  @TableColumnInfo(primary = true, name = "PlayerUniqueId", serialized = @SerializableTableColumn(UuidValueDeserializer.class))
  private UUID uuid;
  @TableColumnInfo(nullable = false, typed = @TypedTableColumn("boolean"))
  private boolean chatMessages;

  public SettingDataModel(UUID uuid) {
    this.uuid = uuid;
    this.chatMessages = true;
  }

  private SettingDataModel() {
  }

  public boolean is(ChatMessageType type) {
    if (type == ChatMessageType.CHAT_MESSAGES) {
      return chatMessages;
    }
    throw new IllegalArgumentException("Unknown message type.");
  }

  public boolean toggle(ChatMessageType type) {
    if(type == ChatMessageType.CHAT_MESSAGES)
      return chatMessages = !chatMessages;
    throw new IllegalArgumentException("Unknown message type.");
  }

  public UUID getUuid() {
    return uuid;
  }
}
