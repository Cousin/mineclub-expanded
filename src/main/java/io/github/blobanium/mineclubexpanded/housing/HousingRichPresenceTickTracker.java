package io.github.blobanium.mineclubexpanded.housing;

import io.github.blobanium.mineclubexpanded.MineclubExpanded;
import io.github.blobanium.mineclubexpanded.global.WorldListener;
import io.github.blobanium.mineclubexpanded.util.tick.TickTracker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

public class HousingRichPresenceTickTracker{
    public static boolean cancelHousingUpdate = false;
    private static int tickTarget;
    private static boolean hasNotified = true;

    public static void setReminder(int seconds){
        tickTarget = TickTracker.tickNo + (seconds * 20);
        hasNotified = false;
    }

    public static void checkReminder(){
        if (TickTracker.tickNo >= tickTarget && !hasNotified) {
            final ClientWorld clientWorld = MinecraftClient.getInstance().world;
            if (clientWorld == null) {
                return;
            }

            if (WorldListener.worldName.equals(clientWorld.getRegistryKey().getValue().getPath())) {
                if (cancelHousingUpdate) {
                    MineclubExpanded.LOGGER.debug("Canceling Housing Update");
                } else {
                    HousingRichPresenceListener.sendHousingPresence();
                }
            }

            cancelHousingUpdate = false;
            hasNotified = true;
        }
    }
}
