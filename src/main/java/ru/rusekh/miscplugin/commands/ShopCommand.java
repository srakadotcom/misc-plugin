package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.inventory.ShopInventory;

@CommandAlias("shop|sklep")
public class ShopCommand extends BaseCommand
{
  @Default
  public void onExecute(@Optional CommandSender sender) {
    Player player = (Player) sender;
    ShopInventory.openInventory(player);
  }
}
