package de.virtualplayground.shop.gui;

import de.virtualplayground.lib.gui.VillagerGui;
import de.virtualplayground.shop.trade.TradeShop;
import net.kyori.adventure.text.Component;

public class ShopGui extends VillagerGui {

    private final TradeShop shop;

    public ShopGui(TradeShop shop) {
        super(Component.text(shop.getName()));
        this.shop = shop;
    }

    @Override
    public void create() {
        shop.getTrades().forEach(trade -> {
            addTrade(trade.result(), trade.ingredients());
        });
    }

}
