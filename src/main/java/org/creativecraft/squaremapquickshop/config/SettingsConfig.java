package org.creativecraft.squaremapquickshop.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.creativecraft.squaremapquickshop.SquaremapQuickShop;

import java.io.File;

public class SettingsConfig {
    private final SquaremapQuickShop plugin;
    private FileConfiguration settings;
    private File settingsFile;

    public SettingsConfig(SquaremapQuickShop plugin) {
        this.plugin = plugin;
        this.register();
    }

    /**
     * Register the settings config.
     */
    public void register() {
        settingsFile = new File(plugin.getDataFolder(), "settings.yml");

        if (!settingsFile.exists()) {
            settingsFile.getParentFile().mkdirs();
            plugin.saveResource("settings.yml", false);
        }

        settings = YamlConfiguration.loadConfiguration(settingsFile);

        setDefaults();
    }

    /**
     * Set the settings config defaults.
     */
    public void setDefaults() {
        settings.addDefault("settings.update-interval", 300);

        settings.addDefault("settings.control.label", "QuickShops");
        settings.addDefault("settings.control.show", true);
        settings.addDefault("settings.control.hide-by-default", false);

        settings.addDefault("settings.layer.z-index", 1);
        settings.addDefault("settings.layer.priority", 1);

        settings.addDefault("settings.icon.size", 10);

        settings.addDefault(
            "settings.tooltip.quickshop",
            """
                Owner: <span style="font-weight:bold;">{owner}</span><br/>
                Type: <span style="font-weight:bold;">{type}</span><br/>
                Item: <span style="font-weight:bold;">{item}</span><br />
                Price: <span style="font-weight:bold;">{price}</span><br />
                Stock: <span style="font-weight:bold;">{stock}</span><br />
                Space: <span style="font-weight:bold;">{space}</span>"""
        );

        settings.options().copyDefaults(true);

        try {
            settings.save(settingsFile);
        } catch (Exception e) {
            //
        }
    }

    /**
     * Retrieve the settings config.
     *
     * @return FileConfiguration
     */
    public FileConfiguration getConfig() {
        return settings;
    }
}
