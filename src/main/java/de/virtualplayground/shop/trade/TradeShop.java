package de.virtualplayground.shop.trade;

import de.virtualplayground.shop.gui.ShopGui;
import lombok.Getter;
import java.util.HashSet;

@Getter
public class TradeShop {

    private final String name;
    private final HashSet<Trade> trades;
    private final ShopGui gui;

    public TradeShop(String name) {
        this.name = name;
        this.trades = new HashSet<>();
        this.gui = new ShopGui(this);
    }

}
