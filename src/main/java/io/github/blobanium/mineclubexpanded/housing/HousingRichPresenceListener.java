package io.github.blobanium.mineclubexpanded.housing;

import io.github.blobanium.mineclubexpanded.util.discord.DiscordRP;
import net.minecraft.client.MinecraftClient;

public class HousingRichPresenceListener {
    public static String lastChatField;

    public static void sendHousingPresence(){
        if(lastChatField.startsWith("/home")) {
            final String filteredMessage = lastChatField.replace("/home", "").replace(" ", "");
            if (filteredMessage.isEmpty()){
                DiscordRP.updateStatus("Currently In " + MinecraftClient.getInstance().getSession().getUsername() + "'s Home", "Playing On Mineclub");
            }else {
                DiscordRP.updateStatus("Currently In " + filteredMessage + "'s Home", "Playing On Mineclub");
            }
        } else {
            DiscordRP.updateStatus("Currently In Housing", "Playing On Mineclub");
        }
        lastChatField = "";
    }
}
