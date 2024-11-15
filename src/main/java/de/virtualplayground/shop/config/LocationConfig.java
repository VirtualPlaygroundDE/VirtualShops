package de.virtualplayground.shop.config;

import de.virtualplayground.lib.config.ConfigHandler;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

@Getter
public class LocationConfig extends ConfigHandler {

    private final HashMap<String, Location> locations = new HashMap<>();

    public LocationConfig(@NotNull JavaPlugin plugin) {
        super(plugin, "locations");
    }

    @Override
    public void onLoad(FileConfiguration config) {
        this.locations.clear();
        for (String name : config.getKeys(false)) {
            this.locations.put(name, config.getLocation(name));
        }
    }

    @Override
    public void onPreSave(FileConfiguration config) {
        this.locations.forEach(config::set);
    }
}
