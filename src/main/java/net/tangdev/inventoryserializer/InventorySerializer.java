package net.tangdev.inventoryserializer;

import org.bukkit.plugin.java.JavaPlugin;
import net.tangdev.inventoryserializer.command.InventorySerializerCmd;

public class InventorySerializer extends JavaPlugin {

	private static InventorySerializer instance;

	public void onEnable() {
		instance = this;
		getCommand("kit").setExecutor(new InventorySerializerCmd());
	}

	public void onDisable() {
		instance = null;
	}

	public static InventorySerializer getInstance() {
		return instance;
	}
}