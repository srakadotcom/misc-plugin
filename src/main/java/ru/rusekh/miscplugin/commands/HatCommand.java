package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("hat")
@CommandPermission("miscplugin.hat")
public class HatCommand extends BaseCommand
{

  @Default
  public boolean onCommand(@Optional CommandSender sender) {
    Player player = (Player) sender;
    if (player.getItemInHand().getType() == Material.AIR) {
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cMusisz mieć blok w ręce!"));
      return false;
    }
    player.getInventory().setHelmet(player.getItemInHand());
    return false;
  }
}
