package io.github.blobanium.mineclubexpanded.util.config;

import io.github.blobanium.mineclubexpanded.util.discord.DiscordRP;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;

public class ConfigReader {
	//variables
	private static boolean refreshingConfig = false;

	//configs
	private static boolean outbidNotificationsEnabled = false;
	private static boolean autoGGEnabled = false;
	private static int outbidVolume = 100;
	private static boolean richPresenceEnabled = false;
	private static boolean autoReconnectEnabled = false;

    public static final Logger LOGGER = LogManager.getLogger("Mineclub Expanded");

    public static void configRegister(){
    	LOGGER.debug("Registering config..");
    	SimpleConfig CONFIG = SimpleConfig.of("MineclubExpanded").provider(ConfigReader::ltProvider).request();
    	final boolean outbidConfig = CONFIG.getOrDefault("outbid_notification", outbidNotificationsEnabled);
		final boolean autoggConfig = CONFIG.getOrDefault("auto_gg", autoGGEnabled);
		final int outbidVolumeConfig = CONFIG.getOrDefault("outbid_volume", outbidVolume);
		final boolean richPresenceConfig = CONFIG.getOrDefault("rich_presence", richPresenceEnabled);
		final boolean autoReconnectConfig = CONFIG.getOrDefault("rich_presence", autoReconnectEnabled);

		if(outbidConfig){
			outbidNotificationsEnabled = true;
		}
		if(autoggConfig){
			autoGGEnabled = true;
		}
		outbidVolume = outbidVolumeConfig;
		if(richPresenceConfig){
			richPresenceEnabled = true;
		} else {
			if(DiscordRP.hasRPStarted && !DiscordRP.hasBlankStatus){
				DiscordRP.clearStatus();
			}
		}
		if(autoReconnectConfig){
			autoReconnectEnabled = true;
		}
    }

    private static String ltProvider(String filename) {
		return "#Mineclub Expanded Config File."
				+ "\noutbid_notification=" + outbidNotificationsEnabled
				+ "\nauto_gg=" + autoGGEnabled
				+ "\noutbid_volume=" + outbidVolume
				+ "\nrich_presence=" + richPresenceEnabled
				+ "\nauto_reconnect=" + autoReconnectEnabled;
	}


	public static void refreshConfig(){
		refreshingConfig = true;
		try {
			if(!Files.deleteIfExists(FabricLoader.getInstance().getConfigDir().resolve("MineclubExpanded.properties"))){
				LOGGER.error("Config file not found. Please ensure the path to the config is correct.\n" + FabricLoader.getInstance().getConfigDir().resolve("LoadingTimer.properties"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.fatal("Config Refresh Failed due to a IOException, please report this on our issues thread.");
			e.printStackTrace();
		}
		configRegister();
		refreshingConfig = false;
	}

	public static boolean isRefreshingConfig() {
		return refreshingConfig;
	}

	public static boolean isOutbidNotificationsEnabled() {
		return outbidNotificationsEnabled;
	}

	public static boolean isAutoGGEnabled() {
		return autoGGEnabled;
	}

	public static int getOutbidVolume() {
		return outbidVolume;
	}

	public static boolean isRichPresenceEnabled() {
		return richPresenceEnabled;
	}

	public static boolean isAutoReconnectEnabled() {
		return autoReconnectEnabled;
	}

	public static void setRefreshingConfig(boolean refreshingConfig) {
		ConfigReader.refreshingConfig = refreshingConfig;
	}

	public static void setOutbidNotificationsEnabled(boolean outbidNotificationsEnabled) {
		ConfigReader.outbidNotificationsEnabled = outbidNotificationsEnabled;
	}

	public static void setAutoGGEnabled(boolean autoGGEnabled) {
		ConfigReader.autoGGEnabled = autoGGEnabled;
	}

	public static void setOutbidVolume(int outbidVolume) {
		ConfigReader.outbidVolume = outbidVolume;
	}

	public static void setRichPresenceEnabled(boolean richPresenceEnabled) {
		ConfigReader.richPresenceEnabled = richPresenceEnabled;
	}

	public static void setAutoReconnectEnabled(boolean autoReconnectEnabled) {
		ConfigReader.autoReconnectEnabled = autoReconnectEnabled;
	}
}
