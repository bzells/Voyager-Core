package com.jzells.voyagercore.tools;

import com.jzells.voyagercore.VoyagerCore;
import com.jzells.voyagercore.tools.data.VoyagerCoreModifierLangProvider;
import com.jzells.voyagercore.tools.data.VoyagerCoreModifierProvider;
import com.jzells.voyagercore.tools.data.VoyagerCoreModifierRecipeProvider;
import com.jzells.voyagercore.tools.data.tags.VCModifierTagProvider;
import com.jzells.voyagercore.tools.modifiers.DragonLootingModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class VCTConModifiers {
    private static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(VoyagerCore.MOD_ID);

    @SuppressWarnings("removal")
    public VCTConModifiers(){
        MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    /// Have to add tags to this for tinkers to recognize it correctly. Oh, and datagen.
    public static final StaticModifier<DragonLootingModifier> dragon_looting = MODIFIERS.register("dragon_looting", DragonLootingModifier::new);

    @SubscribeEvent
    void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        boolean server = event.includeServer();
        generator.addProvider(server, new VoyagerCoreModifierProvider(packOutput));
        generator.addProvider(server, new VoyagerCoreModifierRecipeProvider(packOutput));
        generator.addProvider(server, new VCModifierTagProvider(packOutput,event.getExistingFileHelper()));
    }

}
