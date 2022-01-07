package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.util.ChatUtil;
import ru.rusekh.miscplugin.util.RandomUtil;

@CommandAlias("rtp")
public class RandomTeleportCommand extends BaseCommand
{
  private final Cache<UUID, Long> cooldownChat = CacheBuilder.newBuilder()
      .expireAfterWrite(120, TimeUnit.SECONDS)
      .build();

  private final ToolsPlugin toolsPlugin;

  public RandomTeleportCommand(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  @Default
  public boolean onCommand(@Optional CommandSender sender) {
    Player player = (Player) sender;
    Long lastMessage = cooldownChat.getIfPresent(player.getUniqueId());
    if (lastMessage != null && lastMessage >= System.currentTimeMillis()) {
      ChatUtil.sendMessage(player, "&4Błąd: &cNastępny raz randomowego tp możesz użyć za 120 sekund!");
      return false;
    }
    int x = RandomUtil.getRandomInt(-500, 1500);
    int z = RandomUtil.getRandomInt(-500, 1500);
    double y = player.getWorld().getHighestBlockYAt(x, z) + 1.5f;
    Location location = new Location(player.getWorld(), x, y, z);
    cooldownChat.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(120L));
    toolsPlugin.getCacheMap().putIfAbsent(player.getUniqueId(), player.getLocation());
    player.teleport(location, TeleportCause.PLUGIN);
    ChatUtil.sendMessage(player, "&aPrzeteleportowano w losowe miejsce!");
    return false;
  }
}
