package io.github.blobanium.mineclubexpanded.games.tabletop;

import net.minecraft.text.Text;

public class RichPresenceTabletopChatListener {
    private static String matchedUsername;

    public static void onChatMessage(Text message){
        final String textMessage = message.getString().replaceAll("\\[","\\\\[");
        if (textMessage.startsWith("ꌄ§8\\[§dGames§8] §8Matched with player")) {
            matchedUsername = textMessage.substring(44);
        }
    }

    public static String getMatchedUsername() {
        return matchedUsername;
    }
}
