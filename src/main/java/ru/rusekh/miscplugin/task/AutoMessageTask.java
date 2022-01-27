package ru.rusekh.miscplugin.task;

import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.rusekh.miscplugin.ToolsPlugin;
import ru.rusekh.miscplugin.data.chat.ChatMessageType;
import ru.rusekh.miscplugin.util.ChatUtil;

public class AutoMessageTask implements Runnable
{
  private final AtomicInteger atomicInteger = new AtomicInteger(0);
  private final ToolsPlugin toolsPlugin;

  public AutoMessageTask(ToolsPlugin toolsPlugin) {
    this.toolsPlugin = toolsPlugin;
  }

  @Override
  public void run() {
    if (atomicInteger.get() >= toolsPlugin.getConfig().getStringList("messages.automsg").size()) {
      atomicInteger.set(0);
    }
    for (Player player : Bukkit.getOnlinePlayers()) {
      ChatUtil.sendMessage(player, ChatMessageType.AUTO_MESSAGES, toolsPlugin.getConfig().getStringList("messages.automsg").get(atomicInteger.getAndIncrement()));
    }
  }
}
