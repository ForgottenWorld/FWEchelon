package it.forgottenworld.echelonapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

public interface FWEchelon {

    /**
     * Convenience method for retrieving the FWEchelonPlugin's API
     *
     * @return the plugin's API or null if the plugin is not loaded
     */
    @Nullable
    static FWEchelonApi getApi() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("FWEchelon");
        return plugin instanceof FWEchelonApi ? (FWEchelonApi) plugin : null;
    }

}