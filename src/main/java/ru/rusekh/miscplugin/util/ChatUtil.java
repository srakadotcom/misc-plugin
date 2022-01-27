package ru.rusekh.miscplugin.util;

import java.util.List;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.data.chat.ChatMessageType;

public class ChatUtil {

  public static void sendMessage(final CommandSender sender, final String message) {
    sender.sendMessage(color(message).replace(">>", "»"));
  }

  public static String color(final String message) {
    return ChatColor.translateAlternateColorCodes('&', message.replace(">>", "»"));
  }

  public static List<String> color(final List<String> message) {
    return message.stream()
        .map(ChatUtil::color)
        .toList();
  }

  public static void sendActionBar(Player player, String message){
    PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(color(message)), (byte)2);
    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
  }

  public static void broadcastMessage(ChatMessageType type, String message) {
    ToolsPlugin.getInstance().getSettingManager().broadcast(message, type);
  }

  public static void sendMessage(Player player, ChatMessageType type, String message) {
    ToolsPlugin.getInstance().getSettingManager().sendMessage(player, message, type);
  }

  public static long fromTime(final long time) {
    return ((time - System.currentTimeMillis()) / 1000L);
  }

  public static String getDurationBreakdown(long millis) {
    if (millis == 0) {
      return "0";
    }

    long days = TimeUnit.MILLISECONDS.toDays(millis);
    if (days > 0) {
      millis -= TimeUnit.DAYS.toMillis(days);
    }

    long hours = TimeUnit.MILLISECONDS.toHours(millis);
    if (hours > 0) {
      millis -= TimeUnit.HOURS.toMillis(hours);
    }

    long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
    if (minutes > 0) {
      millis -= TimeUnit.MINUTES.toMillis(minutes);
    }

    long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
    if (seconds > 0) {
      millis -= TimeUnit.SECONDS.toMillis(seconds);
    }

    StringBuilder sb = new StringBuilder();

    if (days > 0) {
      sb.append(days);

      if (days == 1) {
        sb.append(" dzień ");
      }
      else {
        sb.append(" dni ");
      }
    }

    if (hours > 0) {
      sb.append(hours);

      long last = hours % 10;
      long lastTwo = hours % 100;

      if (hours == 1) {
        sb.append(" godzinę ");
      }
      else if (last < 5 && (lastTwo < 11 || lastTwo > 14)) {
        sb.append(" godziny ");
      }
      else {
        sb.append(" godzin ");
      }
    }

    if (minutes > 0) {
      sb.append(minutes);

      long last = minutes % 10;
      long lastTwo = minutes % 100;

      if (minutes == 1) {
        sb.append(" minutę ");
      }
      else if (last < 5 && (lastTwo < 11 || lastTwo > 14)) {
        sb.append(" minuty ");
      }
      else {
        sb.append(" minut ");
      }
    }

    if (seconds > 0) {
      sb.append(seconds);

      long last = seconds % 10;
      long lastTwo = seconds % 100;

      if (seconds == 1) {
        sb.append(" sekundę ");
      }
      else if (last < 5 && (lastTwo < 11 || lastTwo > 14)) {
        sb.append(" sekundy ");
      }
      else {
        sb.append(" sekund ");
      }
    }

    return (sb.toString());
  }
}