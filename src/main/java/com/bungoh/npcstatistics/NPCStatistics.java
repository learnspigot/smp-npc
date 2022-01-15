package com.bungoh.npcstatistics;


import com.bungoh.npcstatistics.commands.CreateNPC;
import com.bungoh.npcstatistics.commands.CreateNPCCommand;
import com.bungoh.npcstatistics.commands.RemoveNPC;
import com.bungoh.npcstatistics.utils.StatisticManager;
import com.bungoh.npcstatistics.utils.commandframework.CommandFramework;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public final class NPCStatistics extends JavaPlugin {

    private static NPCStatistics instance;

    // Command Framework
    private CommandFramework cmdFramework;

    // Citizens
    public static NPCRegistry npcRegistry;

    // PlaceholderAPI
    public static StatisticManager statManager;

    @Override
    public void onEnable() {
        // Check if Citizens, HolographicDisplays & PlaceholderAPI are all installed.
        if (Bukkit.getPluginManager().isPluginEnabled("Citizens") ||
        Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays") ||
        Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getLogger().warning("A dependency is missing! NPCStatistics has been disabled.");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            // Create Plugin Instance
            instance = this;

            // Register Citizens
            npcRegistry = CitizensAPI.getNPCRegistry();

            // Register Command Framework
            cmdFramework = new CommandFramework(this);
            cmdFramework.registerCommands(this);

            registerCommands();

            // Create Statistic Manager
            new BukkitRunnable() {
                @Override
                public void run() {
                    statManager = StatisticManager.getInstance();
                    getLogger().log(Level.INFO, "Stat Manager has been enabled for NPCStats.");
                }
            }.runTaskLaterAsynchronously(this, 50L);
        }
    }

    private void registerCommands() {
        CommandFramework.registerParser(GameMode.class, GameMode::valueOf);

        new CreateNPCCommand(cmdFramework);
    }

    public static NPCStatistics getInstance() {
        return instance;
    }

}
