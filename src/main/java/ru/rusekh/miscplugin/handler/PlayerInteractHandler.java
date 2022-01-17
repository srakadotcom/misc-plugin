package ru.rusekh.miscplugin.handler;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;
import ru.rusekh.miscplugin.manager.RegionManager;

public class PlayerInteractHandler implements Listener
{
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  private void onPlayerBlockPlace(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();
    if (RegionManager.build(player, block, false)) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  private void onPlayerBreakWheat(PlayerInteractEvent event) {
    if (event.getAction() == Action.PHYSICAL) event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  private void onPlayerBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();
    if (RegionManager.build(player, block, false)) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  private void interact(PlayerInteractEvent event) {
    if (event.getClickedBlock() == null) return;
    if (event.getClickedBlock().getType() == Material.STONE_BUTTON) {
      Button button = (Button) event.getClickedBlock().getState().getData();
      Block rtpBlock = event.getClickedBlock().getRelative(button.getAttachedFace());
      if (rtpBlock.getType() != Material.NOTE_BLOCK) return;
      Bukkit.dispatchCommand(event.getPlayer(), "rtp");
    }
    if (event.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
      event.setCancelled(true);
    }
    if (event.getClickedBlock().getType() == Material.ANVIL) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  private void onPlayerGetDamageByBlock(EntityDamageByBlockEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  private void dmg(EntityDamageEvent event) {
    if (event.getEntity().getType() == EntityType.PLAYER) event.setCancelled(true);
  }
}
