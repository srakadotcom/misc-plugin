package ru.rusekh.miscplugin.data.discord;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.memexurer.srakadb.sql.table.DatabaseTable;
import pl.memexurer.srakadb.sql.table.query.DatabaseFetchQuery;
import pl.memexurer.srakadb.sql.table.query.DatabaseInsertQuery;
import pl.memexurer.srakadb.sql.table.query.DatabaseInsertQuery.UpdateType;
import pl.memexurer.srakadb.sql.table.transaction.DatabaseTransactionError;

public class DiscordUserRepository {
  private final DatabaseTable<DiscordUser> databaseTable = new DatabaseTable<>("1jw293wnk5il632wemiTPJ",
      DiscordUser.class);
  private final Plugin plugin;

  public DiscordUserRepository(Plugin plugin, Connection connection) {
    this.plugin = plugin;
    this.databaseTable.initializeTable(connection);
  }

  public CompletableFuture<DiscordUser> fetchModel(UUID uuid) {
    CompletableFuture<DiscordUser> future = new CompletableFuture<>();
    plugin.getServer().getScheduler().runTask(plugin, () -> {
      try {
        DiscordUser dataModel = fetchDataModel(uuid);
        future.complete(dataModel);
      } catch (Exception ex) {
        future.completeExceptionally(ex);
      }
    });
    return future;
  }

  public CompletableFuture<DiscordUser> fetchModel(String id) {
    CompletableFuture<DiscordUser> future = new CompletableFuture<>();
    plugin.getServer().getScheduler().runTask(plugin, () -> {
      try {
        DiscordUser dataModel = fetchDataModelId(id);
        future.complete(dataModel);
      } catch (Exception ex) {
        future.completeExceptionally(ex);
      }
    });
    return future;
  }

  public CompletableFuture<Boolean> createDataModel(Player player, String id) {
    CompletableFuture<Boolean> future = new CompletableFuture<>();
    plugin.getServer().getScheduler().runTask(plugin, () -> {
      try {
        future.complete(insertDataModel(new DiscordUser(player.getUniqueId(), id, player.getName())));
      } catch (Exception ex) {
        ex.printStackTrace();
        future.completeExceptionally(ex);
      }
    });
    return future;
  }

  private DiscordUser fetchDataModel(UUID uuid) {
    try (var transaction = new DatabaseFetchQuery()
        .precondition(databaseTable.getModelMapper().createQueryPair("uuid", uuid))
        .executeFetchQuery(databaseTable)) {
      return transaction.readResult();
    }
  }

  private DiscordUser fetchDataModelId(String id) {
    try (var transaction = new DatabaseFetchQuery()
        .precondition(databaseTable.getModelMapper().createQueryPair("id", id))
        .executeFetchQuery(databaseTable)) {
      return transaction.readResult();
    }
  }

  private boolean insertDataModel(DiscordUser dataModel) {
    try {
      new DatabaseInsertQuery(UpdateType.INSERT)
          .values(databaseTable.getModelMapper().createQueryPairs(dataModel))
          .execute(databaseTable).close();
    } catch (DatabaseTransactionError e) {
      if(e.getCause() instanceof SQLException && ((SQLException) e.getCause()).getErrorCode() == 2627)
        return true;
      else
        throw e;
    }
    return false;
  }
}
