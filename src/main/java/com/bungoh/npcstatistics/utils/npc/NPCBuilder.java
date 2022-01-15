package com.bungoh.npcstatistics.utils.npc;

import com.bungoh.npcstatistics.NPCStatistics;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class NPCBuilder {

	private EntityType npcType;
	private String npcName;
	private boolean showName;

	public NPCBuilder(EntityType npcType, String npcName, boolean showName) {
		this.npcType = npcType;
		this.npcName = npcName;
		this.showName = showName;
	}

	private NPC npcInst;

	public void create() {
		npcInst = CitizensAPI.getNPCRegistry().createNPC(npcType,npcName);

		// Removes the Citizens Nametag
		npcInst.setAlwaysUseNameHologram(showName);
	}


	public void sendToPlayer(Player player) {
		if(npcInst.isSpawned()) {
			npcInst.teleport(player.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
		}
	}

	/*
	 * Multi-Lined NPC Nametags
	 */
	private String[] npcNametag;
	private Hologram nameHologram;

	public void createNametag(String[] npcNametag) {
		this.npcNametag = npcNametag;

		if(npcInst.isSpawned()) {
			Location hologramLocation = null;

			for(int i = 0; i < npcNametag.length; i++) {
				hologramLocation = new Location(npcInst.getStoredLocation().getWorld(), npcInst.getStoredLocation().getX(), npcInst.getStoredLocation().getY() + 0.5, npcInst.getStoredLocation().getZ());
				nameHologram.insertTextLine(i, npcNametag[i]);
			}

			if (hologramLocation != null) nameHologram = HologramsAPI.createHologram(NPCStatistics.getInstance(), hologramLocation);
		}
};

	public void updateNametag(int strLine, String updateTo) {
		nameHologram.getLine(strLine).removeLine();
		nameHologram.insertTextLine(strLine,updateTo);
	}
}
