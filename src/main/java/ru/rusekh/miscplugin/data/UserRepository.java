package ru.rusekh.miscplugin.data;

import java.sql.Connection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.plugin.Plugin;
import pl.memexurer.srakadb.sql.table.DatabaseTable;
import pl.memexurer.srakadb.sql.table.query.DatabaseFetchQuery;
import pl.memexurer.srakadb.sql.table.query.DatabaseInsertQuery;
import pl.memexurer.srakadb.sql.table.query.DatabaseInsertQuery.UpdateType;

public class UserRepository {

  private final DatabaseTable<UserDataModel> databaseTable = new DatabaseTable<>("cqdVBhPp_xk",
      UserDataModel.class);
  private final Plugin plugin;

  public UserRepository(Plugin plugin, Connection connection) {
    this.plugin = plugin;
    this.databaseTable.initializeTable(connection);
  }

  public CompletableFuture<UserDataModel> fetchOrCreateModel(UUID uuid) {
    CompletableFuture<UserDataModel> future = new CompletableFuture<>();
    plugin.getServer().getScheduler().runTask(plugin, () -> {
      try {
        UserDataModel dataModel = fetchDataModel(uuid);
        future.complete(dataModel == null ? new UserDataModel(uuid) : dataModel);
      } catch (Exception ex) {
        future.completeExceptionally(ex);
      }
    });
    return future;
  }

  public CompletableFuture<Void> updateDataModelAsync(UserDataModel dataModel) {
    CompletableFuture<Void> future = new CompletableFuture<>();
    plugin.getServer().getScheduler().runTask(plugin, () -> {
      try {
        updateDataModel(dataModel);
        future.complete(null);
      } catch (Exception ex) {
        ex.printStackTrace();
        future.completeExceptionally(ex);
      }
    });
    return future;
  }

  private UserDataModel fetchDataModel(UUID uuid) {
    try (var transaction = new DatabaseFetchQuery()
        .precondition(databaseTable.getModelMapper().createQueryPair("uuid", uuid))
        .executeFetchQuery(databaseTable)) {
      return transaction.readResult();
    }
  }

  private void updateDataModel(UserDataModel dataModel) {
    new DatabaseInsertQuery(UpdateType.REPLACE)
        .values(databaseTable.getModelMapper().createQueryPairs(dataModel))
        .execute(databaseTable).close();
  }
}
