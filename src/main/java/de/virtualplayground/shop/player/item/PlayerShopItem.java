package de.virtualplayground.shop.player.item;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum PlayerShopItem {

    LIFETIME(Material.VILLAGER_SPAWN_EGG, 0),
    SEVEN_DAYS(Material.VILLAGER_SPAWN_EGG, 7),
    FOURTEEN_DAYS(Material.VILLAGER_SPAWN_EGG, 14),
    THIRTEEN_DAYS(Material.VILLAGER_SPAWN_EGG, 30);

    final Material type;
    final int period;

    private PlayerShopItem(Material t, int p) {
        type = t;
        period = p;
    }

}
