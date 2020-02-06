package com.extendedclip.papi.expansion.chatreaction;

import me.clip.chatreaction.ChatReaction;
import me.clip.chatreaction.ReactionAPI;
import me.clip.chatreaction.events.ReactionWinEvent;
import me.clip.placeholderapi.expansion.Cacheable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatReactionExpansion extends PlaceholderExpansion implements Listener, Cacheable {

    Player getWinner;

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin(getPlugin()) != null;
    }

    @Override
    public boolean register() {

        if (!canRegister()) {
            return false;
        }

        return me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
    }

    @Override
    public String getAuthor() {
        return "clip";
    }

    @Override
    public String getIdentifier() {
        return "chatreaction";
    }

    @Override
    public String getPlugin() {
        return "ChatReaction";
    }

    @Override
    public String getVersion() {
        return "1.2.0";
    }

    @Override
    public void clear() {
        getWinner = null;
    }

    @EventHandler
    public void onReactionWin(ReactionWinEvent event) {
        getWinner = event.getWinner();
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {

        if (p == null) {
            return "";
        }

        final Object result;

        switch (identifier.toLowerCase()) {
            case "wins":
                return String.valueOf(ReactionAPI.getWins(p));

            case "type":
                if (ReactionAPI.isStarted()) {
                    result = ChatReaction.isScrambled() ? "scramble" : "reaction";
                } else {
                    result = "none";
                }
                break;

            case "active_round":
                result = ReactionAPI.isStarted();
                break;

            case "display_word":
                result = ReactionAPI.getDisplayWord() != null ? ReactionAPI.getDisplayWord() : " ";
                break;

            case "reaction_word":
                result = ReactionAPI.getReactionWord() != null ? ReactionAPI.getReactionWord() : " ";
                break;

            case "start_time":
                result = ReactionAPI.getStartTime();
                break;

            case "latest_winner":
                result = getWinner != null ? getWinner.getName() : " ";
                break;

            default:
                return null;
        }
        return String.valueOf(result);
    }
}


