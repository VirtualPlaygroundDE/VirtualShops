package de.virtualplayground.shop.gui;

import de.virtualplayground.lib.gui.Gui;
import de.virtualplayground.lib.gui.GuiIcon;
import de.virtualplayground.lib.lang.Lang;
import de.virtualplayground.shop.VirtualShops;
import de.virtualplayground.shop.trade.Trade;
import de.virtualplayground.shop.trade.TradeShop;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class EditGui extends Gui {

    private final VirtualShops plugin;
    private final TradeShop shop;

    public EditGui(TradeShop shop, VirtualShops plugin) {
        super(Component.text(shop.getName()), 3);
        this.shop = shop;
        this.plugin = plugin;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        int slot = 0;
        for (Trade trade : shop.getTrades()) {
            setItem(slot, new GuiIcon(trade.result()).setFixed(false));
            int offset = slot + 9;
            for (ItemStack ingredient : trade.ingredients()) {
                setItem(offset, new GuiIcon(ingredient).setFixed(false));
                offset += 9;
            }
            slot++;
        }
        update();
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

        shop.getTrades().clear();

        for (int slot = 0; slot < 9; slot++) {

            ItemStack result = event.getInventory().getItem(slot);
            ItemStack ingredient1 = event.getInventory().getItem(slot + 9);
            ItemStack ingredient2 = event.getInventory().getItem(slot + 18);

            if (result != null && ingredient1 != null && !ingredient1.getType().equals(Material.AIR)) {

                Trade trade;

                if (ingredient2 != null && !ingredient2.getType().equals(Material.AIR)) {
                    trade = new Trade(result, ingredient1, ingredient2);
                } else {
                    trade = new Trade(result, ingredient1);
                }

                shop.getTrades().add(trade);
            }
        }

        plugin.getShopConfig().save();
        plugin.getShopConfig().reload();
        event.getPlayer().sendMessage(Lang.parse("Shop <yellow>" + shop.getName() + " <gray>gespeichert."));
    }

}
