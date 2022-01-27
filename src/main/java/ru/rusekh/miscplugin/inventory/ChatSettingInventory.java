package ru.rusekh.miscplugin.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.rusekh.miscplugin.data.chat.ChatMessageType;
import ru.rusekh.miscplugin.data.chat.SettingDataModel;
import ru.rusekh.miscplugin.data.chat.SettingManager;
import ru.rusekh.miscplugin.util.ChatUtil;
import ru.rusekh.miscplugin.util.ItemBuilder;

public class ChatSettingInventory implements ClickableInventory
{
  private final SettingManager settingManager;
  private final SettingDataModel dataModel;

  public ChatSettingInventory(SettingManager settingManager, SettingDataModel dataModel) {
    this.settingManager = settingManager;
    this.dataModel = dataModel;
  }

  public void openGui(Player player) {
    Inventory inventory = Bukkit.createInventory(this, 27, "Ustawienia chatu");

    ItemStack chatMessages = new ItemBuilder(Material.INK_SACK, 1, (short)(dataModel.is(
        ChatMessageType.CHAT_MESSAGES) ? 10 : 1))
        .setName("&aWiadomości na chacie")
        .setLore("   &a* &fKliknij aby zmienić status wiadomości od graczy &a*", "  &a* &fWiadomości te mogą zaśmiecać twój chat &a", "          &c* Wiadomości od administracji będą widoczne *")
        .build();
    ItemStack autoMessages = new ItemBuilder(Material.INK_SACK, 1, (short)(dataModel.is(
        ChatMessageType.AUTO_MESSAGES) ? 10 : 1))
        .setName("&aAutomatyczne wiadomości")
        .setLore("   &a* &fKliknij aby zmienić status automatycznych wiadomości &a*", "  &a* &fWiadomości te mogą zaśmiecać twój chat &a")
        .build();
    ItemStack msgMessages = new ItemBuilder(Material.INK_SACK, 1, (short)(dataModel.is(
        ChatMessageType.MSG_MESSAGES) ? 10 : 1))
        .setName("&aPrywatne wiadomości")
        .setLore("   &a* &fKliknij aby zmienić status prywatnych wiadomosci &a*", "  &a* &fWiadomości te mogą zaśmiecać twój chat &a")
        .build();
    ItemStack banMessages = new ItemBuilder(Material.INK_SACK, 1, (short)(dataModel.is(
        ChatMessageType.BAN_MESSAGES) ? 10 : 1))
        .setName("&aWiadomości o banach")
        .setLore("   &a* &fKliknij aby zmienić status wiadomości o banach &a*", "  &a* &fWiadomości te mogą zaśmiecać twój chat &a")
        .build();
    ItemStack caseMessages = new ItemBuilder(Material.INK_SACK, 1, (short)(dataModel.is(
        ChatMessageType.CASE_MESSAGES) ? 10 : 1))
        .setName("&aWiadomości z skrzynek")
        .setLore("   &a* &fKliknij aby zmienić status wiadomości ze skrzynek &a*", "  &a* &fWiadomości te mogą zaśmiecać twój chat &a")
        .build();

    for (int i = 0; i < 27; i++) {
      inventory.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15));
    }
    inventory.setItem(11, chatMessages);
    inventory.setItem(12, autoMessages);
    inventory.setItem(13, msgMessages);
    inventory.setItem(14, banMessages);
    inventory.setItem(15, caseMessages);

    player.openInventory(inventory);
  }

  @Override
  public void handleClick(InventoryClickEvent event) {
    switch (event.getSlot()) {
      case 11 -> {
        if (dataModel.toggle(ChatMessageType.CHAT_MESSAGES)) {
          ChatUtil.sendMessage(event.getWhoClicked(), "&aWłączyłeś wiadomości na chacie!");
        } else {
          ChatUtil.sendMessage(event.getWhoClicked(), "&cWyłączyłeś wiadomości na chacie");
        }
        settingManager.updateSettings(dataModel).whenComplete(
            (unused, throwable1) -> {
              if (throwable1 != null) {
                throwable1.printStackTrace();
                event.getWhoClicked().sendMessage(ChatColor.RED + "jednak nie, bo sie cos wyjebalo");
              }
            });
      }
      case 12 -> {
        if (dataModel.toggle(ChatMessageType.AUTO_MESSAGES)) {
          ChatUtil.sendMessage(event.getWhoClicked(), "&aWłączyłeś automatyczne wiadomości na chacie!");
        } else {
          ChatUtil.sendMessage(event.getWhoClicked(), "&cWyłączyłeś automatyczne wiadomości na chacie");
        }
        settingManager.updateSettings(dataModel).whenComplete(
            (unused, throwable1) -> {
              if (throwable1 != null) {
                throwable1.printStackTrace();
                event.getWhoClicked().sendMessage(ChatColor.RED + "jednak nie, bo sie cos wyjebalo");
              }
            });
      }
      case 13 -> {
        if (dataModel.toggle(ChatMessageType.MSG_MESSAGES)) {
          ChatUtil.sendMessage(event.getWhoClicked(), "&aWłączyłeś prywatne wiadomości!");
        } else {
          ChatUtil.sendMessage(event.getWhoClicked(), "&cWyłączyłeś prywatne wiadomości");
        }
        settingManager.updateSettings(dataModel).whenComplete(
            (unused, throwable1) -> {
              if (throwable1 != null) {
                throwable1.printStackTrace();
                event.getWhoClicked().sendMessage(ChatColor.RED + "jednak nie, bo sie cos wyjebalo");
              }
            });
      }
      case 14 -> {
        if (dataModel.toggle(ChatMessageType.BAN_MESSAGES)) {
          ChatUtil.sendMessage(event.getWhoClicked(), "&aWłączyłeś wiadomości o banach");
        } else {
          ChatUtil.sendMessage(event.getWhoClicked(), "&cWyłączyłeś wiadomości o banach");
        }
        settingManager.updateSettings(dataModel).whenComplete(
            (unused, throwable1) -> {
              if (throwable1 != null) {
                throwable1.printStackTrace();
                event.getWhoClicked().sendMessage(ChatColor.RED + "jednak nie, bo sie cos wyjebalo");
              }
            });
      }
      case 15 -> {
        if (dataModel.toggle(ChatMessageType.CASE_MESSAGES)) {
          ChatUtil.sendMessage(event.getWhoClicked(), "&aWłączyłeś wiadomości ze skrzynek");
        } else {
          ChatUtil.sendMessage(event.getWhoClicked(), "&cWyłączyłes wiadomości ze skrzynek");
        }
        settingManager.updateSettings(dataModel).whenComplete(
            (unused, throwable1) -> {
              if (throwable1 != null) {
                throwable1.printStackTrace();
                event.getWhoClicked().sendMessage(ChatColor.RED + "jednak nie, bo sie cos wyjebalo");
              }
            });
      }
      default -> {
        event.getWhoClicked().closeInventory();
        settingManager.updateSettings(dataModel).whenComplete(
            (unused, throwable1) -> {
              if (throwable1 != null) {
                throwable1.printStackTrace();
                event.getWhoClicked().sendMessage(ChatColor.RED + "jednak nie, bo sie cos wyjebalo");
              }
            });
      }
    }
  }

  @Override
  public Inventory getInventory() {
    return ClickableInventory.super.getInventory();
  }
}
