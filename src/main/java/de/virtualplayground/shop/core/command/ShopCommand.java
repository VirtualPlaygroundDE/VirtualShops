package de.virtualplayground.shop.core.command;

import de.virtualplayground.lib.command.BaseCommand;
import de.virtualplayground.lib.lang.Lang;
import de.virtualplayground.shop.VirtualShops;
import de.virtualplayground.shop.core.utils.NpcUtils;
import de.virtualplayground.shop.player.gui.PlayerEditGui;
import de.virtualplayground.shop.player.PlayerTrade;
import de.virtualplayground.shop.player.PlayerShop;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ExecutionException;

public class ShopCommand extends BaseCommand {

    public ShopCommand(VirtualShops plugin) {

        super("shop");

        command.withSubcommands(

                new CommandAPICommand("create")
                        .withPermission(getPermission() + ".create")
                        .withArguments(new StringArgument("name"))
                        .executesPlayer((player, args) -> {
                            String name = (String) args.get("name");
                            if (!plugin.getPlayerShopConfig().getShops().containsKey(name)) {

                                PlayerShop shop = new PlayerShop(name);

                                // Create Default Trades
                                shop.getPlayerTrades().add(new PlayerTrade(new ItemStack(Material.STONE), new ItemStack(Material.EMERALD)));
                                shop.getPlayerTrades().add(new PlayerTrade(new ItemStack(Material.OAK_LOG), new ItemStack(Material.EMERALD), new ItemStack(Material.EMERALD)));

                                plugin.getPlayerShopConfig().getShops().put(name, shop);
                                plugin.getPlayerShopConfig().save();
                                plugin.getPlayerShopConfig().reload();

                                try {
                                    NpcUtils.create(name, player, player.getLocation());
                                } catch (ExecutionException | InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                player.sendMessage(Lang.parse("<gray>Shop <yellow>" + name + " <gray>wurde erstellt."));
                            } else {
                                player.sendMessage(Lang.parse("<gray>Shop <red>" + name + " <gray>existiert bereits!"));
                            }
                        }),

                new CommandAPICommand("delete")
                        .withPermission(getPermission() + ".delete")
                        .withArguments(new StringArgument("name").replaceSuggestions(ArgumentSuggestions.strings(plugin.getPlayerShopConfig().getShops().keySet())))
                        .executesPlayer((player, args) -> {
                            String name = (String) args.get("name");
                            if (plugin.getPlayerShopConfig().getShops().containsKey(name)) {

                                plugin.getPlayerShopConfig().getShops().remove(name);
                                plugin.getPlayerShopConfig().save();
                                plugin.getPlayerShopConfig().reload();

                                NpcUtils.remove(name);

                                player.sendMessage(Lang.parse("<gray>Shop <yellow>" + name + " <gray>wurde entfernt."));
                            } else {
                                player.sendMessage(Lang.parse("<gray>Shop <red>" + name + " <gray>existiert nicht!"));
                            }
                        }),

                new CommandAPICommand("edit")
                        .withPermission(getPermission() + ".edit")
                        .withArguments(new StringArgument("name").replaceSuggestions(ArgumentSuggestions.strings(plugin.getPlayerShopConfig().getShops().keySet())))
                        .executesPlayer((player, args) -> {
                            String name = (String) args.get("name");
                            if (plugin.getPlayerShopConfig().getShops().containsKey(name)) {
                                new PlayerEditGui(plugin.getPlayerShopConfig().getShops().get(name), plugin).open(player);
                            } else {
                                player.sendMessage(Lang.parse("<gray>Shop <red>" + name + " <gray>existiert nicht!"));
                            }
                        })
        );

    }


}
