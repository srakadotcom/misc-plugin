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
import ru.rusekh.miscplugin.manager.VanishManager;

@CommandAlias("vanish|v")
@CommandPermission("miscplugin.vanish")
public class VanishCommand extends BaseCommand
{

  @Default
  public boolean onCommand(@Optional CommandSender sender) {
    Player player = (Player) sender;
    if (VanishManager.hasVanish(player.getUniqueId())) {
      VanishManager.removePlayer(player.getUniqueId());
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cWyłączyłeś &fvanisha"));
      for (Player all : Bukkit.getOnlinePlayers()) {
        all.showPlayer(player);
      }
    } else {
      VanishManager.addPlayer(player.getUniqueId(), player);
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &aWłączyłeś &fvanisha"));
      for (Player all : Bukkit.getOnlinePlayers()) {
        if (!all.hasPermission("miscplugin.vanish")) {
          all.hidePlayer(player);
        }
      }
    }
    return false;
  }
}
