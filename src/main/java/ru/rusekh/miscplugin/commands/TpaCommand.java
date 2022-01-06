package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.util.ChatUtil;

@CommandAlias("tpa")
public class TpaCommand extends BaseCommand {
  private final ToolsPlugin toolsPlugin;

  public TpaCommand(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  @Default
  @CommandCompletion("@players")
  public boolean onCommand(@Optional CommandSender sender, @Optional String[] args) {
    Player player = (Player) sender;
    if (args.length != 1) {
      ChatUtil.sendMessage(player, "&8>> &7Poprawne użycie: &f/tpa <gracz>");
      return false;
    }
    Player player2 = Bukkit.getPlayer(args[0]);
    if (player2 == null) {
      ChatUtil.sendMessage(player, "&8>> &cTen gracz jest offline!");
      return false;
    }
    toolsPlugin.getTeleportMap().putIfAbsent(player2.getUniqueId(), player.getUniqueId());
    ChatUtil.sendMessage(
        player2, "&7Gracz " + player.getName() + " chce się do ciebie przeteleportować");
    ChatUtil.sendMessage(
        player2, "&7Wpisz &f/tpaccept " + player.getName() + " &7aby zaakceptować.");
    ChatUtil.sendMessage(player, "&8>> &7Pomyślnie wysłano prośbę teleportacji.");
    return false;
  }
}
