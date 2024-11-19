package de.virtualplayground.shop.player.gui;

import de.virtualplayground.lib.gui.Gui;
import de.virtualplayground.lib.gui.GuiIcon;
import de.virtualplayground.lib.lang.Lang;
import de.virtualplayground.shop.VirtualShops;
import de.virtualplayground.shop.player.PlayerTrade;
import de.virtualplayground.shop.player.PlayerShop;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEditGui extends Gui {

    private final VirtualShops plugin;
    private final PlayerShop shop;

    public PlayerEditGui(PlayerShop shop, VirtualShops plugin) {
        super(Component.text(shop.getName()), 3);
        this.shop = shop;
        this.plugin = plugin;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        int slot = 0;
        for (PlayerTrade playerTrade : shop.getPlayerTrades()) {
            setItem(slot, new GuiIcon(playerTrade.result()).setFixed(false));
            int offset = slot + 9;
            for (ItemStack ingredient : playerTrade.ingredients()) {
                setItem(offset, new GuiIcon(ingredient).setFixed(false));
                offset += 9;
            }
            slot++;
        }
        update();
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

        shop.getPlayerTrades().clear();

        for (int slot = 0; slot < 9; slot++) {

            ItemStack result = event.getInventory().getItem(slot);
            ItemStack ingredient1 = event.getInventory().getItem(slot + 9);
            ItemStack ingredient2 = event.getInventory().getItem(slot + 18);

            if (result != null && ingredient1 != null && !ingredient1.getType().equals(Material.AIR)) {

                PlayerTrade playerTrade;

                if (ingredient2 != null && !ingredient2.getType().equals(Material.AIR)) {
                    playerTrade = new PlayerTrade(result, ingredient1, ingredient2);
                } else {
                    playerTrade = new PlayerTrade(result, ingredient1);
                }

                shop.getPlayerTrades().add(playerTrade);
            }
        }

        plugin.getPlayerShopConfig().save();
        plugin.getPlayerShopConfig().reload();
        event.getPlayer().sendMessage(Lang.parse("Shop <yellow>" + shop.getName() + " <gray>gespeichert."));
    }

}
