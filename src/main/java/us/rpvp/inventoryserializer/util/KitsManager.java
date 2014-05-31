package us.rpvp.inventoryserializer.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import us.rpvp.inventoryserializer.InventorySerializer;

import java.io.File;
import java.io.IOException;

public class KitsManager {

	InventorySerializer plugin = InventorySerializer.getInstance();
	FileConfiguration config;

	private static KitsManager instance;

	public static KitsManager getInstance() {
		if(instance == null)
			instance = new KitsManager();
		return instance;
	}

	public void giveKit(Player player, String kitName) {
		config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "kits.yml"));

		if(config.getString("kits." + kitName.toUpperCase()) != null) {
			PlayerInventory serializedInventory = (PlayerInventory) ItemSerialization.fromBase64(config.getString("kits." + kitName.toUpperCase()));

			player.getInventory().clear();
			player.getInventory().setContents(serializedInventory.getContents());
			player.getInventory().setArmorContents(serializedInventory.getArmorContents());

			player.sendMessage(ChatColor.GREEN + "You have successfully been given the " + kitName.toUpperCase() + " kit!");
		} else {
			player.sendMessage(ChatColor.RED + "Kit " + kitName.toUpperCase() + " does not exist!");
			player.sendMessage(ChatColor.RED + "Please use /kit create " + kitName);
		}
	}

	public void createKit(Player player, String kitName) {
		File file = new File(plugin.getDataFolder(), "kits.yml");
		config = YamlConfiguration.loadConfiguration(file);

		if(config.getString("kits." + kitName.toUpperCase()) == null) {
			String serializedInventory = ItemSerialization.toBase64(player.getInventory());

			config.createSection("kits." + kitName.toUpperCase());
			config.set("kits." + kitName.toUpperCase(), serializedInventory);

			try {
				config.save(file);
				player.sendMessage(ChatColor.GREEN + "Successfully created the kit " + kitName.toUpperCase());
			} catch(IOException e) {
				player.sendMessage(ChatColor.RED + "Error when trying to create kit " + kitName.toUpperCase() + "!");
				player.sendMessage(ChatColor.RED + "Please check console.");
				e.printStackTrace();
			}
		} else {
			player.sendMessage(ChatColor.RED + "Kit " + kitName.toUpperCase() + " already exists!");
			player.sendMessage(ChatColor.RED + "Please use /kit update " + kitName.toUpperCase() + " instead!");
		}
	}

	public void updateKit(Player player, String kitName) {
		File file = new File(plugin.getDataFolder(), "kits.yml");
		config = YamlConfiguration.loadConfiguration(file);

		if(config.getString("kits." + kitName.toUpperCase()) != null) {
			String serializedInventory = ItemSerialization.toBase64(player.getInventory());

			config.set("kits." + kitName.toUpperCase(), serializedInventory);

			try {
				config.save(file);
				player.sendMessage(ChatColor.GREEN + "Successfully updated the kit " + kitName.toUpperCase());
			} catch(IOException e) {
				player.sendMessage(ChatColor.RED + "Error when trying to update kit " + kitName.toUpperCase() + "!");
				player.sendMessage(ChatColor.RED + "Please check console.");
				e.printStackTrace();
			}
		} else {
			player.sendMessage(ChatColor.RED + "Kit " + kitName.toUpperCase() + " does not exist!");
			player.sendMessage(ChatColor.RED + "Please use /kit create " + kitName.toUpperCase() + "instead!");
		}
	}

	public void removeKit(Player player, String kitName) {
		File file = new File(plugin.getDataFolder(), "kits.yml");
		config = YamlConfiguration.loadConfiguration(file);

		if(config.getString("kits." + kitName.toUpperCase()) != null) {
			config.set("kits." + kitName.toUpperCase(), null);
			try {
				config.save(file);
				player.sendMessage(ChatColor.GREEN + "Successfully removed the kit " + kitName.toUpperCase());
			} catch(IOException e) {
				player.sendMessage(ChatColor.RED + "Error when trying to remove the kit! Check console");
				e.printStackTrace();
			}
		} else {
			player.sendMessage(ChatColor.RED + "Kit " + kitName.toUpperCase() + " does not exist!");
		}
	}
}

