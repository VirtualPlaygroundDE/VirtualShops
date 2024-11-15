package de.virtualplayground.shop.trade;

import org.bukkit.inventory.ItemStack;

public record Trade(ItemStack result, ItemStack... ingredients) {

}
