package de.virtualplayground.shop.player.config;

import de.virtualplayground.lib.config.ConfigHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class PlayerConfig extends ConfigHandler {

    public PlayerConfig(@NotNull JavaPlugin plugin) {
        super(plugin, "players/players");
    }

    @Override
    public void onLoad(FileConfiguration config) {

    }
}
