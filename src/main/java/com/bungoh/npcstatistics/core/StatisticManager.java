package com.bungoh.npcstatistics.core;

import com.bungoh.npcstatistics.NPCStatistics;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StatisticManager {

    private static StatisticManager instance = null;

    private List<BNPC> bnpcs;
    private File file;
    private YamlConfiguration data;

    private Map<Statistic, OfflinePlayer> topPlayers;

    private StatisticManager() {
        setupDataFile();
        bnpcs = new ArrayList<>();
        topPlayers = new HashMap<>();

        List<String> bnpcUUIDS = data.getStringList("uuids");
        for (NPC npc : NPCStatistics.npcRegistry.sorted()) {
            if (bnpcUUIDS.contains(npc.getUniqueId().toString())) {
                bnpcs.add(new BNPC(npc));
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                update(Statistic.PLAYER_KILLS);
                update(Statistic.DEATHS);
            }
        }.runTaskTimerAsynchronously(NPCStatistics.getInstance(), 0, 300L * 20L);
    }

    public static StatisticManager getInstance() {
        if (instance == null) {
            instance = new StatisticManager();
        }

        return instance;
    }

    private void setupDataFile() {
        file = new File(NPCStatistics.getInstance().getDataFolder(), "data.yml");
        file.mkdir();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        data = YamlConfiguration.loadConfiguration(file);
        saveDataFile();
    }

    public void addBNPCToData(UUID uuid) {
        List<String> uuids = data.getStringList("uuids");
        uuids.add(uuid.toString());
        data.set("uuids", uuids);
        saveDataFile();
    }

    public boolean dataContainsUUID(UUID uuid) {
        return data.getStringList("uuids").contains(uuid.toString());
    }

    public void removeBNPCFromData(UUID uuid) {
        List<String> uuids = data.getStringList("uuids");
        uuids.remove(uuid.toString());
        data.set("uuids", uuids);
        saveDataFile();
    }

    private void saveDataFile() {
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update(Statistic statistic) {
        Map<OfflinePlayer, Integer> map = new HashMap<>();
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            map.put(p, p.getStatistic(statistic));
        }

        Map.Entry<OfflinePlayer, Integer> maxEntry = null;
        for (Map.Entry<OfflinePlayer, Integer> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        topPlayers.put(statistic, maxEntry.getKey());
        for (BNPC bnpc : bnpcs) {
            if (bnpc.getType().getStatistic() == statistic) {
                bnpc.update(topPlayers.get(statistic));
            }
        }
    }

    public void addBNPC(BNPC bnpc) {
        bnpcs.add(bnpc);
        addBNPCToData(bnpc.getNPC().getUniqueId());
        bnpc.update(topPlayers.get(bnpc.getType().getStatistic()));
    }

    public boolean removeBNPC(NPC npc) {
        for (BNPC bnpc : bnpcs) {
            if (bnpc.getNPC().equals(npc)) {
                bnpcs.remove(bnpc);
                removeBNPCFromData(bnpc.getNPC().getUniqueId());
                return true;
            }
        }
        return false;
    }
}
