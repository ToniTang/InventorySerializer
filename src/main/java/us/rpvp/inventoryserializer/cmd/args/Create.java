package us.rpvp.inventoryserializer.cmd.args;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.rpvp.inventoryserializer.cmd.InventorySerializerCmd;
import us.rpvp.inventoryserializer.util.KitsManager;

public class Create implements InventorySerializerCmd.CommandHandler {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length > 0) {
				KitsManager.getInstance().createKit(player, args[1]);
			} else {
				sender.sendMessage(ChatColor.RED + "You need to specify a kit name! /kit create <kit name>");
			}
		}
	}
}
