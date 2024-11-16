package de.virtualplayground.shop;

import de.virtualplayground.shop.command.ShopCommand;
import de.virtualplayground.shop.config.ShopConfig;
import de.virtualplayground.shop.listener.NpcListener;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class VirtualShops extends JavaPlugin {

    private final ShopConfig shopConfig = new ShopConfig(this);

    @Override
    public void onEnable() {

        shopConfig.init();

        registerEvents(getServer().getPluginManager());
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents(final PluginManager pluginManager) {
        pluginManager.registerEvents(new NpcListener(this), this);
    }

    private void registerCommands() {
        new ShopCommand(this).register();
    }
}
