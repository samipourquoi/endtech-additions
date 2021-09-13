package io.github.samipourquoi.endtech;

import net.minecraft.network.packet.s2c.play.ScoreboardPlayerUpdateS2CPacket;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.WorldSavePath;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class ETAdditionsSettings {
    public static boolean scoreboardTotals;
    private static MinecraftServer server;

    public static int setScoreboardTotal(ServerCommandSource source, boolean boolean1) {
        MinecraftServer server = source.getMinecraftServer();
        scoreboardTotals = boolean1;

        source.sendFeedback(new TranslatableText("Set scoreboardTotals to " + scoreboardTotals),true);
        if (!boolean1)
            removeTotalScore(server);
        else
            addTotalScore(server);

        writeConfigSettings(server, boolean1);
        return 1;
    }

    private static void removeTotalScore(MinecraftServer server) {
        server.getPlayerManager().sendToAll(
                new ScoreboardPlayerUpdateS2CPacket(ServerScoreboard.UpdateMode.REMOVE, null, "Total", 0));
    }

    private static void addTotalScore(MinecraftServer server) {
        ServerScoreboard scoreboard = server.getScoreboard();
        ScoreboardObjective scoreboardObjective = scoreboard.getObjectiveForSlot(1);
        if (scoreboardObjective == null) return;

        scoreboard.createChangePackets(scoreboardObjective);
    }

    // Most code below this is from
    // https://github.com/gnembon/fabric-carpet/blob/92063fa8917d4591234efe73bb9201942f814f11/src/main/java/carpet/settings/SettingsManager.java

    public static void loadConfigSettings(MinecraftServer server) {
        try (BufferedReader reader = Files.newBufferedReader(getFile(server))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String value = line.split(" ")[1];
                boolean boolean2 = Boolean.parseBoolean(value);
                scoreboardTotals = boolean2;
            }
        } catch (NoSuchFileException e) {
            try {
                if (Files.notExists(getFile(server))) {
                    Files.createFile(getFile(server));
                }
            } catch (IOException e2) {
                System.out.println("Couldn't create config file for et-additions");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeConfigSettings(MinecraftServer server, boolean boolean1) {
        try (BufferedWriter writer = Files.newBufferedWriter(getFile(server))) {
            writer.write("scoreboardTotals " + boolean1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getFile(MinecraftServer server) {
        return server.getSavePath(WorldSavePath.ROOT).resolve("et-additions.txt");
    }
}


