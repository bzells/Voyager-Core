package com.jzells.voyagercore.tools.data;

import com.gregtechceu.gtceu.data.lang.LangHandler;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.tools.VCTConModifiers;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class VoyagerCoreModifierLangProvider extends LangHandler {

    public static String getName() {
        return "VoyagerCore TCon Lang";
    }

    public static void init(RegistrateLangProvider provider) {
        VoyagerCore.LOGGER.info(VoyagerCoreModifierLangProvider::getName);
        addTranslation(provider, VCTConModifiers.dragon_looting.getId(), "Dragon Looting",
                "Find more from ores with this tool", "Hoarder!");

        addTranslation(provider, VCTconModifierIds.dragon_strength, "Dragon Strength",
                "Dragonblood fuels this tool's attack power", "Power!");

        addTranslation(provider, VCTconModifierIds.dragon_protection, "Dragon Scales",
                "Ice-Forged steel strengthens the armors forged in this material", "Hard!");
    }

    protected static void addTranslation(RegistrateLangProvider provider, ModifierId id, String name, String desc,
                                         String flavor) {
        String langKey = id.toLanguageKey("modifier");
        addTranslation(provider, langKey, name, desc, flavor);
    }

    protected void addTranslation(RegistrateLangProvider provider, Modifier id, String name, String desc,
                                  String flavor) {
        String langKey = id.getTranslationKey();
        addTranslation(provider, langKey, name, desc, flavor);
    }

    protected static void addTranslation(RegistrateLangProvider provider, String id, String name, String desc,
                                         String flavor) {
        provider.add(id, name);
        provider.add((id + ".flavor"), flavor);
        provider.add((id + ".description"), desc);
    }
}
