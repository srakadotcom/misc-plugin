package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.util.ChatUtil;

public class CustomRanksCommands extends BaseCommand
{
  private final ToolsPlugin toolsPlugin;

  public CustomRanksCommands(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  @CommandAlias("heal")
  @CommandPermission("miscplugin.heal")
  @Default
  public void healCommand(@Optional CommandSender sender) {
    if (sender == null) {
      Bukkit.getConsoleSender().sendMessage("only player");
      return;
    }
    Player player = (Player) sender;
    player.setHealth(20.0);
    ChatUtil.sendMessage(player, "&aUleczyłeś się!");
  }

  @CommandAlias("feed")
  @CommandPermission("miscplugin.feed")
  @Default
  public void feedCommand(@Optional CommandSender sender) {
    if (sender == null) {
      Bukkit.getConsoleSender().sendMessage("only player");
      return;
    }
    Player player = (Player) sender;
    player.setFoodLevel(20);
    ChatUtil.sendMessage(player, "&aNakarmiłeś się!");
  }

  @CommandAlias("back")
  @CommandPermission("miscplugin.death")
  @Default
  public void backCommand(@Optional CommandSender sender, @Optional String[] args) {
    if (sender == null) {
      Bukkit.getConsoleSender().sendMessage("only player");
      return;
    }
    Player player = (Player) sender;
    if (args.length >= 1) {
      ChatUtil.sendMessage(player, "&8>> &fPoprawne użycie: &6/back");
      return;
    }
    if (toolsPlugin.getCacheMap().containsKey(player.getUniqueId())) {
      ChatUtil.sendMessage(player, "&aPrzeteleportowano do ostatniej lokacji");
      player.teleport(toolsPlugin.getCacheMap().get(player.getUniqueId()));
      toolsPlugin.getCacheMap().remove(player.getUniqueId());
    } else {
      ChatUtil.sendMessage(player, "&4Błąd: &cNie masz żadnej ostatniej lokacji!");
    }
  }
}
