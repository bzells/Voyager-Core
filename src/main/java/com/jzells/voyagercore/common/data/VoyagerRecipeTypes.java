package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.sound.SoundEntry;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;

import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;

import net.minecraft.client.resources.language.I18n;

import com.jzells.voyagercore.common.data.recipe.helper.HelperAssemblerRecipeLogic;
import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;

public class VoyagerRecipeTypes {

    //
    // public static final GTRecipeType ADVANCED_CALORIE_CONVERSION1 = GTRecipeTypes
    // .register("advanced_calorie_conversion", GTRecipeTypes.MULTIBLOCK)
    // .setEUIO(IO.OUT)
    // .setMaxIOSize(4, 2, 2, 1)
    // .setProgressBar(GuiTextures.PROGRESS_BAR_RECYCLER, ProgressTexture.FillDirection.DOWN_TO_UP)
    // .setSlotOverlay(false, false, GuiTextures.ARROW_INPUT_OVERLAY)
    // .setSound(GTSoundEntries.BATH);

    public static final GTRecipeType ADVANCED_CALORIE_CONVERSION = voyagerRecipeType("advanced_calorie_conversion",
            GTRecipeTypes.MULTIBLOCK, IO.OUT, 1, 1, 0, 1,
            GuiTextures.PROGRESS_BAR_RECYCLER, ProgressTexture.FillDirection.DOWN_TO_UP, GuiTextures.DUST_OVERLAY,
            GTSoundEntries.CHEMICAL);

    public static final GTRecipeType GRANDMAS_BAKING = voyagerRecipeType("grandmas_baking",
            GTRecipeTypes.MULTIBLOCK, IO.IN, 9, 9, 0, 0,
            GuiTextures.PRIMITIVE_FURNACE_OVERLAY, ProgressTexture.FillDirection.LEFT_TO_RIGHT, GuiTextures.BOXED_OVERLAY,
            GTSoundEntries.FURNACE);

    public static final GTRecipeType CELESTIAL_POST_BOX = voyagerRecipeType("celestial_post_box",
            GTRecipeTypes.MULTIBLOCK, IO.IN, 9, 9, 0, 0,
            GuiTextures.PROGRESS_BAR_CANNER, ProgressTexture.FillDirection.UP_TO_DOWN, GuiTextures.BOX_OVERLAY,
            GTSoundEntries.PORTABLE_SCANNER);

    public static final GTRecipeType CHEMICAL_PLANT = GTRecipeTypes
            .register("chemical_plant", GTRecipeTypes.MULTIBLOCK)
            .setEUIO(IO.IN)
            .setMaxIOSize(6, 6, 6, 6)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSlotOverlay(true, false, GuiTextures.VIAL_OVERLAY_1)
            .addDataInfo(tag -> {
                if (tag.contains("ebf_temp")) {
                    return "Required Temperature: " + tag.getInt("ebf_temp") + " K";
                }
                return "";
            })
            .addDataInfo(data -> {
                int temp = data.getInt("ebf_temp");
                ICoilType requiredCoil = ICoilType.getMinRequiredType(temp);

                if (requiredCoil != null && !requiredCoil.getMaterial().isNull()) {
                    return LocalizationUtils.format("gtceu.recipe.coil.tier",
                            I18n.get(requiredCoil.getMaterial().getUnlocalizedName()));
                }
                return "";
            })
            .addDataInfo(data -> {
                String specialized = (data.contains("specialized")) ? data.getString("specialized") : "none";
                return (specialized.equals("none")) ? "" :
                        "Helper Specialization: §6" + VoyagerVoltageTierUtils.helperSpecializationFromData(specialized);
            })
            .addDataInfo(tag -> {
                if (tag.contains("paramount")) {
                    return "Paramount Application Required: " +
                            VoyagerVoltageTierUtils.paramountApplicationFromData(tag.getString("paramount")) +
                            "\nParamount Level Required: " + tag.getInt("paramount_level");
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
            .setSound(GTSoundEntries.ARC)
            .addDataInfo(tag -> {
                if (tag.contains("beam_concentration")) {
                    return "Beam Concentration: " + (tag.getFloat("beam_concentration") * 100) + "%%";
                }
                return "";
            });

    public static final GTRecipeType PULVERIZING = GTRecipeTypes
            .register("pulverizing", GTRecipeTypes.MULTIBLOCK)
            .setEUIO(IO.IN)
            .setMaxIOSize(1, 6, 1, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MACERATE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSlotOverlay(true, false, GuiTextures.CRUSHED_ORE_OVERLAY)
            .addDataInfo(tag -> {
                if (tag.contains("crushing_wheel_tier")) {
                    return "Crushing Wheel Tier: " + GTValues.ALL_TIERS[tag.getInt("crushing_wheel_tier")];
                }
                return "";
            })
            .setSound(GTSoundEntries.MACERATOR);

    public static final GTRecipeType PLANET_EXTRACTING = GTRecipeTypes
            .register("planet_extracting", GTRecipeTypes.MULTIBLOCK)
            .setEUIO(IO.IN)
            .setMaxIOSize(1, 6, 1, 6)
            .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSlotOverlay(true, false, GuiTextures.CRUSHED_ORE_OVERLAY)

            .setSound(GTSoundEntries.JET_ENGINE);

    public static final GTRecipeType COILTRONICS_ASSEMBLY = GTRecipeTypes
            .register("coiltronics_assembly", GTRecipeTypes.MULTIBLOCK)
            .setEUIO(IO.IN)
            .setMaxIOSize(5, 1, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ASSEMBLY_LINE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSlotOverlay(true, false, GuiTextures.BOX_OVERLAY)

            .setSound(GTSoundEntries.ASSEMBLER);

    public static final GTRecipeType HELPER_FACTORY = GTRecipeTypes
            .register("helper_factory", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(9, 1, 1, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ASSEMBLY_LINE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSlotOverlay(true, false, GuiTextures.BOX_OVERLAY)

            .setSound(GTSoundEntries.ASSEMBLER);

    public static final GTRecipeType HELPER_ASSEMBLY = GTRecipeTypes
            .register("helper_assembly", GTRecipeTypes.ELECTRIC)
            .setEUIO(IO.IN)
            .setMaxIOSize(3, 1, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_BENDING, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.ASSEMBLER)
            .addCustomRecipeLogic(new HelperAssemblerRecipeLogic());

    public static final GTRecipeType HELPER_ASSEMBLY_JEI = GTRecipeTypes
            .register("helper_assembly_jei", GTRecipeTypes.DUMMY)
            .setEUIO(IO.IN)
            .setMaxIOSize(2, 1, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_BENDING, ProgressTexture.FillDirection.LEFT_TO_RIGHT);

    public static final GTRecipeType SMD_ASSEMBLY = voyagerRecipeType("smd_assembly", GTRecipeTypes.MULTIBLOCK, IO.IN,
            6, 1, 1, 0,
            GuiTextures.PROGRESS_BAR_BENDING, ProgressTexture.FillDirection.LEFT_TO_RIGHT, GuiTextures.BOX_OVERLAY,
            GTSoundEntries.ASSEMBLER);

    public static final GTRecipeType OVEN = GTRecipeTypes
            .register("oven", GTRecipeTypes.MULTIBLOCK)
            .setEUIO(IO.IN)
            .setMaxIOSize(9, 9, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARC_FURNACE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSlotOverlay(true, false, GuiTextures.BOX_OVERLAY)
            .setMaxTooltips(5)
            .addDataInfo(tag -> {
                if (tag.contains("paramount")) {
                    return "Paramount Application Required: \n" +
                            VoyagerVoltageTierUtils.paramountApplicationFromData(tag.getString("paramount")) +
                            "\nParamount Level Required: " + tag.getInt("paramount_level");
                }
                return "";
            })
            .setSound(GTSoundEntries.ASSEMBLER);

    public static void init() {}

    public static GTRecipeType voyagerRecipeType(String id, String type, IO io, int maxInputs, int maxOutputs,
                                                 int fluidInputs, int fluidOutputs, ResourceTexture pBar,
                                                 ProgressTexture.FillDirection fillDir,
                                                 ResourceTexture guiOutputOverlay, SoundEntry sound) {
        return GTRecipeTypes
                .register(id, type)
                .setEUIO(io)
                .setMaxIOSize(maxInputs, maxOutputs, fluidInputs, fluidOutputs)
                .setProgressBar(pBar, fillDir)
                .setSlotOverlay(true, false, guiOutputOverlay)
                .setMaxTooltips(5)
                .addDataInfo(tag -> {
                    if (tag.contains("paramount")) {
                        return "Paramount Application Required: \n" +
                                VoyagerVoltageTierUtils.paramountApplicationFromData(tag.getString("paramount")) +
                                "\nParamount Level Required: " + tag.getInt("paramount_level");
                    }
                    return "";
                })
                .addDataInfo(data -> {
                    String specialized = (data.contains("specialized")) ? data.getString("specialized") : "none";
                    return (specialized.equals("none")) ? "" :
                            "Helper Specialization: §6" +
                                    VoyagerVoltageTierUtils.helperSpecializationFromData(specialized);
                })
                .setSound(sound);
    }
}
