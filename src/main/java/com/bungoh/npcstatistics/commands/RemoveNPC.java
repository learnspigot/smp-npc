package com.bungoh.npcstatistics.commands;

import com.bungoh.npcstatistics.core.BNPCRemover;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RemoveNPC implements CommandExecutor {

    private Map<Player, BNPCRemover> map;

    public RemoveNPC() {
        map = new HashMap<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (map.containsKey(player)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cYou have already in the removal process."));
            return true;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&ePunch &aan NPC to remove it. &eRight-click &aanywhere to stop."));

        map.put(player, new BNPCRemover(player, () -> map.remove(player)));
        return true;
    }

}
