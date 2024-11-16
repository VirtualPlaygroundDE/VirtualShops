package de.virtualplayground.shop.command;

import de.virtualplayground.lib.command.BaseCommand;
import de.virtualplayground.lib.lang.Lang;
import de.virtualplayground.shop.VirtualShops;
import de.virtualplayground.shop.gui.EditGui;
import de.virtualplayground.shop.trade.Trade;
import de.virtualplayground.shop.trade.TradeShop;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopCommand extends BaseCommand {

    public ShopCommand(VirtualShops plugin) {

        super("shop");

        command.withSubcommands(

                new CommandAPICommand("create")
                        .withPermission(getPermission() + ".create")
                        .withArguments(new StringArgument("name"))
                        .executesPlayer((player, args) -> {
                            String name = (String) args.get("name");
                            if (!plugin.getShopConfig().getShops().containsKey(name)) {

                                TradeShop shop = new TradeShop(name);

                                // Create Default Trades
                                shop.getTrades().add(new Trade(new ItemStack(Material.STONE), new ItemStack(Material.EMERALD)));
                                shop.getTrades().add(new Trade(new ItemStack(Material.OAK_LOG), new ItemStack(Material.EMERALD), new ItemStack(Material.EMERALD)));

                                plugin.getShopConfig().getShops().put(name, shop);
                                plugin.getShopConfig().save();
                                plugin.getShopConfig().reload();

                                player.sendMessage(Lang.parse("<gray>Shop <yellow>" + name + " <gray>wurde erstellt."));
                            } else {
                                player.sendMessage(Lang.parse("<gray>Shop <red>" + name + " <gray>existiert bereits!"));
                            }
                        }),

                new CommandAPICommand("delete")
                        .withPermission(getPermission() + ".delete")
                        .withArguments(new StringArgument("name").replaceSuggestions(ArgumentSuggestions.strings(plugin.getShopConfig().getShops().keySet())))
                        .executesPlayer((player, args) -> {
                            String name = (String) args.get("name");
                            if (plugin.getShopConfig().getShops().containsKey(name)) {

                                plugin.getShopConfig().getShops().remove(name);
                                plugin.getShopConfig().save();
                                plugin.getShopConfig().reload();

                                player.sendMessage(Lang.parse("<gray>Shop <yellow>" + name + " <gray>wurde entfernt."));
                            } else {
                                player.sendMessage(Lang.parse("<gray>Shop <red>" + name + " <gray>existiert nicht!"));
                            }
                        }),

                new CommandAPICommand("edit")
                        .withPermission(getPermission() + ".edit")
                        .withArguments(new StringArgument("name").replaceSuggestions(ArgumentSuggestions.strings(plugin.getShopConfig().getShops().keySet())))
                        .executesPlayer((player, args) -> {
                            String name = (String) args.get("name");
                            if (plugin.getShopConfig().getShops().containsKey(name)) {
                                new EditGui(plugin.getShopConfig().getShops().get(name), plugin).open(player);
                            } else {
                                player.sendMessage(Lang.parse("<gray>Shop <red>" + name + " <gray>existiert nicht!"));
                            }
                        })
        );

    }


}
