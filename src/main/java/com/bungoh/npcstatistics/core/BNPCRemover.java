package com.bungoh.npcstatistics.core;

import com.bungoh.npcstatistics.NPCStatistics;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;

public class BNPCRemover implements Listener {

    private final Player player;
    private final Runnable runnable;

    public BNPCRemover(Player player, Runnable runnable) {
        this.player = player;
        this.runnable = runnable;
        Bukkit.getPluginManager().registerEvents(this, NPCStatistics.getInstance());
    }

    @EventHandler
    public void punchEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (!player.equals(damager)) {
                return;
            }

            //Check if the entity that was hit has a UUID included in the list (this should be sufficient)
            Entity hit = event.getEntity();
            NPC npc = NPCStatistics.npcRegistry.getNPC(hit);
            if (npc == null) {
                return;
            }

            if (StatisticManager.getInstance().dataContainsUUID(npc.getUniqueId())) {
                // Remove this BNPC
                if (StatisticManager.getInstance().removeBNPC(npc)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&aSuccessfully removed NPC."));
                    npc.destroy();
                }
                destroy();
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if ((event.getAction() == RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getPlayer().equals(player)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&aQuit NPC removal!"));
            destroy();
        }
    }

    private void destroy() {
        runnable.run();
        HandlerList.unregisterAll(this);
    }

}
