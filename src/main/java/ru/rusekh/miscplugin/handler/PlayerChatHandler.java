package ru.rusekh.miscplugin.handler;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.commands.ChatCommand;
import ru.rusekh.miscplugin.util.ChatUtil;

public class PlayerChatHandler implements Listener
{
  private final Cache<UUID, Long> cooldownChat = CacheBuilder.newBuilder()
      .expireAfterWrite(5, TimeUnit.SECONDS)
      .build();

  private final ToolsPlugin toolsPlugin;

  public PlayerChatHandler(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  @EventHandler
  public void onPlayerChat(AsyncPlayerChatEvent event) {
    Player player = event.getPlayer();
    String group = toolsPlugin.getChat().getPrimaryGroup(player);
    if (!ChatCommand.chatStatus) if (!player.hasPermission("miscplugin.chat")) event.setCancelled(true);
    if (player.hasPermission("miscplugin.chat")) {
      event.setFormat(ChatColor.translateAlternateColorCodes('&', toolsPlugin.getConfig().getString("chat-format." + group)
          .replace("{PLAYER}", player.getName())
          .replace("{MESSAGE}", "%2$s")));
      return;
    }
    Long lastMessage = cooldownChat.getIfPresent(player.getUniqueId());
    if (lastMessage != null && lastMessage >= System.currentTimeMillis()) {
      ChatUtil.sendActionBar(player, "&cNastępna wiadomość napiszesz za 5 sekund!");
      event.setCancelled(true);
      return;
    }
    cooldownChat.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5L));
    event.setFormat(ChatColor.translateAlternateColorCodes('&', toolsPlugin.getConfig().getString("chat-format." + group)
          .replace("{PLAYER}", player.getName())
          .replace("{MESSAGE}", "%2$s")));
  }
}
