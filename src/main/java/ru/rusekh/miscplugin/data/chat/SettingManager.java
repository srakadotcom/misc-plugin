package ru.rusekh.miscplugin.data.chat;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zaxxer.hikari.HikariDataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.memexurer.srakadb.sql.table.DatabaseTable;
import pl.memexurer.srakadb.sql.table.query.DatabaseFetchQuery;
import pl.memexurer.srakadb.sql.table.query.DatabaseInsertQuery;
import pl.memexurer.srakadb.sql.table.query.DatabaseInsertQuery.UpdateType;
import pl.memexurer.srakadb.sql.table.transaction.DatabaseTransactionError;

public class SettingManager {

  private final DatabaseTable<SettingDataModel> databaseTable;
  private final Plugin plugin;
  private final Cache<UUID, SettingDataModel> cache = CacheBuilder.newBuilder()
      .expireAfterWrite(5, TimeUnit.MINUTES)
      .build();

  public SettingManager(Plugin plugin, HikariDataSource dataSource) {
    this.plugin = plugin;
    this.databaseTable = new DatabaseTable<>("rzygisranie", dataSource,
        SettingDataModel.class);
    this.databaseTable.initializeTable();
  }

  public void broadcast(String message, ChatMessageType type) {
    Collection<? extends Player> onlinePlayers = plugin.getServer().getOnlinePlayers();
    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
      List<Player> missingPlayers = new ArrayList<>();

      DatabaseFetchQuery query = new DatabaseFetchQuery();
      for (Player player : onlinePlayers) {
        SettingDataModel dataModel = cache.getIfPresent(player.getUniqueId());
        if (dataModel != null) {
          if (dataModel.is(type)) {
            player.sendMessage(message);
          }
        } else {
          query.or(databaseTable.getModelMapper().createQueryPair("uuid", player.getUniqueId()));
          missingPlayers.add(player);
        }
      }

      if (!query.isEmpty()) {
        for (SettingDataModel model : query.executeFetchQuery(databaseTable)) {
          updateCache(model);
          onlinePlayers.stream()
              .filter(x -> x.getUniqueId().equals(model.getUuid()))
              .findFirst().ifPresent(player -> {
                missingPlayers.remove(player);
                if (model.is(type)) {
                  player.sendMessage(message);
                }
              });
        }
      }

      missingPlayers.forEach(player -> player.sendMessage(message));
    });
  }

  public void broadcast(Player sender, String message, ChatMessageType type) {
    Collection<? extends Player> onlinePlayers = plugin.getServer().getOnlinePlayers();
    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
      List<Player> missingPlayers = new ArrayList<>();

      DatabaseFetchQuery query = new DatabaseFetchQuery();
      for (Player player : onlinePlayers) {
        SettingDataModel dataModel = cache.getIfPresent(player.getUniqueId());
        if (dataModel != null) {
          if (dataModel.is(type)) {
            player.sendMessage(message);
          } else if (dataModel.getUuid().equals(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED
                + "Probujesz wyslac wiadomosc na chat, ale posiadasz chat ukryty czy cos.");
            player.sendMessage(
                ChatColor.RED + "Ale twoja wiadomosc zostala i tak wyslana wiec wiesz xd");
          }
        } else {
          query.or(databaseTable.getModelMapper().createQueryPair("uuid", player.getUniqueId()));
          missingPlayers.add(player);
        }
      }

      if (!query.isEmpty()) {
        for (SettingDataModel model : query.executeFetchQuery(databaseTable)) {
          updateCache(model);
          onlinePlayers.stream()
              .filter(x -> x.getUniqueId().equals(model.getUuid()))
              .findFirst().ifPresent(player -> {
                missingPlayers.remove(player);
                if (model.is(type)) {
                  player.sendMessage(message);
                } else if (model.getUuid().equals(player.getUniqueId())) {
                  player.sendMessage(ChatColor.RED
                      + "Probujesz wyslac wiadomosc na chat, ale posiadasz chat ukryty czy cos.");
                  player.sendMessage(
                      ChatColor.RED + "Ale twoja wiadomosc zostala i tak wyslana wiec wiesz xd");
                }
              });
        }
      }

      missingPlayers.forEach(player -> player.sendMessage(message));
    });
  }

  public void sendMessage(Player player, String message, ChatMessageType type) {
    SettingDataModel dataModel = cache.getIfPresent(player.getUniqueId());
    if (dataModel == null) {
      querySingleDataModel(player.getUniqueId()).thenAccept(model -> {
        if (model == null || model.is(type)) {
          player.sendMessage(message);
        }
      });
    } else if (dataModel.is(type)) {
      player.sendMessage(message);
    }
  }

  public CompletableFuture<SettingDataModel> querySingleDataModel(UUID uuid) {
    CompletableFuture<SettingDataModel> future = new CompletableFuture<>();

    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
      try {
        new DatabaseFetchQuery()
            .and(databaseTable.getModelMapper().createQueryPair("uuid", uuid))
            .executeFetchQuerySingle(databaseTable)
            .ifPresentOrElse(future::complete, () -> future.complete(null));
      } catch (DatabaseTransactionError error) {
        future.completeExceptionally(error.getCause());
      }
    });

    return future;
  }

  public CompletableFuture<Void> updateSettings(SettingDataModel dataModel) {
    updateCache(dataModel);

    CompletableFuture<Void> future = new CompletableFuture<>();
    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
      try {
        new DatabaseInsertQuery(UpdateType.REPLACE)
            .values(databaseTable.getModelMapper().createQueryPairs(dataModel))
            .execute(databaseTable);
      } catch (DatabaseTransactionError error) {
        future.completeExceptionally(error.getCause());
      }
    });

    return future;
  }

  private void updateCache(SettingDataModel dataModel) {
    cache.put(dataModel.getUuid(), dataModel);
  }
}
