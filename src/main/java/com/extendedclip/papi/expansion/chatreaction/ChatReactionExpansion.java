package com.extendedclip.papi.expansion.chatreaction;

import com.extendedclip.papi.expansion.chatreaction.util.DateUtil;
import me.clip.chatreaction.ReactionAPI;
import me.clip.chatreaction.ChatReaction;
import me.clip.chatreaction.ReactionConfig;
import me.clip.chatreaction.events.ReactionWinEvent;
import me.clip.chatreaction.reactionplayer.ReactionPlayer;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Calendar;

public final class ChatReactionExpansion extends PlaceholderExpansion implements Listener {

    final ChatReaction plugin = JavaPlugin.getPlugin(ChatReaction.class);
    final ReactionConfig config = plugin.config;
    final ReactionAPI api = new ReactionAPI();

    @Override
    public String getAuthor() {
        return "kaliber";
    }

    @Override
    public String getIdentifier() {
        return "chatreaction";
    }

    @Override
    public String getRequiredPlugin() {
        return "ChatReaction";
    }

    @Override
    public String getVersion() {
        return "1.6.3";
    }

    private Player winner;

    @EventHandler
    public void onReactionWin(final ReactionWinEvent event) {
        winner = event.getWinner();
    }

    @Override
    public String onRequest(final OfflinePlayer player, final String input) {
        if (player == null) {
            return null;
        }

        final int timeLimit = config.timeLimit();
        final boolean reactionHasStarted = ReactionAPI.isStarted();
        final long timeNow = Calendar.getInstance().getTimeInMillis();
        final long startTime = ReactionAPI.getStartTime();
        final long timeDifference = (Math.abs(timeNow - startTime)) / 1000;

        switch (input.toLowerCase()) {
            case "wins":
                return String.valueOf(ReactionAPI.getWins(player));

            case "type":
                if (!reactionHasStarted) {
                    return "";
                }
                return ChatReaction.isScrambled() ? "Scramble" : "Reaction";

            case "active_round":
                return reactionHasStarted ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();

            case "display_word":
                final String displayWord = ReactionAPI.getDisplayWord();
                return displayWord != null ? displayWord : "";

            case "reaction_word":
                final String reactionWord = ReactionAPI.getReactionWord();
                return reactionWord != null ? reactionWord : "";

            case "latest_winner":
                return winner != null ? winner.getName() : "";

            case "start_time":
                return String.valueOf(startTime);

            case "start_time_formatted":
                if (!reactionHasStarted) {
                    return "";
                }
                return DateUtil.formatDate(startTime);

            case "time_in_seconds":
                if (!reactionHasStarted) {
                    return "0";
                }
                return String.valueOf(timeDifference);

            case "time_remaining":
                if (!reactionHasStarted) {
                    return "0";
                }
                return String.valueOf(timeLimit - timeDifference);
        }

        if (input.startsWith("wins_")) {
            final OfflinePlayer target = Bukkit.getOfflinePlayer(input.substring(5)); // substring after "wins_"
            return String.valueOf(ReactionAPI.getWins(target));
        }

        if (!input.startsWith("top_")) {
            return null;
        }

        final String[] args = input.split("_");
        if (args.length < 3 || Integer.parseInt(args[2]) > config.topPlayersSize()) {
            return null; // entering a number over the specified top players limit in the config will cause IOOB
        }

        final List<ReactionPlayer> topWinners = api.getTopWinners();
        if (topWinners == null) {
            return null;
        }

        if (args[1].equals("wins")) {
            return String.valueOf(topWinners.get(Integer.parseInt(args[2]) - 1).getWins());
        }

        if (args[1].equals("player")) {
            return topWinners.get(Integer.parseInt(args[2]) - 1).getName();
        }
        return null;
    }
}
