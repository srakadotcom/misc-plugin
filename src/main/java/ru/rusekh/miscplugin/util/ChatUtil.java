package ru.rusekh.miscplugin.util;

import java.util.List;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtil {

  public static void sendMessage(final CommandSender sender, final String message) {
    sender.sendMessage(color(message).replace(">>", "»"));
  }

  public static String color(final String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }

  public static List<String> color(final List<String> message) {
    return message.stream()
        .map(ChatUtil::color)
        .collect(Collectors.toList());
  }
}