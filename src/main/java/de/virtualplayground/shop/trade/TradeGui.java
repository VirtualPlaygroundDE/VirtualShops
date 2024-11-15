package de.virtualplayground.shop.trade;

import de.virtualplayground.lib.gui.VillagerGui;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@Getter
public class TradeGui extends VillagerGui {

    private final TradeShop shop;

    public TradeGui(TradeShop shop) {
        super(Component.text(shop.getName()));
        this.shop = shop;
        create();
    }

    @Override
    protected void create() {
        shop.getTrades().forEach(trade -> {
            addTrade(trade.result(), trade.ingredients());
        });
    }
}
