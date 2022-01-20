package ru.rusekh.miscplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.data.discord.DiscordUserRepository;

public class DiscordCommand extends BaseCommand {

  private final Map<String, String> discords = new HashMap<>();
  private final DiscordUserRepository repository;

  public DiscordCommand(DiscordUserRepository repository) {
    this.repository = repository;
  }

  @Subcommand("use")
  @Syntax("")
  @CommandPermission("discord.permission")
  @Description("Rconisz?")
  public void onDiscord(CommandSender sender, String name, String id) {
    if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender) {
      repository.fetchModel(id).whenComplete((discordUser, throwable) -> {
        if (throwable != null) {
          throwable.printStackTrace();
          sender.sendMessage("\00ERROR:" + throwable.getMessage());
          return;
        }

        if (discordUser != null) {
          sender.sendMessage(
              "\00ALREADY_CONNECTED:" + discordUser.getName() + ";" + discordUser.getUuid());
          return;
        }

        discords.put(name, id);
        sender.sendMessage("\00SUCCESS:" + discords);
      });
    }
  }

  @Default
  public void onUse(Player player) {
    player.sendMessage(
        ChatColor.GRAY + "Link do discorda: discord.gg/" + ChatColor.MAGIC + "sraka");

    if (discords.containsKey(player.getName())) {
      repository.createDataModel(player, discords.remove(player.getName())).whenComplete(
          (isDuplicate, throwable) -> {
            if (throwable != null) {
              throwable.printStackTrace();
              player.sendMessage(ChatColor.RED + "Blad kurwo: " + throwable.getMessage());
              return;
            }

            if (isDuplicate) {
              player.sendMessage(ChatColor.RED + "Juz polaczyles swoje konto kurwiu!");
              return;
            }

            player.sendMessage(ChatColor.GREEN + "Pomyslnie polaczono konto!");
            //todo jakas nagroda
          });
    } else {
      player.sendMessage(ChatColor.RED + "Nie znaleziono zadnej proby polaczenia konta!");
    }
  }
}
