package it.forgottenworld.echelonapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;

public class FWEchelon {

    /**
     * Convenience method for retrieving the FWEchelonPlugin's API
     *
     * @return the plugin's API or null if the plugin is not loaded
     */
    @Nullable
    public static FWEchelonApi getApi() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("FWEchelon");
        return plugin instanceof FWEchelonApi ? (FWEchelonApi) plugin : null;
    }

}