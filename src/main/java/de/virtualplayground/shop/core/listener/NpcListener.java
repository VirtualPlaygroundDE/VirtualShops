package de.virtualplayground.shop.core.listener;

import de.oliver.fancynpcs.api.events.NpcInteractEvent;
import de.virtualplayground.shop.VirtualShops;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NpcListener implements Listener {

    private final VirtualShops plugin;

    public NpcListener(VirtualShops plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onNpcInteract(NpcInteractEvent e) {
        if (plugin.getPlayerShopConfig().getShops().containsKey(e.getNpc().getData().getName())) {
            plugin.getPlayerShopConfig().getShops().get(e.getNpc().getData().getName()).getGui().open(e.getPlayer());
        }
    }

}
