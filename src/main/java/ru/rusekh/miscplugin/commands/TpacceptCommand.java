package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.util.ChatUtil;

@CommandAlias("tpaccept")
public class TpacceptCommand extends BaseCommand
{
  private final ToolsPlugin toolsPlugin;

  public TpacceptCommand(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  @Default
  @CommandCompletion("@players")
  public boolean onCommand(@Optional CommandSender sender, @Optional String[] args) {
    Player player = (Player) sender;
    if (args.length != 1) {
      ChatUtil.sendMessage(player, "&8>> &7Poprawne użycie: &f/tpaccept <gracz>");
      return false;
    }
    UUID player2 = toolsPlugin.getTeleportMap().get(player.getUniqueId());
    Player bukkitPlayer = Bukkit.getPlayer(player2);
    if (bukkitPlayer == null) {
      ChatUtil.sendMessage(player, "&8>> &cNie masz żadnych oczekujących próśb teleportacji!");
      return false;
    }
    bukkitPlayer.teleport(player.getLocation());
    ChatUtil.sendMessage(bukkitPlayer, "&8>> &7Gracz &f" + player.getName() + " &7zaakceptował prośbę o teleportację!");
    toolsPlugin.getTeleportMap().remove(bukkitPlayer.getUniqueId());
    toolsPlugin.getTeleportMap().remove(player.getUniqueId());
    return false;
  }
}
