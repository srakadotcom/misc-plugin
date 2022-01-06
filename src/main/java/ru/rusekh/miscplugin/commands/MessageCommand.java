package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.util.ChatUtil;

@CommandAlias("msg")
public class MessageCommand extends BaseCommand
{

  @Default
  @CommandCompletion("@players")
  public boolean onCommand(@Optional CommandSender sender, @Optional String[] args) {
    Player player = (Player) sender;
    if (args.length < 2) {
      ChatUtil.sendMessage(player, "&8>> &7Poprawne użycie: &f/msg <gracz>");
      return false;
    }
    Player player2 = Bukkit.getPlayer(args[0]);
    if (player2 == null) {
      ChatUtil.sendMessage(player, "&8>> &cTen gracz jest offline!");
      return false;
    }
    String message = ChatColor.stripColor(ChatUtil.color(StringUtils.join(args, " ", 1, args.length)));
    ChatUtil.sendMessage(player, "&6Ja &8>> &6" + player2.getName() + "&8: &f" + message);
    ChatUtil.sendMessage(player2, "&6" + player.getName() + " &8>> &6Ja&8: &f" + message);
    return false;
  }
}
