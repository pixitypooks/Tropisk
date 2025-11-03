package net.tropixmc.tropisk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.plugin.java.JavaPlugin;

public class Tropisk extends JavaPlugin {
    private static Tropisk instance;
    private SkriptAddon addon;

    @Override
    public void onEnable() {
        instance = this;
        
        try {
            addon = Skript.registerAddon(this);
            addon.loadClasses("net.tropixmc.tropisk.elements");
            getLogger().info("Addon has been enabled!");
        } catch (Exception e) {
            getLogger().error("Error loading addon: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Addon has been disabled!");
    }

    public static Tropisk getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }
}