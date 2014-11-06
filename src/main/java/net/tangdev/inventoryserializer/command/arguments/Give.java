package net.tangdev.inventoryserializer.command.arguments;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.tangdev.inventoryserializer.command.InventorySerializerCmd;
import net.tangdev.inventoryserializer.util.KitsManager;

@SuppressWarnings("deprecation")
public class Give implements InventorySerializerCmd.CommandHandler {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length > 1) {
				if(args.length > 2) {
					Player target = Bukkit.getPlayer(args[2]);
					if(target == null) {
						player.sendMessage(ChatColor.RED + args[2] + " is offline.");
						return;
					}
					KitsManager.getInstance().giveKit(target, args[1]);
				} else {
					KitsManager.getInstance().giveKit(player, args[1]);
				}
			} else {
				sender.sendMessage(ChatColor.RED + "/kit give <kit name> [player]");
			}
		}
	}
}