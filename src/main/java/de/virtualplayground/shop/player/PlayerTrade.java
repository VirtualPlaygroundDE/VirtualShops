package de.virtualplayground.shop.player;

import org.bukkit.inventory.ItemStack;

public record PlayerTrade(ItemStack result, ItemStack... ingredients) {

}
