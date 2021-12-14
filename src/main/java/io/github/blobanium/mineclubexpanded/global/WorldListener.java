package io.github.blobanium.mineclubexpanded.global;

import io.github.blobanium.mineclubexpanded.MineclubExpanded;
import io.github.blobanium.mineclubexpanded.games.tabletop.RichPresenceTabletopChatListener;
import io.github.blobanium.mineclubexpanded.housing.HousingRichPresenceListener;
import io.github.blobanium.mineclubexpanded.util.discord.DiscordRP;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

import java.util.function.BiPredicate;

public class WorldListener {

    public static String worldName;

    private static boolean inHousing = false;
    private static boolean cancelHousingUpdate = false;
    private static boolean alreadyInStaffHQ = false;

    public static void listenWorld(){
        if (MineclubExpanded.isOnMineclub()) {
            final ClientWorld clientWorld = MinecraftClient.getInstance().world;
            if (clientWorld == null) {
                return;
            }

            if (!clientWorld.getRegistryKey().getValue().getPath().equals(worldName)) {
                worldName = clientWorld.getRegistryKey().getValue().getPath();
                MineclubExpanded.LOGGER.debug("WorldName=" + worldName);
                worldCheck(worldName);
            }
        }
    }

    private static void worldCheck(String world){
        //Lobby, AFK Lounge
        checkWorld(MatchingMode.EQUALITY, world, "overworld", "In The Lobby", "Playing On Mineclub");

        //Main Games
        checkWorld(MatchingMode.EQUALITY, world, "gamemap_battle_dome", "Currently In Battle Dome", "Playing On Mineclub");
        checkWorld(MatchingMode.EQUALITY, world, "gamemap_slime_walls", "Currently In Slime Walls", "Playing On Mineclub");
        checkWorld(MatchingMode.STARTS_WITH, world, "master", "Currently Playing Speed Tag", "Playing On Mineclub");
        checkWorld(MatchingMode.EQUALITY, world, "gamemap_laser_tag", "Currently In Laser Tag", "Playing On Mineclub");
        checkWorld(MatchingMode.EQUALITY, world, "gamemap_dodge_ball", "Currently In Dodge Ball", "Playing On Mineclub");

        //Tabletop Games
        checkWorld(MatchingMode.STARTS_WITH, world, "connect4", "Playing with " + RichPresenceTabletopChatListener.getMatchedUsername(), "Currently Playing Connect 4");
        checkWorld(MatchingMode.STARTS_WITH, world, "match5", "Playing with " + RichPresenceTabletopChatListener.getMatchedUsername(), "Currently Playing Match 5");
        checkWorld(MatchingMode.STARTS_WITH, world, "luckyshot", "Playing with " + RichPresenceTabletopChatListener.getMatchedUsername(), "Currently Playing Lucky Shot");
        checkWorld(MatchingMode.STARTS_WITH, world, "ttt", "Playing with " + RichPresenceTabletopChatListener.getMatchedUsername(), "Currently Playing Tic Tac Toe");
        checkWorld(MatchingMode.STARTS_WITH, world, "sumo", "Playing with " + RichPresenceTabletopChatListener.getMatchedUsername(), "Currently Playing Sumo");
        checkWorld(MatchingMode.STARTS_WITH, world, "ms", "Playing with " + RichPresenceTabletopChatListener.getMatchedUsername(), "Currently Playing Minesweep");
        checkWorld(MatchingMode.STARTS_WITH, world, "snowball", "Playing with " + RichPresenceTabletopChatListener.getMatchedUsername(), "Currently Playing Snowball Fight");

        //Housing
        checkHousing(world);
    }

    private static void checkWorld(MatchingMode matchingMode, String world, String targetWorldName, String state, String details){
        if (matchingMode.getBiPredicate().test(world, targetWorldName)) {
            sendPresence(state, details);
        }
    }

    private static void sendPresence(String state, String details){
        DiscordRP.updateStatus(state, details);
        alreadyInStaffHQ = false;
    }

    private static void checkHousing(String world){
        if (!cancelHousingUpdate) {
            if (world.startsWith("housing")) {
                inHousing = true;
                if (FabricLoader.getInstance().isModLoaded("advancedchat")) {
                    DiscordRP.updateStatus("Currently In Housing", "Playing On Mineclub");
                } else {
                    HousingRichPresenceListener.sendHousingPresence();
                }
            } else {
                inHousing = false;
            }
        } else {
            cancelHousingUpdate = false;
        }
    }

    private enum MatchingMode {

        EQUALITY((world, targetWorldName) -> world.equals(targetWorldName)),
        STARTS_WITH((world, targetWorldName) -> world.startsWith(targetWorldName));

        final BiPredicate<String, String> biPredicate;

        MatchingMode(BiPredicate<String, String> biPredicate) {
            this.biPredicate = biPredicate;
        }

        public BiPredicate<String, String> getBiPredicate() {
            return biPredicate;
        }

    }

    public static boolean isInHousing() {
        return inHousing;
    }

    public static boolean isCancelHousingUpdate() {
        return cancelHousingUpdate;
    }

    public static boolean isAlreadyInStaffHQ() {
        return alreadyInStaffHQ;
    }

    public static void setInHousing(boolean inHousing) {
        WorldListener.inHousing = inHousing;
    }

    public static void setCancelHousingUpdate(boolean cancelHousingUpdate) {
        WorldListener.cancelHousingUpdate = cancelHousingUpdate;
    }

    public static void setAlreadyInStaffHQ(boolean alreadyInStaffHQ) {
        WorldListener.alreadyInStaffHQ = alreadyInStaffHQ;
    }
}
