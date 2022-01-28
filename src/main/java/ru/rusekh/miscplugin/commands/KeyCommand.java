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

@CommandAlias("key|klucz|klucze")
@CommandPermission("miscplugin.key")
public class KeyCommand extends BaseCommand
{
  @Default
  @CommandCompletion("epicka|legendarna|pospolita|rzadka")
  public void onCommand(@Optional CommandSender sender, @Optional String[] args) {
    Player player = (Player) sender;
    if (args.length == 0) {
      ChatUtil.sendMessage(player, "&7Poprawne użycie: &f/klucz <epicka|legendarna|pospolita|rzadka> <ilośc>");
      return;
    }
    switch (args[0]) {
      case "epicka" -> {
        if (args.length != 2)
          return;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crazycrates giveall p epicka " + args[1]);
        Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil
            .sendMessage(player1, "&7Cały serwer otrzymał &d&lEpicki &7Klucz &8x&f" + args[1]));
      }
      case "legendarna" -> {
        if (args.length != 2)
          return;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crazycrates giveall p legendarna " + args[1]);
        Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil
            .sendMessage(player1, "&7Cały serwer otrzymał &6&lLegendarny &7Klucz &8x&f" + args[1]));
      }
      case "zwykla" -> {
        if (args.length != 2)
          return;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crazycrates giveall p zwykla " + args[1]);
        Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil
            .sendMessage(player1, "&7Cały serwer otrzymał &2&lZwykły &7Klucz &8x&f" + args[1]));
      }
      case "rzadka" -> {
        if (args.length != 2)
          return;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crazycrates giveall p rzadka " + args[1]);
        Bukkit.getOnlinePlayers().forEach(player1 -> ChatUtil
            .sendMessage(player1, "&7Cały serwer otrzymał &b&lRzadki &7Klucz &8x&f" + args[1]));
      }
      default -> ChatUtil.sendMessage(player,
          "&7Poprawne użycie: &f/klucz <epicka|legendarna|zwykla|rzadka> <ilośc>");
    }
  }
}
