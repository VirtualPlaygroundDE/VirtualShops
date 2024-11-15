package de.virtualplayground.shop.trade;

import lombok.Getter;
import java.util.HashSet;

@Getter
public class TradeShop {

    private final String name;
    private final HashSet<Trade> trades;

    public TradeShop(String name) {
        this.name = name;
        this.trades = new HashSet<>();
    }

}
