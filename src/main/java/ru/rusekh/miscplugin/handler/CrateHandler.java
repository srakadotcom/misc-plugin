package ru.rusekh.miscplugin.handler;

import me.badbones69.crazycrates.api.events.PlayerPrizeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.rusekh.miscplugin.data.chat.ChatMessageType;
import ru.rusekh.miscplugin.util.ChatUtil;

public class CrateHandler implements Listener
{
  @EventHandler
  private void onCrate(PlayerPrizeEvent event) {
    Bukkit.getOnlinePlayers().forEach(it -> ChatUtil.sendMessage(it, ChatMessageType.CASE_MESSAGES,
        "&8[&6Skrzynki&8] &6&l" + event.getPlayer().getName() + " &7otwiera " + event.getCrateName()));
  }
}
