package ru.rusekh.miscplugin.handler;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.commands.ChatCommand;
import ru.rusekh.miscplugin.data.chat.ChatMessageType;
import ru.rusekh.miscplugin.util.ChatUtil;

public class PlayerChatHandler implements Listener {

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
    if (!ChatCommand.chatStatus && !player.hasPermission("miscplugin.chat")) {
      event.setCancelled(true);
      return;
    }

    Long lastMessage = cooldownChat.getIfPresent(player.getUniqueId());
    if (!player.hasPermission("miscplugin.chat") && lastMessage != null
        && lastMessage >= System.currentTimeMillis()) {
      ChatUtil.sendActionBar(player, "&cNastępna wiadomość napiszesz za 5 sekund!");
      ChatUtil.sendMessage(player,"&cNastępna wiadomość napiszesz za 5 sekund!");
      event.setCancelled(true);
      return;
    }

    if(event.getPlayer().hasPermission("miscplugin.chat")) {
      event.setMessage(ChatUtil.color(event.getMessage()));
    }

    cooldownChat.put(player.getUniqueId(),
        System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5L));
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onChatSetting(AsyncPlayerChatEvent event) {
    event.setCancelled(true);

    String message =   ChatUtil.color(toolsPlugin.getConfig()
            .getString("chat-format." + (toolsPlugin.getChat() == null ? "default"
                : toolsPlugin.getChat().getPrimaryGroup(event.getPlayer()))))
        .replace("{PLAYER}", event.getPlayer().getName())
        .replace("{MESSAGE}", event.getMessage());

    if(event.getPlayer().hasPermission("miscplugin.chat")) {
      Bukkit.broadcastMessage(message);
    } else {
      toolsPlugin.getSettingManager().broadcast(
          event.getPlayer(), message, ChatMessageType.CHAT_MESSAGES);
    }
  }
}
