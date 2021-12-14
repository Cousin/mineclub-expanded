package io.github.blobanium.mineclubexpanded.mixin;

import io.github.blobanium.mineclubexpanded.MineclubExpanded;
import io.github.blobanium.mineclubexpanded.global.WorldListener;
import io.github.blobanium.mineclubexpanded.housing.HousingRichPresenceListener;
import io.github.blobanium.mineclubexpanded.housing.HousingRichPresenceTickTracker;
import io.github.blobanium.mineclubexpanded.util.feature.Autoreconnect;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(at = @At("HEAD"), method = "onKey")
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci){
        switch (key) {
            case 256: // esc
                MineclubExpanded.setChatOpen(false);
                Autoreconnect.cancelAutoReconnect = true;
                break;

            case 257: // enter/return
                if (WorldListener.isInHousing()) {
                    if (HousingRichPresenceListener.lastChatField.startsWith("/home")) {
                        HousingRichPresenceTickTracker.setReminder(3);
                    }
                }
                break;
        }
    }

}
