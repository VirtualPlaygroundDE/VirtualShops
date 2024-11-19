package de.virtualplayground.shop.core.listener;

import de.virtualplayground.api.VirtualAPI;
import de.virtualplayground.shop.VirtualShops;
import de.virtualplayground.shop.core.utils.NpcUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.ExecutionException;

public class InteractListener implements Listener {

    private final VirtualShops plugin;

    public InteractListener(VirtualShops plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) throws ExecutionException, InterruptedException {
        if (e.getItem() != null && e.getItem().getType().equals(Material.VILLAGER_SPAWN_EGG) && e.getClickedBlock() != null && VirtualAPI.getInstance().getItemManager().isCustomItem(e.getItem())) {

            final Player player = e.getPlayer();
            final Location location = e.getClickedBlock().getLocation();

            e.setCancelled(true);

            player.getInventory().removeItem(e.getItem());
            location.setY(location.getY() + 1);
            NpcUtils.create("shop", player, location);
        }
    }

}
