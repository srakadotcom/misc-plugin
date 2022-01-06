package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.util.ChatUtil;

@CommandAlias("spawn")
public class SpawnCommand extends BaseCommand
{

  private final ToolsPlugin toolsPlugin;

  public SpawnCommand(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  @Default
  public boolean onCommand(@Optional CommandSender sender) {
    Player player = (Player) sender;
    toolsPlugin.getCacheMap().putIfAbsent(player.getUniqueId(), player.getLocation());
    player.teleport(player.getWorld().getSpawnLocation());
    ChatUtil.sendMessage(player, "&aPrzeteleportowano na spawna!");
    return false;
  }
}
