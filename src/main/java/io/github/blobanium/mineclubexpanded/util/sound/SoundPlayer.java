package io.github.blobanium.mineclubexpanded.util.sound;

import io.github.blobanium.mineclubexpanded.util.config.ConfigReader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SoundPlayer {

    public static void playSound(float pitch){
        final SoundEvent sound = registerSound();
        final PositionedSoundInstance posSound = PositionedSoundInstance.master(sound, pitch, getVolume(ConfigReader.getOutbidVolume()));
        MinecraftClient.getInstance().getSoundManager().play(posSound);
    }

    private static SoundEvent registerSound(){
        final Identifier soundId = new Identifier(SoundEvents.ENTITY_BEE_DEATH.getId().toString());
        return Registry.SOUND_EVENT.get(soundId);
    }

    private static float getVolume(int volume){
        return new BigDecimal(volume).divide(new BigDecimal(100), 2, RoundingMode.CEILING).floatValue();
    }
}
