package ru.rusekh.miscplugin.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.util.ChatUtil;

public class AutoMessageTask implements Runnable
{
  private int i = 0;
  private final ToolsPlugin toolsPlugin;

  public AutoMessageTask(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  @Override
  public void run() {
    if (i >= toolsPlugin.getConfig().getStringList("messages.automsg").size()) {
      i = 0;
    }
    for (Player player : Bukkit.getOnlinePlayers()) {
      ChatUtil.sendMessage(player, toolsPlugin.getConfig().getStringList("messages.automsg").get(i));
    }
    ++i;
  }
}
