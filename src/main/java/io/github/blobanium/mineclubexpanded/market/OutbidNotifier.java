package io.github.blobanium.mineclubexpanded.market;

import io.github.blobanium.mineclubexpanded.util.sound.SoundPlayer;
import net.minecraft.text.Text;

public class OutbidNotifier {

    public static void onChatMessage(Text message){
        final String textMessage = message.getString().replaceAll("\\[","\\\\[");
        if (textMessage.startsWith("ꌄ§8\\[§dMarket§8] §cYou have been outbid by")) {
            SoundPlayer.playSound(1.0F);
        }
        if (textMessage.startsWith("ꌄ§8\\[§dHousing§8]§c You have been outbid by")) {
            SoundPlayer.playSound(0.8F);
        }
    }

}
