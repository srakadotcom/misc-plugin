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
  @TableColumnInfo(nullable = false, typed = @TypedTableColumn("boolean"))
  private boolean autoMessages;
  @TableColumnInfo(nullable = false, typed = @TypedTableColumn("boolean"))
  private boolean msgMessages;
  @TableColumnInfo(nullable = false, typed = @TypedTableColumn("boolean"))
  private boolean caseMessages;
  @TableColumnInfo(nullable = false, typed = @TypedTableColumn("boolean"))
  private boolean banMessages;

  public SettingDataModel(UUID uuid) {
    this.uuid = uuid;
    this.chatMessages = true;
    this.autoMessages = true;
    this.msgMessages = true;
    this.caseMessages = true;
    this.banMessages = true;
  }

  private SettingDataModel() {
  }

  public boolean is(ChatMessageType type) {
    return switch (type) {
      case CHAT_MESSAGES -> chatMessages;
      case MSG_MESSAGES -> msgMessages;
      case AUTO_MESSAGES -> autoMessages;
      case BAN_MESSAGES -> banMessages;
      case CASE_MESSAGES -> caseMessages;
    };
  }

  public boolean toggle(ChatMessageType type) {
    return switch (type) {
      case CHAT_MESSAGES -> chatMessages = !chatMessages;
      case BAN_MESSAGES -> banMessages = !banMessages;
      case AUTO_MESSAGES -> autoMessages = !autoMessages;
      case CASE_MESSAGES -> caseMessages = !caseMessages;
      case MSG_MESSAGES -> msgMessages = !msgMessages;
    };
  }

  public UUID getUuid() {
    return uuid;
  }
}
