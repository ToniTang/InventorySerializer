package net.tangdev.inventoryserializer.util;

import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import net.tangdev.inventoryserializer.InventorySerializer;

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
		String storedKitName = kitName.toUpperCase();
		String messageKitName = StringUtils.capitalize(storedKitName.toLowerCase());

		if(config.getString("kits." + storedKitName) == null) {
			player.sendMessage(ChatColor.RED + "The kit " + messageKitName + " does not exist.");
			player.sendMessage(ChatColor.RED + "Please use /kit create " + messageKitName);
			return;
		}
		PlayerInventory serializedInventory = (PlayerInventory) ItemSerialization.fromBase64(config.getString("kits." + storedKitName));

		player.getInventory().clear();
		player.getInventory().setContents(serializedInventory.getContents());
		player.getInventory().setArmorContents(serializedInventory.getArmorContents());

		player.sendMessage(ChatColor.GREEN + "You have been given the " + storedKitName + " kit.");
	}

	public void createKit(Player player, String kitName) {
		config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "kits.yml"));
		String storedKitName = kitName.toUpperCase();
		String messageKitName = StringUtils.capitalize(storedKitName.toLowerCase());

		if(config.getString("kits." + storedKitName) != null) {
			player.sendMessage(ChatColor.RED + "The kit " + messageKitName + " already exists.");
			player.sendMessage(ChatColor.RED + "Please use /kit update " + messageKitName);
			return;
		}
		String serializedInventory = ItemSerialization.toBase64(player.getInventory());

		config.createSection("kits." + storedKitName);
		config.set("kits." + storedKitName, serializedInventory);

		try {
			config.save(new File(plugin.getDataFolder(), "kits.yml"));
			player.sendMessage(ChatColor.GREEN + "You have created the kit " + messageKitName + ".");
		} catch(IOException e) {
			player.sendMessage(ChatColor.RED + "Error when trying to create kit " + messageKitName + ".");
			player.sendMessage(ChatColor.RED + "Please check console.");
			e.printStackTrace();
		}
	}

	public void updateKit(Player player, String kitName) {
		config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "kits.yml"));
		String storedKitName = kitName.toUpperCase();
		String messageKitName = StringUtils.capitalize(storedKitName.toLowerCase());

		if(config.getString("kits." + storedKitName) == null) {
			player.sendMessage(ChatColor.RED + "The kit " + messageKitName + " does not exist.");
			player.sendMessage(ChatColor.RED + "Please use /kit create " + messageKitName);
			return;
		}
		String serializedInventory = ItemSerialization.toBase64(player.getInventory());

		config.set("kits." + storedKitName, serializedInventory);

		try {
			config.save(new File(plugin.getDataFolder(), "kits.yml"));
			player.sendMessage(ChatColor.GREEN + "You have updated the kit " + messageKitName + ".");
		} catch(IOException e) {
			player.sendMessage(ChatColor.RED + "Error when trying to update kit " + messageKitName + ".");
			player.sendMessage(ChatColor.RED + "Please check console.");
			e.printStackTrace();
		}
	}

	public void removeKit(Player player, String kitName) {
		config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "kits.yml"));
		String storedKitName = kitName.toUpperCase();
		String messageKitName = StringUtils.capitalize(storedKitName.toLowerCase());

		if(config.getString("kits." + storedKitName) == null) {
			player.sendMessage(ChatColor.RED + "The kit " + messageKitName + " does not exist.");
			return;
		}
		config.set("kits." + storedKitName, null);

		try {
			config.save(new File(plugin.getDataFolder(), "kits.yml"));
			player.sendMessage(ChatColor.GREEN + "You have removed the kit " + messageKitName + ".");
		} catch(IOException e) {
			player.sendMessage(ChatColor.RED + "Error when trying to remove the kit " + messageKitName + ".");
			player.sendMessage(ChatColor.RED + "Please check console.");
			e.printStackTrace();
		}
	}

	public boolean doesKitExist(String kitName) {
		config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "kits.yml"));
		return config.getConfigurationSection("kits." + kitName.toUpperCase()) == null;
	}
}