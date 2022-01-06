package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.command.CommandSender;
import ru.rusekh.miscplugin.util.ChatUtil;

@CommandAlias("help|pomoc")
public class HelpCommand extends BaseCommand
{
  @Default
  public void onCommand(@Optional CommandSender sender) {
    ChatUtil.sendMessage(sender, "&8* &7/dzialki &8- &fPomoc odnośnie działek");
    ChatUtil.sendMessage(sender, "&8* &7/wyplac &8- &fInformacje odnośnie wypłacania kasy");
    ChatUtil.sendMessage(sender, "&8* &7/ile &8- &fAktualny stan twojego konta");
    ChatUtil.sendMessage(sender, "&8* &7/tophajsu &8- &fTopka hajsu");
    ChatUtil.sendMessage(sender, "&8* &7/rtp &8- &fRandomowa teleportacja");
    ChatUtil.sendMessage(sender, "&8* &7/kit &8- &fZestawy dla rang");
    ChatUtil.sendMessage(sender, "&8* &7/rangi &8- &fLista dostępnych rang");
    ChatUtil.sendMessage(sender, "&8* &7/helpop &8- &fWiadomość do administracji");
  }
}
