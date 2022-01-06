package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("fly")
@CommandPermission("miscplugin.fly")
public class FlyCommand extends BaseCommand
{

  @Default
  public boolean onCommand(@Optional CommandSender sender, @Optional String[] args) {
    Player player = (Player) sender;
    if (args.length == 0) {
      player.setAllowFlight(!player.getAllowFlight());
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &fTryb latania został " + (player.getAllowFlight() ? "&awłączony" : "&cwyłączony")));
      return false;
    }
    Player otherPlayer = Bukkit.getPlayer(args[0]);
    if (otherPlayer == null) {
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &fPodany gracz nie istnieje!"));
      return false;
    }
    otherPlayer.setAllowFlight(!otherPlayer.getAllowFlight());
    otherPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &fTryb latania został " + (player.getAllowFlight() ? "&awłączony" : "&cwyłączony") + " &fprzez &d" + player.getName()));
    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &fTryb latania został " + (player.getAllowFlight() ? "&awłączony" : "&cwyłączony") + " &fdla &d" + otherPlayer.getName()));
    return false;
  }
}
