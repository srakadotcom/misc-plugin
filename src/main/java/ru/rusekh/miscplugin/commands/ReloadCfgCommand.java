package ru.rusekh.miscplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.rusekh.miscplugin.ToolsPlugin;

public class ReloadCfgCommand implements CommandExecutor
{
  private final ToolsPlugin toolsPlugin;

  public ReloadCfgCommand(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    toolsPlugin.reloadConfig();
    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Przeładowałeś config!"));
    return false;
  }
}
