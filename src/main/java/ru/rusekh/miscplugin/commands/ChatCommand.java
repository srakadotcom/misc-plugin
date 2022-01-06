package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.util.ChatUtil;

@CommandAlias("chat")
@CommandPermission("miscplugin.clear")
public class ChatCommand extends BaseCommand
{
  public static boolean chatStatus;

  @Default
  @CommandCompletion("on|off|clear")
  public boolean onCommand(@Optional CommandSender sender, @Optional String[] args) {
    Player player = (Player) sender;
    if (args.length != 1) {
      ChatUtil.sendMessage(player, "&8>> &7Poprawne użycie: &f/chat <clear/off/on/clear>");
      return false;
    }
    if (args[0].equalsIgnoreCase("on")) {
      if (chatStatus) {
        ChatUtil.sendMessage(player, "&8>> &cChat jest już włączony!");
        return false;
      }
      chatStatus = true;
      Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil.sendMessage(player1, "&7Chat został &awłączony &7przez &f" + player.getName()));
    }
    if (args[0].equalsIgnoreCase("off")) {
      if (!chatStatus) {
        ChatUtil.sendMessage(player, "&8>> &cChat jest już wyłączony!");
        return false;
      }
      chatStatus = false;
      Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil.sendMessage(player1, "&7Chat został &cwyłączony &7przez &f" + player.getName()));
    }
    if (args[0].equalsIgnoreCase("clear")) {
      for (int i = 0; i < 100; i++) {
        Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil.sendMessage(player1, " "));
      }
      Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil.sendMessage(player1, "&8>> &fChat został wyczyszczony przez &f" + player.getName()));
    }
    return false;
  }
}
