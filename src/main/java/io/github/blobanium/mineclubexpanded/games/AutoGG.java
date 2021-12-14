package io.github.blobanium.mineclubexpanded.games;

import io.github.blobanium.mineclubexpanded.util.config.ConfigReader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;

public class AutoGG {
    private static boolean spectatorMode = false;

    public static void autoGg(SoundInstance sound){
        if(ConfigReader.isAutoGGEnabled()) {
            if (sound.getId().toString().equals("minecraft:custom.mineclub.roundover-1")) {
                if (!spectatorMode) {
                    MinecraftClient.getInstance().player.sendChatMessage("gg");
                }
            }
        }
    }

    public static boolean isSpectatorMode() {
        return spectatorMode;
    }

    public static void setSpectatorMode(boolean spectatorMode) {
        AutoGG.spectatorMode = spectatorMode;
    }
}
