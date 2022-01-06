package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("clear")
@CommandPermission("miscplugin.clear")
public class ClearCommand extends BaseCommand
{

  @Default
  public boolean onCommand(@Optional CommandSender sender) {
    Player player = (Player) sender;
    if (!player.hasPermission("miscplugin.clear")) {
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNie masz uprawnień (miscplugin.clear)"));
      return false;
    }
    player.getInventory().setArmorContents(null);
    player.getInventory().clear();
    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usunąłeś przedmioty z ekwipunku!"));
    return false;
  }
}
