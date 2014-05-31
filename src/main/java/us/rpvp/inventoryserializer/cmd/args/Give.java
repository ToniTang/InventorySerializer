package us.rpvp.inventoryserializer.cmd.args;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.rpvp.inventoryserializer.cmd.InventorySerializerCmd;
import us.rpvp.inventoryserializer.util.KitsManager;

public class Give implements InventorySerializerCmd.CommandHandler {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length > 0) {
				if(args.length > 2) {
					Player target = Bukkit.getPlayer(args[1]);
					if(target == null) {
						player.sendMessage(ChatColor.RED + args[1] + " is offline.");
						return;
					}
					KitsManager.getInstance().giveKit(target, args[2]);
				} else {
					KitsManager.getInstance().giveKit(player, args[1]);
				}
			} else {
				sender.sendMessage(ChatColor.RED + "/kit give <kit name> or /kit give <player> <kit name>");
			}
		}
	}
}
