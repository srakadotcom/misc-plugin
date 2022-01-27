package ru.rusekh.miscplugin.handler;

import me.leoko.advancedban.bukkit.event.PunishmentEvent;
import me.leoko.advancedban.utils.PunishmentType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ru.rusekh.miscplugin.data.chat.ChatMessageType;
import ru.rusekh.miscplugin.util.ChatUtil;

public class PunishmentHandler implements Listener
{
  @EventHandler(priority = EventPriority.MONITOR)
  private void onPunishment(PunishmentEvent event) {
    Player player = Bukkit.getPlayer(event.getPunishment().getName());
    Bukkit.getOnlinePlayers().forEach(it -> ChatUtil.sendMessage(it, ChatMessageType.BAN_MESSAGES, "&c" + player.getName() + " został " + getType(event.getPunishment().getType()) + "! Powód: " + event.getPunishment().getReason()));
  }

  public String getType(PunishmentType punishmentType) {
    String name;
    switch (punishmentType) {
      case BAN -> name = "zbanowany";
      case KICK -> name = "wyrzucony";
      case MUTE -> name = "wyciszony";
      case IP_BAN -> name = "zbanowany na IP";
      case WARNING -> name = "ostrzeżony";
      case TEMP_BAN -> name = "tymaczasowo zbanowany";
      case TEMP_MUTE -> name = "tymczasowo wyciszony";
      case TEMP_IP_BAN -> name = "tymczasowo zbanowany na IP";
      case TEMP_WARNING -> name = "tymczasowo ostrzeżony";
      default -> name = "dupa dupciszu 69";
    }
    return name;
  }
}
