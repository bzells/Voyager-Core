package com.jzells.voyagercore.common.data;

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
            .setSound(GTSoundEntries.BATH);

    public static final GTRecipeType CHEMICAL_PLANT = GTRecipeTypes
            .register("chemical_plant", GTRecipeTypes.MULTIBLOCK)
            .setEUIO(IO.IN)
            .setMaxIOSize(6, 6, 6, 6)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSlotOverlay(true, false, GuiTextures.VIAL_OVERLAY_1)
            .addDataInfo(tag -> {
                if (tag.contains("ebf_temp")) {
                    return "Temperature: " + tag.getInt("ebf_temp") + " K";
                }
                return "";
            })
            .setSound(GTSoundEntries.CHEMICAL);

    public static final GTRecipeType BEAM_HEATING = GTRecipeTypes
            .register("beam_heating", GTRecipeTypes.MULTIBLOCK)
            .setEUIO(IO.IN)
            .setMaxIOSize(2, 1, 1, 1)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARC_FURNACE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSlotOverlay(false, false, GuiTextures.LENS_OVERLAY)
            .setSound(GTSoundEntries.ARC);

    public static void init() {}
}
