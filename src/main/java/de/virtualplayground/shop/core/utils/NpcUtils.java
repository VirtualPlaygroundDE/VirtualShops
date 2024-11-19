package de.virtualplayground.shop.core.utils;

import de.oliver.fancynpcs.api.FancyNpcsPlugin;
import de.oliver.fancynpcs.api.Npc;
import de.oliver.fancynpcs.api.NpcData;
import de.oliver.fancynpcs.api.utils.SkinFetcher;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.ExecutionException;

public class NpcUtils {

    public static Npc create(String name, Player player, Location location) throws ExecutionException, InterruptedException {

        NpcData data = new NpcData(name, player.getUniqueId(), location);

        data.setSkin(SkinFetcher.fetchSkinByURL("https://s.namemc.com/i/069f740e11523d1d.png").get());
        data.setDisplayName("<gold>Shop");

        Npc npc = FancyNpcsPlugin.get().getNpcAdapter().apply(data);
        FancyNpcsPlugin.get().getNpcManager().registerNpc(npc);

        npc.create();
        npc.spawnForAll();

        return npc;
    }

    public static void remove(String name) {

        Npc npc = FancyNpcsPlugin.get().getNpcManager().getNpc(name);
        npc.removeForAll();

        FancyNpcsPlugin.get().getNpcManager().removeNpc(npc);
    }

}
