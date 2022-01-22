package ru.rusekh.miscplugin.util;

import java.util.List;
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
}