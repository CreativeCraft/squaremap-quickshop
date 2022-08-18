package org.creativecraft.squaremapquickshop.hook;

import org.bukkit.World;
import org.creativecraft.squaremapquickshop.SquaremapQuickShop;
import org.maxgamer.quickshop.api.shop.Shop;

import java.util.List;

public class QuickShopHook {
    private final SquaremapQuickShop plugin;

    public QuickShopHook(SquaremapQuickShop plugin) {
        this.plugin = plugin;
    }

    /**
     * Retrieve the shops.
     *
     * @param  world The world.
     * @return List
     */
    public List<Shop> getShops(World world) {
        return plugin.getShop().getShopManager().getShopsInWorld(world);
    }
}
