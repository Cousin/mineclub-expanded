package io.github.blobanium.mineclubexpanded;

import io.github.blobanium.mineclubexpanded.util.config.ConfigReader;
import io.github.blobanium.mineclubexpanded.util.discord.DiscordRP;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class MineclubExpanded implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("Mineclub Expanded");

	private static boolean chatOpen = false;

	private static final Pattern MINECLUB_SERVER_ADDR_PATTERN = Pattern.compile("(.+\\.)?mineclub\\.(com|net|org|house)", Pattern.CASE_INSENSITIVE);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Mineclub Expanded Initialized!");
		ConfigReader.configRegister();
		try {
			DiscordRP.startRP();
		} catch (Exception e){
			LOGGER.error("Failed to start rich presence, Your Device/Install may not support rich presence! \n" + e);
			DiscordRP.supportsRichPresence = false;
		}
	}

	public static boolean isOnMineclub() {
		final ServerInfo serverInfo = MinecraftClient.getInstance().getCurrentServerEntry();
		if (serverInfo == null) {
			return false;
		}

		return MINECLUB_SERVER_ADDR_PATTERN.matcher(serverInfo.address).matches();
	}

	public static boolean isChatOpen() {
		return chatOpen;
	}

	public static void setChatOpen(boolean chatOpen) {
		MineclubExpanded.chatOpen = chatOpen;
	}
}
