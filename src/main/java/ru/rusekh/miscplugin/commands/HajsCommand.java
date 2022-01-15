package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.sexozix.cashblockminers.CashBlockPlugin;
import pl.sexozix.cashblockminers.system.data.UserDataModel;
import pl.sexozix.cashblockminers.system.data.UserHandler;
import ru.rusekh.miscplugin.ToolsPlugin;

@CommandAlias("hajs|balance|money|pieniadze|bal")
public class HajsCommand extends BaseCommand {

  private final Economy economy;
  private final UserHandler userHandler;

  public HajsCommand(ToolsPlugin plugin) {
    this.economy = plugin.getEconomy();
    this.userHandler = JavaPlugin.getPlugin(CashBlockPlugin.class).getHandler();
  }

  @Default
  public void onUse(Player sender) {
    UserDataModel dataModel = userHandler.getUserDataModel(sender);
    sender.sendMessage("Posiadasz " + economy.getBalance(sender) + "$");
    sender.sendMessage("oraz " + String.format("%.2f", dataModel.money()) + "zł");
  }
}
