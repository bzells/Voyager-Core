package com.jzells.voyagercore;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import net.minecraft.data.recipes.FinishedRecipe;

import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;
import com.jzells.voyagercore.common.data.recipe.recipes.VoyagerRecipes;
import com.jzells.voyagercore.common.machine.cover.HeatRedstoneCoverDefinition;

import java.util.function.Consumer;

@SuppressWarnings("unused")
@GTAddon
public class VoyagerGTAddon implements IGTAddon {

    @Override
    public GTRegistrate getRegistrate() {
        return VoyagerCore.VOYAGERCORE_REGISTRATE;
    }

    @Override
    public void initializeAddon() {}

    @Override
    public String addonModId() {
        return VoyagerCore.MOD_ID;
    }

    @Override
    public void registerTagPrefixes() {
        // CustomTagPrefixes.init();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        VoyagerRecipeTypes.init();
        VoyagerRecipes.init(provider);
    }

    @Override
    public void registerElements() {
        // CustomElements.init();
    }

    @Override
    public void registerCovers() {
        HeatRedstoneCoverDefinition.registerAll();
    }

    // If you have custom ingredient types, uncomment this & change to match your capability.
    // KubeJS WILL REMOVE YOUR RECIPES IF THESE ARE NOT REGISTERED.
    /*
     * public static final ContentJS<Double> PRESSURE_IN = new ContentJS<>(NumberComponent.ANY_DOUBLE,
     * CustomRecipeCapabilities.PRESSURE, false);
     * public static final ContentJS<Double> PRESSURE_OUT = new ContentJS<>(NumberComponent.ANY_DOUBLE,
     * CustomRecipeCapabilities.PRESSURE, true);
     * 
     * @Override
     * public void registerRecipeKeys(KJSRecipeKeyEvent event) {
     * event.registerKey(CustomRecipeCapabilities.PRESSURE, Pair.of(PRESSURE_IN, PRESSURE_OUT));
     * }
     */
}
