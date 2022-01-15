package com.bungoh.npcstatistics.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

	public static String translate(String string) {
		if(string == null) return null;
		return ChatColor.translateAlternateColorCodes('&', string);
	}

}
