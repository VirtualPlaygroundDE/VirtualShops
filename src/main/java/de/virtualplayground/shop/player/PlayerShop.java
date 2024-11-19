package de.virtualplayground.shop.player;

import de.virtualplayground.shop.player.gui.PlayerShopGui;
import lombok.Getter;
import java.util.HashSet;

@Getter
public class PlayerShop {

    private final String name;
    private final HashSet<PlayerTrade> playerTrades;
    private final PlayerShopGui gui;

    public PlayerShop(String name) {
        this.name = name;
        this.playerTrades = new HashSet<>();
        this.gui = new PlayerShopGui(this);
    }

}
