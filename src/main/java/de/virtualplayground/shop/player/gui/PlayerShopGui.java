package de.virtualplayground.shop.player.gui;

import de.virtualplayground.lib.gui.VillagerGui;
import de.virtualplayground.shop.player.PlayerShop;
import net.kyori.adventure.text.Component;

public class PlayerShopGui extends VillagerGui {

    private final PlayerShop shop;

    public PlayerShopGui(PlayerShop shop) {
        super(Component.text(shop.getName()));
        this.shop = shop;
    }

    @Override
    public void create() {
        shop.getPlayerTrades().forEach(trade -> {
            addTrade(trade.result(), trade.ingredients());
        });
    }

}
