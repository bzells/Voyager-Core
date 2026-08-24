package com.jzells.voyagercore.tools.data;

import com.tterrag.registrate.providers.ProviderType;

import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;

public class VoyagerTinkersDataGen {

    public static void init() {
        VOYAGERCORE_REGISTRATE.addDataGenerator(ProviderType.LANG, VoyagerCoreModifierLangProvider::init);
    }
}
