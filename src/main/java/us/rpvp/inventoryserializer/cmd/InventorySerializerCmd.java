package us.rpvp.inventoryserializer.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import us.rpvp.inventoryserializer.cmd.args.Create;
import us.rpvp.inventoryserializer.cmd.args.Give;
import us.rpvp.inventoryserializer.cmd.args.Remove;
import us.rpvp.inventoryserializer.cmd.args.Update;

import java.util.*;

public class InventorySerializerCmd implements CommandExecutor {

	Map<String, CommandHandler> cmdMap = new HashMap<>();

	public interface CommandHandler {
		public void execute(CommandSender sender, String[] args);
	}

	public InventorySerializerCmd() {
		cmdMap.put("give", new Give());
		cmdMap.put("create", new Create());
		cmdMap.put("update", new Update());
		cmdMap.put("remove", new Remove());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("kit") && sender.isOp()) {
			if(args.length > 0) {
				if(cmdMap.containsKey(args[0].toLowerCase())) {
					cmdMap.get(args[0].toLowerCase()).execute(sender, args);
				} else {
					sender.sendMessage(ChatColor.RED + "Not a valid argument. Try the following:");
					sender.sendMessage(ChatColor.AQUA + getAllArguments());
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Not enough arguments. Try the following arguments:");
				sender.sendMessage(ChatColor.AQUA + getAllArguments());
			}
			return true;
		}
		return false;
	}

	public String getAllArguments() {
		StringBuilder stringBuilder = new StringBuilder();
		SortedSet<String> sortedSet = new TreeSet<>(cmdMap.keySet());
		for(String command : sortedSet) {
			stringBuilder.append(command);
			stringBuilder.append(", ");
		}
		return stringBuilder.substring(0, stringBuilder.length() - 2);
	}
}
