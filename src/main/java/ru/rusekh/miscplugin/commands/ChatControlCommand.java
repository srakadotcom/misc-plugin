package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.data.chat.SettingDataModel;
import ru.rusekh.miscplugin.data.chat.SettingManager;
import ru.rusekh.miscplugin.inventory.ChatSettingInventory;

@CommandAlias("chatcontrol|cc")
public class ChatControlCommand extends BaseCommand {

  private final SettingManager manager;

  public ChatControlCommand(SettingManager manager) {
    this.manager = manager;
  }

  @Default
  public void onCommand(Player player) {
    manager.querySingleDataModel(player.getUniqueId()).whenComplete(
        (settingDataModel, throwable) -> {
          if (throwable != null) {
            throwable.printStackTrace();
            player.sendMessage(ChatColor.RED + "Wystapil blad przy pobieraniu danych: "
                + throwable.getMessage());
            return;
          }

          if (settingDataModel == null) {
            settingDataModel = new SettingDataModel(player.getUniqueId());
          }

          new ChatSettingInventory(manager, settingDataModel).openGui(player);
        });
  }
}
