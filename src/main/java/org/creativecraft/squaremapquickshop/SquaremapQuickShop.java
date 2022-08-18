package org.creativecraft.squaremapquickshop;

import org.bstats.bukkit.MetricsLite;
import org.creativecraft.squaremapquickshop.config.SettingsConfig;
import org.creativecraft.squaremapquickshop.data.Icons;
import org.creativecraft.squaremapquickshop.hook.QuickShopHook;
import org.creativecraft.squaremapquickshop.hook.SquaremapHook;
import org.bukkit.plugin.java.JavaPlugin;
import org.maxgamer.quickshop.QuickShop;
import org.maxgamer.quickshop.api.QuickShopAPI;

public class SquaremapQuickShop extends JavaPlugin {
    public static SquaremapQuickShop plugin;
    private SettingsConfig settingsConfig;
    private SquaremapHook squaremapHook;
    private QuickShopAPI shop;
    private QuickShopHook quickShopHook;
    private Icons icons;

    @Override
    public void onEnable() {
        plugin = this;
        shop = QuickShop.getInstance();

        registerSettings();
        registerData();
        registerHooks();

        new MetricsLite(this, 16171);
    }

    @Override
    public void onDisable() {
        if (squaremapHook != null) {
            squaremapHook.disable();
        }
    }

    /**
     * Register the plugin hooks.
     */
    public void registerHooks() {
        squaremapHook = new SquaremapHook(this);
        quickShopHook = new QuickShopHook(this);
    }

    /**
     * Register the plugin config.
     */
    public void registerSettings() {
        settingsConfig = new SettingsConfig(this);
    }

    /**
     * Register the plugin data.
     */
    public void registerData() {
        icons = new Icons(this);
    }

    /**
     * Retrieve the plugin config.
     *
     * @return Config
     */
    public SettingsConfig getSettings() {
        return settingsConfig;
    }

    /**
     * Retrieve the QuickShop API instance.
     *
     * @return Shop
     */
    public QuickShopAPI getShop() {
        return shop;
    }

    /**
     * Retrieve the QuickShop hook instance.
     *
     * @return ShopHook
     */
    public QuickShopHook getShopHook() {
        return quickShopHook;
    }

    /**
     * Retrieve the Icons instance.
     *
     * @return Icons
     */
    public Icons getIcons() {
        return icons;
    }
}
