package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("workbench|wb")
@CommandPermission("miscplugin.wb")
public class WorkbenchCommand extends BaseCommand
{

  @Default
  public boolean onCommand(@Optional CommandSender sender) {
    Player player = (Player) sender;
    player.openWorkbench(player.getLocation(), true);
    return false;
  }
}
