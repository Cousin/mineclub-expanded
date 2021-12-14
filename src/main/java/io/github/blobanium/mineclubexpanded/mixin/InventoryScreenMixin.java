package io.github.blobanium.mineclubexpanded.mixin;

import io.github.blobanium.mineclubexpanded.global.WorldListener;
import io.github.blobanium.mineclubexpanded.util.mixinhelper.GradientHelper;
import io.github.blobanium.mineclubexpanded.util.discord.DiscordRP;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.Item;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class InventoryScreenMixin {

    private static final int PLAYER_HEAD_RAW_ID = 955;

    @Inject(at = @At("HEAD"), method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V")
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci){
        if (Item.getRawId(slot.getStack().getItem()) == PLAYER_HEAD_RAW_ID) {
            if (slot.getStack().getNbt() == null) { // hasNbt probably better, but rids of annoying Nullable warning
                return;
            }

            final String jsonDisplayName = slot.getStack().getNbt().getCompound("display").getString("Name");
            final String fullname = jsonDisplayName.substring(130).replace("\"}],\"text\":\"\"}", "");

            if(fullname.length() <= 16){
                WorldListener.cancelHousingUpdate = true;
                DiscordRP.updateStatus("Currently In " + fullname + "'s Home", "Playing On Mineclub");
            } else {
                WorldListener.cancelHousingUpdate = true;
                DiscordRP.updateStatus("Currently In " + GradientHelper.convertGradientToString(fullname) + "'s Home", "Playing On Mineclub");
            }
        }
    }

}
