package com.bungoh.npcstatistics.utils.placeholders;

import com.bungoh.npcstatistics.utils.StatsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.jetbrains.annotations.NotNull;

public class TopKills extends PlaceholderExpansion {

	@Override
	public @NotNull String getIdentifier() {
		return "ls_stats_kills";
	}

	@Override
	public @NotNull String getAuthor() {
		return "LearnSpigot";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}

	@Override
	public String onRequest(OfflinePlayer player, String placeHolder) {
		if(placeHolder.equalsIgnoreCase("1")) {
			// Get the #1 Player Killer
			return null;
		}

		if(placeHolder.equalsIgnoreCase("3")) {
			// Get the #2 Player Killer from a HashMap
			return null;
		}

		if(placeHolder.equalsIgnoreCase("3")) {
			// Get the #3 Player Killer
			return null;
		}

		// Unknown Placeholder
		return null;
	}
}
