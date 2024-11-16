package de.virtualplayground.shop.config;

import de.virtualplayground.lib.config.ConfigHandler;
import de.virtualplayground.shop.trade.Trade;
import de.virtualplayground.shop.gui.ShopGui;
import de.virtualplayground.shop.trade.TradeShop;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

@Getter
public class ShopConfig extends ConfigHandler {

    private final HashMap<String, TradeShop> shops = new HashMap<>();

    public ShopConfig(@NotNull JavaPlugin plugin) {
        super(plugin, "shops");
    }

    @Override
    public void onLoad(FileConfiguration config) {

        // Clear Cache
        this.shops.clear();

        // Load Shops
        config.getKeys(false).forEach(name -> {

            ConfigurationSection section = config.getConfigurationSection(name);

            if (section != null) {

                TradeShop shop = new TradeShop(name);

                for (String key : section.getKeys(false)) {
                    ItemStack result = section.getItemStack(key + ".result");
                    ItemStack[] ingredients = section.getList(key + ".ingredients").toArray(new ItemStack[0]);
                    shop.getTrades().add(new Trade(result, ingredients));
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
            for (Trade trade : shop.getTrades()) {
                tradeCount++;
                config.set(name + "." + tradeCount + ".result", trade.result());
                config.set(name + "." + tradeCount + ".ingredients", trade.ingredients());
            }
        });
    }
}
