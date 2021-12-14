package io.github.blobanium.mineclubexpanded.util.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.blobanium.mineclubexpanded.util.discord.DiscordRP;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;


public class ModMenuConfig implements ModMenuApi {

    private static ConfigScreenFactory<?> CONFIG = FabricLoader.getInstance().isModLoaded("cloth-config2")
    ? new LTClothConfig()
    : parent -> null;
    
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
            // Return the screen here with the one you created from Cloth Config Builder;
            return CONFIG;
    }

    private static class LTClothConfig implements ConfigScreenFactory<Screen> {

        @Override
        public Screen create(Screen parent) {

            final ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("mineclub-expanded.config"));

            // Serialise the config into the config file. This will be called last after all variables are updated.
            builder.setSavingRunnable(ConfigReader::refreshConfig);
        
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("mineclub-expanded.category.market"));

            if(DiscordRP.supportsRichPresence) {
                general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("mineclub-expanded.config.richpresence"), ConfigReader.isRichPresenceEnabled())
                        .setDefaultValue(false)
                        .setTooltip(new TranslatableText("mineclub-expanded.config.richpresence.description"))
                        .setSaveConsumer(ConfigReader::setRichPresenceEnabled)
                        .build());
            } else {
                general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("mineclub-expanded.config.richpresence.unsupported"), ConfigReader.isRichPresenceEnabled())
                        .setDefaultValue(false)
                        .setTooltip(new TranslatableText("mineclub-expanded.config.richpresence.unsupported.description"))
                        .setSaveConsumer(ConfigReader::setRichPresenceEnabled)
                        .build());
            }

            general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("mineclub-expanded.config.outbidsound"), ConfigReader.isOutbidNotificationsEnabled())
                    .setDefaultValue(false)
                    .setTooltip(new TranslatableText("mineclub-expanded.config.outbidsound.description"))
                    .setSaveConsumer(ConfigReader::setOutbidNotificationsEnabled)
                    .build());

            general.addEntry(entryBuilder.startIntSlider(new TranslatableText("mineclub-expanded.config.outbidvolume"), ConfigReader.getOutbidVolume(), 0, 200)
                    .setDefaultValue(100)
                    .setTooltip(new TranslatableText("mineclub-expanded.config.outbidvolume.description"))
                    .setSaveConsumer(ConfigReader::setOutbidVolume)
                    .build());

            general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("mineclub-expanded.config.autogg"), ConfigReader.isAutoGGEnabled())
                    .setDefaultValue(false)
                    .setTooltip(new TranslatableText("mineclub-expanded.config.autogg.description"))
                    .setSaveConsumer(ConfigReader::setAutoGGEnabled)
                    .build());

            general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("mineclub-expanded.config.autoreconnect"), ConfigReader.isAutoReconnectEnabled())
                    .setDefaultValue(false)
                    .setTooltip(new TranslatableText("mineclub-expanded.config.autoreconnect.description"))
                    .setSaveConsumer(ConfigReader::setAutoReconnectEnabled)
                    .build());


            return builder.build();
        }
    }
}
