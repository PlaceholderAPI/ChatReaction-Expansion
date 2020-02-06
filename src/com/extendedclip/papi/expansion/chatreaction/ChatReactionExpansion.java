package com.extendedclip.papi.expansion.chatreaction;

import me.clip.chatreaction.ChatReaction;
import me.clip.chatreaction.ReactionAPI;
import me.clip.chatreaction.events.ReactionWinEvent;
import me.clip.placeholderapi.expansion.Cacheable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class ChatReactionExpansion extends PlaceholderExpansion implements Cacheable {

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
		return "1.0.4";
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (p == null) {
			return "";
		}

		if (identifier.equals("wins")) {
			return String.valueOf(ReactionAPI.getWins(p));
		}

		if (identifier.equals("type")) {
			if (ReactionAPI.isStarted()) {
				if (ChatReaction.isScrambled()) return "scramble";
				else return "reaction";
			} else return "none";
		}

		if (identifier.equals("isStarted")) {
			return String.valueOf(ReactionAPI.isStarted());
		}

		if (identifier.equals("displayWord")) {
			return ReactionAPI.getDisplayWord() != null ? ReactionAPI.getDisplayWord() : " ";
		}

		if (identifier.equals("reactionWord")) {
			return ReactionAPI.getReactionWord() != null ? ReactionAPI.getReactionWord() : " ";
		}

		if (identifier.equals("startTime")) {
			return String.valueOf(ReactionAPI.getStartTime());
		}

		if (identifier.equals("latestWinner")) {
			return getWinner != null ? getWinner.getName() : " ";
		}

		return null;
	}

		@Override
		public void clear () {
			getWinner = null;
		}

		@EventHandler
		public void playerWinEvent (ReactionWinEvent event){
			getWinner = event.getWinner();
		}
	}
