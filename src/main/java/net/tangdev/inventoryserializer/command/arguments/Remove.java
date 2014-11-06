package net.tangdev.inventoryserializer.command.arguments;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.tangdev.inventoryserializer.command.InventorySerializerCmd;
import net.tangdev.inventoryserializer.util.KitsManager;

public class Remove implements InventorySerializerCmd.CommandHandler {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length > 0) {
				KitsManager.getInstance().removeKit(player, args[1]);
			} else {
				sender.sendMessage(ChatColor.RED + "You need to specify a kit name! /kit remove <kit name>");
			}
		}
	}
}