package com.bungoh.npcstatistics;


import com.bungoh.npcstatistics.commands.CreateNPC;
import com.bungoh.npcstatistics.commands.RemoveNPC;
import com.bungoh.npcstatistics.core.StatisticManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public final class NPCStatistics extends JavaPlugin {

    private static NPCStatistics instance;
    public static NPCRegistry npcRegistry;
    public static StatisticManager statManager;

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("Citizens") == null) {
            getLogger().warning("Citizens is required for this plugin! Disabling the plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            // Create instance
            instance = this;
            // Register Citizens
            npcRegistry = CitizensAPI.getNPCRegistry();
            // Register Commands
            getCommand("createnpc").setExecutor(new CreateNPC());
            getCommand("removenpc").setExecutor(new RemoveNPC());

            //Create Statistic Manager
            new BukkitRunnable() {
                @Override
                public void run() {
                    statManager = StatisticManager.getInstance();
                    getLogger().log(Level.INFO, "Stat Manager has been enabled for NPCStats.");
                }
            }.runTaskLaterAsynchronously(this, 50L);

        }
    }

    public static NPCStatistics getInstance() {
        return instance;
    }

}
