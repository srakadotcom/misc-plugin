package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.util.ChatUtil;

@CommandAlias("helpop")
public class HelpopCommand extends BaseCommand
{
  private final Cache<UUID, Long> helpopMap = CacheBuilder.newBuilder()
      .maximumSize(1000)
      .expireAfterWrite(30, TimeUnit.SECONDS)
      .build();

  @Default
  public void onCommand(@Optional CommandSender sender, @Optional String[] args) {
    Player player = (Player) sender;
    Long lastHelpop = helpopMap.getIfPresent(player.getUniqueId());
    if (lastHelpop != null && lastHelpop >= System.currentTimeMillis()) {
      ChatUtil.sendMessage(player, "&4Błąd: &cNastępną wiadomość możesz wysłać za 30 sekund!");
      return;
    }
    String msg = StringUtils.join(args, " ", 0, args.length);
    if (msg.isEmpty()) {
      ChatUtil.sendMessage(player, "&4Błąd: &cTwoja wiadomość nie może być pusta!");
      return;
    }
    helpopMap.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30L));
    ChatUtil.sendMessage(player, "&aWysłałes wiadomość na helpop!");
    Bukkit.getOnlinePlayers().stream().filter(player1 -> player1.hasPermission("miscplugin.helpop")).forEach(player1 -> ChatUtil.sendMessage(player1, "&4[HelpOP] + &7"+ player.getName() + "&8: &f" + msg));
  }
}
