package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import ru.rusekh.miscplugin.data.PlayerHomes;
import ru.rusekh.miscplugin.data.UserRepository;
import ru.rusekh.miscplugin.inventory.ClickableInventory;
import ru.rusekh.miscplugin.util.ItemBuilder;

@CommandAlias("home|domek|homes")
public class HomeCommand extends BaseCommand {

  private final UserRepository repository;

  public HomeCommand(UserRepository repository) {
    this.repository = repository;
  }

  private static int getHomeLimit(Player player) {
    if (player.hasPermission("homes.svip")) {
      return 3;
    } else if (player.hasPermission("homes.vip")) {
      return 2;
    } else {
      return 1;
    }
  }

  @Default
  public void showMenu(Player sender, @Optional Integer home) {
    repository.fetchOrCreateModel(sender.getUniqueId()).whenComplete(
        (dataModel, throwable) -> {
          if (throwable != null) {
            throwable.printStackTrace();
            sender.sendMessage(ChatColor.RED + "Error kurwo: " + throwable.getMessage());
            return;
          }

          if (home == null) {
            sender.openInventory(new HomeInventory(sender, dataModel.getHomes()).getInventory());
            return;
          }

          if (!dataModel.getHomes().hasHome(home)) {
            throw new InvalidCommandArgument("Nie znaleziono domku!", false);
          }

          sender.teleport(dataModel.getHomes().getHome(home));
          sender.sendMessage(ChatColor.GREEN + "Przeteleportowano!");
        });
  }

  @CommandAlias("sethome|ustawhome|ustawdomek|setdomek")
  @Subcommand("set|ustaw")
  public void setHome(Player sender, @Optional Integer home) {
    repository.fetchOrCreateModel(sender.getUniqueId()).whenComplete(
        (dataModel, throwable) -> {
          if (home == null || !dataModel.getHomes()
              .createHome(sender.getLocation(), getHomeLimit(sender))) {
            throw new InvalidCommandArgument(
                "Osiagnieto limit domkow! Uzyj komendy /sethome (numer) aby zmienic lokalizacje istniejacego domku.",
                false);
          }

          if (!dataModel.getHomes().setHome(sender.getLocation(), home)) {
            throw new InvalidCommandArgument("Nie znaleziono takiego domku.", false);
          }

          repository.updateDataModelAsync(dataModel).whenComplete(
              (unused, throwable1) -> {
                if (throwable1 != null) {
                  throwable1.printStackTrace();
                  sender.sendMessage(ChatColor.RED + "Error kurwo: " + throwable1.getMessage());
                } else {
                  sender.sendMessage(ChatColor.GREEN + "Pomyslnie ustawiono domek!");
                }
              });
        });
  }

  private static class HomeInventory implements ClickableInventory {

    private final Player player;
    private final PlayerHomes homes;

    private HomeInventory(Player player, PlayerHomes homes) {
      this.player = player;
      this.homes = homes;
    }

    @Override
    public Inventory getInventory() {
      Inventory inventory = Bukkit.createInventory(this, InventoryType.HOPPER, "Domki");

      int homeLimit = getHomeLimit(player);
      inventory.setItem(0, new ItemBuilder(Material.PAPER, 1)
          .setName("&7Informacje")
          .setLore("&8\u00BB &7Twoj limit domkow: &e" + getHomeLimit(player))
          .addLore(homeLimit < 2 ? null : "&7Aby zwiekszyc limit domkow do 2, zakup range &6VIP")
          .addLore(homeLimit < 3 ? null : "&7Aby zwiekszyc limit domkow do 3, zakup range &eSVIP")
          .build());
      inventory.setItem(1, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
          .setName("&7Domek 1")
          .setLore("&8\u00BB &7Ten domek " + (homes.hasHome(1) ? "&ajest" : "&cnie jest")
              + " &7ustawiony.")
          .setSkullUrl(
              homes.hasHome(1) ? "88991697469653c9af8352fdf18d0cc9c67763cfe66175c1556aed33246c7"
                  : "8d2454e4c67b323d5be953b5b3d54174aa271460374ee28410c5aeae2c11f5")
          .build());
      inventory.setItem(2, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
          .setName("&7Domek 2")
          .setLore("&8\u00BB &7Ten domek " + (homes.hasHome(2) ? "&ajest" : "&cnie jest")
              + " &7ustawiony.")
          .setSkullUrl(
              homes.hasHome(2) ? "5496c162d7c9e1bc8cf363f1bfa6f4b2ee5dec6226c228f52eb65d96a4635c" :
                  "b13b778c6e5128024214f859b4fadc7738c7be367ee4b9b8dbad7954cff3a")
          .build());
      inventory.setItem(3, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
          .setName("&7Domek 3")
          .setLore("&8\u00BB &7Ten domek " + (homes.hasHome(3) ? "&ajest" : "&cnie jest")
              + " &7ustawiony.")
          .setSkullUrl(
              homes.hasHome(3) ? "c4226f2eb64abc86b38b61d1497764cba03d178afc33b7b8023cf48b49311"
                  : "031f66be0950588598feeea7e6c6779355e57cc6de8b91a44391b2e9fd72")
          .build());
      return inventory;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
      if (event.getRawSlot() >= 1 && event.getRawSlot() <= 3) {
        if (!homes.hasHome(event.getRawSlot())) {
          event.getWhoClicked().sendMessage(ChatColor.RED + "Ten domek nie jest ustawiony!");
          event.getWhoClicked().closeInventory();
          return;
        }

        event.getWhoClicked().teleport(homes.getHome(event.getRawSlot()));
      }
    }

  }
}
