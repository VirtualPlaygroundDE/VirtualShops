package de.virtualplayground.shop;

import de.virtualplayground.api.VirtualAPI;
import de.virtualplayground.api.item.CustomItemManager;
import de.virtualplayground.lib.item.CustomItem;
import de.virtualplayground.lib.item.ItemBuilder;
import de.virtualplayground.lib.lang.Lang;
import de.virtualplayground.shop.admin.config.AdminShopConfig;
import de.virtualplayground.shop.core.command.ShopCommand;
import de.virtualplayground.shop.core.listener.InteractListener;
import de.virtualplayground.shop.player.config.PlayerConfig;
import de.virtualplayground.shop.player.config.PlayerShopConfig;
import de.virtualplayground.shop.core.listener.NpcListener;
import de.virtualplayground.shop.player.item.PlayerShopItem;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class VirtualShops extends JavaPlugin {

    private final AdminShopConfig adminShopConfig = new AdminShopConfig(this);
    private final PlayerConfig playerConfig = new PlayerConfig(this);
    private final PlayerShopConfig playerShopConfig = new PlayerShopConfig(this);

    @Override
    public void onEnable() {

        adminShopConfig.init();
        playerConfig.init();
        playerShopConfig.init();

        registerEvents(getServer().getPluginManager());
        registerCommands();

        createItems(VirtualAPI.getInstance().getItemManager());
    }

    private void registerEvents(final PluginManager pluginManager) {
        pluginManager.registerEvents(new InteractListener(this), this);
        pluginManager.registerEvents(new NpcListener(this), this);
    }

    private void registerCommands() {
        new ShopCommand(this).register();
    }

    private void createItems(final CustomItemManager itemManager) {

        for (PlayerShopItem item : PlayerShopItem.values()) {

            ItemBuilder builder = new ItemBuilder(item.getType()).setName(Lang.parse("<gold>Spieler-Shop"));

            String description1 = "<gray>Platziere diesen Item um einen NPC zu";
            String description2 = "<gray>erstellen, durch diesen du automatisiert";
            String description3 = "<gray>Items verkaufen kannst.";

            if (item.getPeriod() > 0) {
                builder.setLore(
                        Component.empty(),
                        Lang.parse("<gray>Laufzeit"),
                        Lang.parse("<yellow>" + item.getPeriod() + " Tage"),
                        Component.empty(),
                        Lang.parse(description1),
                        Lang.parse(description2),
                        Lang.parse(description3)
                );
            } else {
                builder.setLore(
                        Component.empty(),
                        Lang.parse("<gray>Laufzeit"),
                        Lang.parse("<yellow><bold>LIFETIME</bold>"),
                        Component.empty(),
                        Lang.parse(description1),
                        Lang.parse(description2),
                        Lang.parse(description3)
                );
            }

            CustomItem customItem = new CustomItem("player_shop_" + item.name().toLowerCase(), builder);
            customItem.setStackable(false);

            itemManager.register(customItem);
        }
    }
}
