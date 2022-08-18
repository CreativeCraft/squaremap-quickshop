package org.creativecraft.squaremapquickshop.hook;

import org.bukkit.World;
import org.creativecraft.squaremapquickshop.SquaremapQuickShop;
import org.creativecraft.squaremapquickshop.task.SquaremapTask;
import xyz.jpenilla.squaremap.api.BukkitAdapter;
import xyz.jpenilla.squaremap.api.Key;
import xyz.jpenilla.squaremap.api.SquaremapProvider;
import xyz.jpenilla.squaremap.api.SimpleLayerProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SquaremapHook {
    private final Map<UUID, SquaremapTask> provider = new HashMap<>();
    private final SquaremapQuickShop plugin;

    public SquaremapHook(SquaremapQuickShop plugin) {
        this.plugin = plugin;
        hook();
    }

    /**
     * Hook into squaremap.
     */
    public void hook() {
        SquaremapProvider.get().mapWorlds().forEach(value -> {
            final World world = BukkitAdapter.bukkitWorld(value);

            SimpleLayerProvider provider = SimpleLayerProvider
                .builder(plugin.getSettings().getConfig().getString("settings.control.label", "QuickShops"))
                .showControls(plugin.getSettings().getConfig().getBoolean("settings.control.show"))
                .defaultHidden(plugin.getSettings().getConfig().getBoolean("settings.control.hide"))
                .layerPriority(plugin.getSettings().getConfig().getInt("settings.layer.priority"))
                .zIndex(plugin.getSettings().getConfig().getInt("settings.layer.z-index"))
                .build();

            value.layerRegistry().register(Key.of("quickshop_" + world.getUID()), provider);

            SquaremapTask task = new SquaremapTask(plugin, world, provider);

            task.runTaskTimer(plugin, 20L, 20L * plugin.getSettings().getConfig().getInt("settings.update-interval"));

            this.provider.put(world.getUID(), task);
        });
    }

    /**
     * Disable the squaremap hook.
     */
    public void disable() {
        provider.values().forEach(SquaremapTask::disable);
        provider.clear();

        SquaremapProvider.get().iconRegistry().unregister(
            plugin.getIcons().getIcon()
        );
    }
}
