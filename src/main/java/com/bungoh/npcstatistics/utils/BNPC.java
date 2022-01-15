package com.bungoh.npcstatistics.utils;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

public class BNPC {

    private NPC npc;
    private NPCTypes type;
    private SkinTrait skinTrait;

    public BNPC(NPC npc) {
        this.npc = npc;
        this.skinTrait = npc.getTraitNullable(SkinTrait.class);
        type = NPCTypes.valueOf(npc.data().get("stat_type"));
    }

    public void update(OfflinePlayer player) {
        // FIXME: There's currently a bug within the game that removes the NPC Tag, this is a quick workaround until we have a solid solution
        npc.setAlwaysUseNameHologram(true);
        npc.setName(type.getColor() + type.getDisplayString() + " " +  ChatColor.WHITE + player.getName());
        skinTrait.setSkinName(player.getName());
    }

    public NPC getNPC() {
        return npc;
    }

    public NPCTypes getType() {
        return type;
    }

    public enum NPCTypes {
        TOP_KILLS("Top Kills", ChatColor.GREEN, Statistic.PLAYER_KILLS),
        TOP_DEATHS("Top Deaths", ChatColor.RED, Statistic.DEATHS);

        private String displayString;
        private ChatColor color;
        private Statistic statistic;

        NPCTypes(String displayString, ChatColor color, Statistic statistic) {
            this.displayString = displayString;
            this.color = color;
            this.statistic = statistic;
        }

        public String getDisplayString() {
            return displayString;
        }
        public ChatColor getColor() {
            return color;
        }
        public Statistic getStatistic() {
            return statistic;
        }
    }

}
