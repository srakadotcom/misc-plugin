package ru.rusekh.miscplugin.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
public class PlayerJoinHandler implements Listener
{
  @EventHandler
  private void onPlayerJoin(PlayerJoinEvent event) {
    event.setJoinMessage(null);
    Player player = event.getPlayer();
    if (!player.hasPlayedBefore()) player.teleport(player.getWorld().getSpawnLocation());
  }

  @EventHandler
  private void onPlayerQuit(PlayerQuitEvent event) {
    event.setQuitMessage(null);
  }
}
