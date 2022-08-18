package org.creativecraft.squaremapquickshop.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.creativecraft.squaremapquickshop.SquaremapQuickShop;
import xyz.jpenilla.squaremap.api.Key;
import xyz.jpenilla.squaremap.api.SquaremapProvider;

public final class Icons {
    private final SquaremapQuickShop plugin;
    private final Key chest;

    public Icons(SquaremapQuickShop plugin) {
        this.plugin = plugin;
        this.chest = Key.of("chest");
        register();
    }

    /**
     * Register the icon(s).
     */
    private void register() {
        String filename = "icons" + File.separator + "chest.png";
        File file = new File(plugin.getDataFolder(), filename);

        if (!file.exists()) {
            plugin.saveResource(filename, false);
        }

        try {
            BufferedImage image = ImageIO.read(file);
            SquaremapProvider.get().iconRegistry().register(chest, image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to register chest icon.", e);
        }
    }

    /**
     * Retrieve the icon key.
     *
     * @return Key
     */
    public Key getIcon() {
        return chest;
    }

    /**
     * Retrieve the icon size.
     *
     * @return int
     */
    public int getSize() {
        return plugin.getSettings().getConfig().getInt("settings.icon.size");
    }
}
