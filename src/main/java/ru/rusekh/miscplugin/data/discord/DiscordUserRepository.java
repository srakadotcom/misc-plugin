package ru.rusekh.miscplugin.data.discord;

import com.zaxxer.hikari.HikariDataSource;
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

  private final DatabaseTable<DiscordUser> databaseTable;
  private final Plugin plugin;

  public DiscordUserRepository(Plugin plugin, HikariDataSource dataSource) {
    this.plugin = plugin;
    this.databaseTable = new DatabaseTable<>("1jw293wnk5il632wemiTPJ", dataSource,
        DiscordUser.class);
    this.databaseTable.initializeTable();
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
        future.complete(
            insertDataModel(new DiscordUser(player.getUniqueId(), id, player.getName())));
      } catch (Exception ex) {
        ex.printStackTrace();
        future.completeExceptionally(ex);
      }
    });
    return future;
  }

  private DiscordUser fetchDataModel(UUID uuid) {
    return new DatabaseFetchQuery()
        .precondition(databaseTable.getModelMapper().createQueryPair("uuid", uuid))
        .executeFetchQuerySingle(databaseTable).orElse(null);
  }

  private DiscordUser fetchDataModelId(String id) {
    return new DatabaseFetchQuery()
        .precondition(databaseTable.getModelMapper().createQueryPair("id", id))
        .executeFetchQuerySingle(databaseTable).orElse(null);
  }

  private boolean insertDataModel(DiscordUser dataModel) {
    try {
      new DatabaseInsertQuery(UpdateType.INSERT)
          .values(databaseTable.getModelMapper().createQueryPairs(dataModel))
          .execute(databaseTable);
    } catch (DatabaseTransactionError e) {
      if (e.getCause() instanceof SQLException
          && ((SQLException) e.getCause()).getErrorCode() == 1062) {
        return true;
      } else {
        throw e;
      }
    }
    return false;
  }
}
