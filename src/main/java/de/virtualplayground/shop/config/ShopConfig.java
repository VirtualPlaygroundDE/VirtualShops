package de.virtualplayground.shop.config;

import de.virtualplayground.api.VirtualAPI;
import de.virtualplayground.lib.config.ConfigHandler;
import de.virtualplayground.shop.trade.Trade;
import de.virtualplayground.shop.trade.TradeGui;
import de.virtualplayground.shop.trade.TradeShop;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

@Getter
public class ShopConfig extends ConfigHandler {

    private final HashMap<String, TradeGui> shops = new HashMap<>();

    public ShopConfig(@NotNull JavaPlugin plugin) {
        super(plugin, "shops");
    }

    @Override
    public void onLoad(FileConfiguration config) {


        config.getKeys(false).forEach(name -> {

            ConfigurationSection section = config.getConfigurationSection(name);

            if (section != null) {
                TradeShop shop = new TradeShop(name);

                for (String key : section.getKeys(false)) {
                    ItemStack result = section.getItemStack(key + ".result");
                    ItemStack[] ingredients = section.getList(key + ".ingredients").toArray(new ItemStack[0]);
                    shop.getTrades().add(new Trade(result, ingredients));
                }

                this.shops.put(shop.getName(), new TradeGui(shop));
            }

        });
    }

    @Override
    public void onPreSave(FileConfiguration config) {
        this.shops.forEach((name, gui) -> {
            int tradeCount = 0;
            for (Trade trade : gui.getShop().getTrades()) {
                tradeCount++;
                config.set(name + "." + tradeCount + ".result", trade.result());
                config.set(name + "." + tradeCount + ".ingredients", trade.ingredients());
            }
        });
    }
}
