package com.bungoh.npcstatistics.commands;

import com.bungoh.npcstatistics.core.BNPC;
import com.bungoh.npcstatistics.NPCStatistics;
import com.bungoh.npcstatistics.core.StatisticManager;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateNPC implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect usage! Use &f/createnpc [kill/death]"));
            return true;
        }

        String input = args[0];

        if (!input.equalsIgnoreCase("kill") && !input.equalsIgnoreCase("death")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect usage! Use &f/createnpc [kill/death]"));
            return true;
        }

        // Create the NPC
        BNPC.NPCTypes type = (input.equalsIgnoreCase("kill")) ? BNPC.NPCTypes.TOP_KILLS : BNPC.NPCTypes.TOP_DEATHS;

        NPC npc = NPCStatistics.npcRegistry.createNPC(EntityType.PLAYER, type.getDisplayString());
        npc.setAlwaysUseNameHologram(true);
        npc.setName(type.getColor() + type.getDisplayString());
        npc.data().setPersistent("stat_type", type.toString());

        SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);
        skinTrait.setSkinName("bungoh");

        npc.spawn(player.getLocation());

        // Add the BNPC to the Stat Manager
        StatisticManager.getInstance().addBNPC(new BNPC(npc));
        return false;
    }

}
