package com.extendedclip.papi.expansion.chatreaction;

import me.clip.chatreaction.ReactionAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatReactionExpansion extends PlaceholderExpansion {

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
		return "1.0.2";
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}
		
		if (identifier.equals("wins")) {
			return String.valueOf(ReactionAPI.getWins(p));
		}

		if (identifier.equals("isStarted")) {
			return String.valueOf(ReactionAPI.isStarted());
		}

		if (identifier.equals("displayWord")) {
			return String.valueOf(ReactionAPI.getDisplayWord());
		}

		if (identifier.equals("reactionWord")) {
			return String.valueOf(ReactionAPI.getReactionWord());
		}

		if (identifier.equals("startTime")) {
			return String.valueOf(ReactionAPI.getStartTime());
		}
		return null;
	}
}
