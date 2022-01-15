package ru.rusekh.miscplugin;

import co.aikar.commands.PaperCommandManager;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.rusekh.miscplugin.commands.ChatCommand;
import ru.rusekh.miscplugin.commands.CustomRanksCommands;
import ru.rusekh.miscplugin.commands.EnderChestCommand;
import ru.rusekh.miscplugin.commands.FlyCommand;
import ru.rusekh.miscplugin.commands.HajsCommand;
import ru.rusekh.miscplugin.commands.HatCommand;
import ru.rusekh.miscplugin.commands.HelpCommand;
import ru.rusekh.miscplugin.commands.HelpopCommand;
import ru.rusekh.miscplugin.commands.KeyCommand;
import ru.rusekh.miscplugin.commands.MessageCommand;
import ru.rusekh.miscplugin.commands.RandomTeleportCommand;
import ru.rusekh.miscplugin.commands.ReloadCfgCommand;
import ru.rusekh.miscplugin.commands.ShopCommand;
import ru.rusekh.miscplugin.commands.SpawnCommand;
import ru.rusekh.miscplugin.commands.TpaCommand;
import ru.rusekh.miscplugin.commands.TpacceptCommand;
import ru.rusekh.miscplugin.commands.VanishCommand;
import ru.rusekh.miscplugin.commands.WorkbenchCommand;
import ru.rusekh.miscplugin.commands.WyplacCommand;
import ru.rusekh.miscplugin.handler.InventoryClickListener;
import ru.rusekh.miscplugin.handler.PlayerChatHandler;
import ru.rusekh.miscplugin.handler.PlayerInteractHandler;
import ru.rusekh.miscplugin.handler.PlayerJoinHandler;
import ru.rusekh.miscplugin.manager.ShopManager;
import ru.rusekh.miscplugin.task.AutoMessageTask;

public class ToolsPlugin extends JavaPlugin
{
  private final Map<UUID, UUID> teleportMap = new HashMap<>();
  private final Map<UUID, Location> cacheMap = new HashMap<>(); //mapa od back
  private PaperCommandManager paperCommandManager;
  private static LuckPerms luckPermsApi;
  private ShopManager shopManager;
  private Economy economy;

  @Override
  public void onEnable() {
    saveDefaultConfig();

    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp != null) {
      economy = rsp.getProvider();
    }
    RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    if (provider != null) {
      luckPermsApi = provider.getProvider();
    }

    shopManager = new ShopManager(this);

    PluginManager pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(new PlayerInteractHandler(), this);
    pluginManager.registerEvents(new PlayerJoinHandler(), this);
    pluginManager.registerEvents(new PlayerChatHandler(this), this);
    pluginManager.registerEvents(new InventoryClickListener(), this);


    getCommand("reloadcfg").setExecutor(new ReloadCfgCommand(this));

    paperCommandManager = new PaperCommandManager(this);
    paperCommandManager.registerCommand(new EnderChestCommand());
    paperCommandManager.registerCommand(new VanishCommand());
    paperCommandManager.registerCommand(new FlyCommand());
    paperCommandManager.registerCommand(new RandomTeleportCommand(this));
    paperCommandManager.registerCommand(new MessageCommand());
    paperCommandManager.registerCommand(new HatCommand());
    paperCommandManager.registerCommand(new WorkbenchCommand());
    paperCommandManager.registerCommand(new TpaCommand(this));
    paperCommandManager.registerCommand(new TpacceptCommand(this));
    paperCommandManager.registerCommand(new SpawnCommand(this));
    //paperCommandManager.registerCommand(new ClearCommand()); memecore ma to
    paperCommandManager.registerCommand(new ChatCommand());
    paperCommandManager.registerCommand(new WyplacCommand());
    paperCommandManager.registerCommand(new HelpopCommand());
    paperCommandManager.registerCommand(new HelpCommand());
    paperCommandManager.registerCommand(new KeyCommand());
    paperCommandManager.registerCommand(new CustomRanksCommands(this));
    paperCommandManager.registerCommand(new ShopCommand(this));

    Bukkit.getScheduler().runTaskTimer(this, new AutoMessageTask(this), 20L, 800L);

    Logger.getLogger("misc-plugin").info("Successfully loaded a miscelanous");
  }

  @Override
  public void onDisable() {
    paperCommandManager.unregisterCommands();
  }

  public Map<UUID, UUID> getTeleportMap() {
    return teleportMap;
  }

  public Map<UUID, Location> getCacheMap() {
    return cacheMap;
  }

  public static LuckPerms getLuckPermsApi() {
    return luckPermsApi;
  }

  public ShopManager getShopManager() {
    return shopManager;
  }

  public Economy getEconomy() {
    return economy;
  }
}
