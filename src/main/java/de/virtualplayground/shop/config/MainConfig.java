package de.virtualplayground.shop.config;

import de.virtualplayground.lib.config.ConfigHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class MainConfig extends ConfigHandler {

    public MainConfig(@NotNull JavaPlugin plugin) {
        super(plugin, "config");
    }

    @Override
    public void onLoad(FileConfiguration config) {

    }

    @Override
    public void onPreSave(FileConfiguration config) {

    }
}
