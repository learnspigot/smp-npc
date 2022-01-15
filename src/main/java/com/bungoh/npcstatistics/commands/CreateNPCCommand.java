package com.bungoh.npcstatistics.commands;

import com.bungoh.npcstatistics.utils.ChatUtils;
import com.bungoh.npcstatistics.utils.commandframework.Command;
import com.bungoh.npcstatistics.utils.commandframework.CommandArgs;
import com.bungoh.npcstatistics.utils.commandframework.CommandFramework;
import com.bungoh.npcstatistics.utils.npc.NPCBuilder;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CreateNPCCommand {

	public CreateNPCCommand(CommandFramework cmdFramework) {
		cmdFramework.registerCommands(this);
	}

	@Command(name = "createnpc", permission = "npcstats.createnpc", description = "Create a Stat NPC", usage = "/createnpc []", inGameOnly = true)
	public void createNPCCommand(CommandArgs cmdArgs) {

		Player cmdPlayer = cmdArgs.getPlayer();

		if(cmdArgs.length() != 1) {
			cmdPlayer.sendMessage(ChatUtils.translate("&7You must specify a Top Stat NPC type (Kills/Deaths)"));
		} else if (cmdArgs.getArgs(0).equalsIgnoreCase("kills")) {

			// Creates the Top Kills NPC and sends to exact player location
			NPCBuilder topKillsNPC = new NPCBuilder(EntityType.PLAYER, "Top Kills", false);
			topKillsNPC.create();
			topKillsNPC.sendToPlayer(cmdPlayer);

			topKillsNPC.createNametag(new String[]{
					"&a&lTOP KILLS",
					"&e&l#1 &f%top_kills_1%",
					"&e&l#2 &f%top_kills_2%",
					"&e&l#1 &f%top_kills_3%"
			});
		} else if (cmdArgs.getArgs(0).equalsIgnoreCase("deaths")) {

		} else {
			cmdPlayer.sendMessage(ChatUtils.translate("&7You must specify a Top Stat NPC type (Kills/Deaths)"));
		}
	}
}
