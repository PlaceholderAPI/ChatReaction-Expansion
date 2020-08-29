package com.extendedclip.papi.expansion.chatreaction;

import me.clip.chatreaction.ReactionAPI;
import me.clip.chatreaction.ChatReaction;

import me.clip.chatreaction.ReactionConfig;
import me.clip.chatreaction.events.ReactionWinEvent;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import java.util.Calendar;

public class ChatReactionExpansion extends PlaceholderExpansion implements Listener {

    static ReactionConfig config;

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
        return "1.5";
    }

    private Player winner;

    @EventHandler
    public void onReactionWin(ReactionWinEvent event) {
        winner = event.getWinner();
    }

    @Override
    public String onRequest(final OfflinePlayer player, final String input) {
        if (player == null) {
            return "";
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
                    return "none";
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

        return null;
    }
}
