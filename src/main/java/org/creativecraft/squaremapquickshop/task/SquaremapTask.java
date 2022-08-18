package org.creativecraft.squaremapquickshop.task;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.inventory.meta.ItemMeta;
import org.creativecraft.squaremapquickshop.SquaremapQuickShop;
import org.maxgamer.quickshop.api.shop.Shop;
import xyz.jpenilla.squaremap.api.Key;
import xyz.jpenilla.squaremap.api.Point;
import xyz.jpenilla.squaremap.api.SimpleLayerProvider;
import xyz.jpenilla.squaremap.api.marker.Icon;
import xyz.jpenilla.squaremap.api.marker.MarkerOptions;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Locale;

public class SquaremapTask extends BukkitRunnable {
    private final SimpleLayerProvider provider;
    private final SquaremapQuickShop plugin;
    private final World world;

    private boolean stop;

    public SquaremapTask(SquaremapQuickShop plugin, World world, SimpleLayerProvider provider) {
        this.plugin = plugin;
        this.world = world;
        this.provider = provider;
    }

    @Override
    public void run() {
        if (stop) {
            cancel();
        }

        updateShops();
    }

    /**
     * Update the shops.
     */
    private void updateShops() {
        provider.clearMarkers();

        List<Shop> shops = plugin.getShopHook().getShops(this.world);

        if (shops == null || shops.isEmpty()) {
            return;
        }

        shops.forEach(this::handleShop);
    }

    /**
     * Handle the shop on the map.
     *
     * @param shop The shop.
     */
    private void handleShop(Shop shop) {
        Location location = shop.getLocation();

        if (shop.isDeleted() || location.getWorld() == null) {
            return;
        }

        Icon icon = Icon.icon(
            Point.of(shop.getLocation().getBlockX(), shop.getLocation().getBlockZ()),
            plugin.getIcons().getIcon(),
            plugin.getIcons().getSize()
        );

        ItemMeta itemMeta = shop.getItem().getItemMeta();
        String itemName = itemMeta != null && itemMeta.hasDisplayName() ?
            ChatColor.stripColor(itemMeta.getDisplayName()) :
            StringUtils.capitalize(
                shop.getItem().getType().name().toLowerCase().replace("_", " ")
            );

        MarkerOptions.Builder options = MarkerOptions
            .builder()
            .clickTooltip(
                plugin.getSettings().getConfig().getString("settings.tooltip.quickshop")
                    .replace("{owner}", shop.ownerName())
                    .replace("{type}", StringUtils.capitalize(shop.getShopType().name().toLowerCase()))
                    .replace("{item}", itemName)
                    .replace("{price}", Double.toString(shop.getPrice()))
                    .replace("{stock}", Integer.toString(shop.getRemainingStock()))
                    .replace("{space}", Integer.toString(shop.getRemainingSpace()))
            );

        icon.markerOptions(options);

        String markerId = "shop_" + location.getWorld().getName() + "_" + shop.getOwner() + "_" + location.getBlockX() + "_" + location.getBlockZ();

        this.provider.addMarker(Key.of(markerId), icon);
    }

    /**
     * Disable the task.
     */
    public void disable() {
        cancel();
        this.stop = true;
        this.provider.clearMarkers();
    }
}
