package de.virtualplayground.shop.player.config;

import de.virtualplayground.lib.config.ConfigHandler;
import de.virtualplayground.shop.player.PlayerTrade;
import de.virtualplayground.shop.player.PlayerShop;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

@Getter
public class PlayerShopConfig extends ConfigHandler {

    private final HashMap<String, PlayerShop> shops = new HashMap<>();

    public PlayerShopConfig(@NotNull JavaPlugin plugin) {
        super(plugin, "players/shops");
    }

    @Override
    public void onLoad(FileConfiguration config) {

        // Clear Cache
        this.shops.clear();

        // Load Shops
        config.getKeys(false).forEach(name -> {

            ConfigurationSection section = config.getConfigurationSection(name);

            if (section != null) {

                PlayerShop shop = new PlayerShop(name);

                for (String key : section.getKeys(false)) {
                    ItemStack result = section.getItemStack(key + ".result");
                    ItemStack[] ingredients = section.getList(key + ".ingredients").toArray(new ItemStack[0]);
                    shop.getPlayerTrades().add(new PlayerTrade(result, ingredients));
                }

                shop.getGui().create();
                this.shops.put(shop.getName(), shop);
            }

        });
    }

    @Override
    public void onPreSave(FileConfiguration config) {

        // Reset Config
        for(String key : config.getKeys(false)) {
            config.set(key, null);
        }

        // Save Shops
        this.shops.forEach((name, shop) -> {
            int tradeCount = 0;
            for (PlayerTrade playerTrade : shop.getPlayerTrades()) {
                tradeCount++;
                config.set(name + "." + tradeCount + ".result", playerTrade.result());
                config.set(name + "." + tradeCount + ".ingredients", playerTrade.ingredients());
            }
        });
    }
}
