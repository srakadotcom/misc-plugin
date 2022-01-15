package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import ru.rusekh.miscplugin.util.ChatUtil;

@CommandAlias("chat")
@CommandPermission("miscplugin.clear")
public class ChatCommand extends BaseCommand {

  public static boolean chatStatus = true;

  @Default
  @Syntax("(on/off/clear)")
  public void handleUsage() {
    throw new InvalidCommandArgument(true);
  }

  @CommandAlias("clear|wyczysc")
  public void clearChat(CommandSender sender) {
    for (int i = 0; i < 100; i++) {
      Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil.sendMessage(player1, " "));
    }
    Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil.sendMessage(player1,
        "&8>> &fChat został wyczyszczony przez &f" + sender.getName()));
  }

  @CommandAlias("off|wylacz")
  public void disableChat(CommandSender sender) {
    if (!chatStatus) {
      ChatUtil.sendMessage(sender, "&8>> &cChat jest już wyłączony!");
      return;
    }
    chatStatus = false;
    Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil.sendMessage(player1,
        "&7Chat został &cwyłączony &7przez &f" + sender.getName()));
  }

  @CommandAlias("on|wlacz")
  public void enableChat(CommandSender sender) {
    if (chatStatus) {
      ChatUtil.sendMessage(sender, "&8>> &cChat jest już włączony!");
      return;
    }

    chatStatus = true;
    Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil.sendMessage(player1,
        "&7Chat został &awłączony &7przez &f" + sender.getName()));
  }
}
