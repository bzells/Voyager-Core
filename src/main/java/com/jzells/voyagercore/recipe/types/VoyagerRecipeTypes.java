package com.jzells.voyagercore.recipe.types;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;

import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;

public class VoyagerRecipeTypes {

    public static final GTRecipeType ADVANCED_CALORIE_CONVERSION = GTRecipeTypes
            .register("advanced_calorie_conversion", GTRecipeTypes.MULTIBLOCK)
            .setEUIO(IO.OUT)
            .setMaxIOSize(4, 2, 2, 1)
            .setProgressBar(GuiTextures.PROGRESS_BAR_RECYCLER, ProgressTexture.FillDirection.DOWN_TO_UP)
            .setSlotOverlay(false, false, GuiTextures.ARROW_INPUT_OVERLAY)
            .setSound(GTSoundEntries.MORTAR_TOOL);

    public static void init() {}
}
