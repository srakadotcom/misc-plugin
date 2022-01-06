package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.util.ChatUtil;

@CommandAlias("wyplac")
public class WyplacCommand extends BaseCommand
{
  @Default
  public void onCommand(@Optional CommandSender sender) {
    Player player = (Player) sender;
    ChatUtil.sendMessage(player, "&7Minimalna kwota wypłaty to 10zł");
    ChatUtil.sendMessage(player, "&7Swój aktualny stan konta sprawdzisz pod /ile");
    ChatUtil.sendMessage(player, "&7Aby wypłacić pieniądze otwórz Ticket na naszym discordzie");
    ChatUtil.sendMessage(player, "&7dc.cashblock.eu");
  }
}
