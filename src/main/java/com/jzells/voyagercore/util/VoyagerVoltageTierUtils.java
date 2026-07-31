package com.jzells.voyagercore.util;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTMaterialItems;
import com.gregtechceu.gtceu.utils.GTUtil;

import com.tterrag.registrate.util.entry.ItemEntry;

import java.util.Objects;

public class VoyagerVoltageTierUtils {

    public static int getExactVoltageTier(long voltage) {
        return GTUtil.getTierByVoltage(voltage);
    }

    public static int getOverclockCount(long recipeVolts, long machineVolts) {
        return getExactVoltageTier(machineVolts) - getExactVoltageTier(recipeVolts);
    }

    public static int getOverclockCount(GTRecipe recipe, WorkableElectricMultiblockMachine machine) {
        return getExactVoltageTier(machine.getMaxVoltage()) - getExactVoltageTier(recipe.getInputEUt().voltage());
    }

    public static String getVoltageTierColorStringShortForm(String voltage) {
        for (VoltageColorTable tier : VoltageColorTable.values()) {
            if (tier.tier.equalsIgnoreCase(voltage)) {
                return tier.color + tier.tier + "§r";
            }
        }
        return voltage;
    }

    public enum VoltageColorTable {

        ULV("ULV", "§8"),
        LV("LV", "§7"),
        MV("MV", "§b"),
        HV("HV", "§6"),
        EV("EV", "§5"),
        IV("IV", "§9"),
        LuV("LuV", "§d"),
        ZPM("ZPM", "§4"),
        UV("UV", "§2"),
        UHV("UHV", "§f");

        public final String tier;
        public final String color;

        VoltageColorTable(String tier, String color) {
            this.tier = tier;
            this.color = color;
        }
    }

    public static String helperSpecializationFromData(String dat) {
        return switch (dat) {
            case "plat_line" -> "§ePlat Line";
            case "desh_line" -> "§6Desh Line";
            default -> dat;
        };
    }

    public static String helperRecipeFromID(String id) {
        return switch (id) {
            case "gtceu:electric_blast_furnace" -> "§eElectric Blast Furnace";
            case "desh_line" -> "§6Desh Line";
            default -> id;
        };
    }

    public static ItemEntry getMatieralItem(TagPrefix tagPrefix, Material gtMaterials) {
        return Objects.requireNonNull(GTMaterialItems.MATERIAL_ITEMS.get(
                tagPrefix, gtMaterials));
    }
}
