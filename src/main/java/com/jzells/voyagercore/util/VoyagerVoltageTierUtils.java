package com.jzells.voyagercore.util;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterialItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.utils.GTUtil;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.level.block.Block;

import javax.annotation.CheckForNull;
import java.util.Objects;

public class VoyagerVoltageTierUtils {

    public static float getParallelMultiplierForSequentialRecipeModifier(GTRecipe recipe, int parallels) {
        int existingParallels = recipe.parallels;

        if (existingParallels <= 1) {
            return parallels;
        }

        return (float) (existingParallels + parallels) / existingParallels;
    }

    public static ModifierFunction getModifierFunctionWithParallels(GTRecipe recipe, int pars, float outputMod,
                                                                    float eutMod, float speed) {
        int recipePars = recipe.parallels;

        float parMultiplier = getParallelMultiplierForSequentialRecipeModifier(recipe, pars);

        return ModifierFunction.builder()
                .outputModifier(ContentModifier.multiplier(outputMod * parMultiplier))
                .inputModifier(ContentModifier.multiplier(parMultiplier))
                .eutMultiplier(eutMod)
                .durationMultiplier(speed)
                .parallels((int) Math.ceil(parMultiplier))
                .build();
    }

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

    /*

    When using, make sure the material actually has a frame.

     */
    @CheckForNull
    public static BlockEntry<?> getFrameBlock(Material material)
    {
        return GTMaterialBlocks.MATERIAL_BLOCKS
                .get(TagPrefix.frameGt, material);
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
            case "smd_assembly" -> "§bSMD Assembly";
            case "petrochem" -> "§6Petrochem";
            default -> dat;
        };
    }

    public static String helperRecipeFromID(String id) {
        return switch (id) {
            case "gtceu:electric_blast_furnace" -> "§eElectric Blast Furnace";
            default -> id;
        };
    }

    public static String paramountApplicationFromData(String id) {
        return switch (id) {
            case "coiltronics" -> "§6Coiltronics";
            case "grandma" -> "§6Grandma";
            case "embassy" -> "§9Embassy";
            case "chemist" -> "§bThe Chemist";
            case "hungry" -> "§2Hungry";
            default -> id;
        };
    }

    public static ItemEntry getMatieralItem(TagPrefix tagPrefix, Material gtMaterials) {
        return Objects.requireNonNull(GTMaterialItems.MATERIAL_ITEMS.get(
                tagPrefix, gtMaterials));
    }

    public static int darkenAndSaturateHex(int color, double darkenFactor, double saturationBoost) {
        // Extract RGB and darken
        int rInt = (color >> 16) & 0xFF;
        int gInt = (color >> 8) & 0xFF;
        int bInt = color & 0xFF;

        double r = (rInt / 255.0) * darkenFactor;
        double g = (gInt / 255.0) * darkenFactor;
        double b = (bInt / 255.0) * darkenFactor;

        // Clamp
        r = Math.min(1.0, Math.max(0.0, r));
        g = Math.min(1.0, Math.max(0.0, g));
        b = Math.min(1.0, Math.max(0.0, b));

        // RGB -> HSL
        double max = Math.max(r, Math.max(g, b));
        double min = Math.min(r, Math.min(g, b));

        double h;
        double s;
        double l = (max + min) / 2.0;

        if (max == min) {
            h = 0;
            s = 0;
        } else {
            double d = max - min;

            s = l > 0.5 ? d / (2.0 - max - min) : d / (max + min);

            if (max == r) {
                h = (g - b) / d + (g < b ? 6 : 0);
            } else if (max == g) {
                h = (b - r) / d + 2;
            } else {
                h = (r - g) / d + 4;
            }

            h /= 6.0;
        }

        // Increase saturation
        s = Math.min(1.0, s + saturationBoost);

        // HSL -> RGB
        double rOut;
        double gOut;
        double bOut;

        if (s == 0) {
            rOut = gOut = bOut = l;
        } else {
            double q = l < 0.5 ? l * (1.0 + s) : l + s - l * s;

            double p = 2.0 * l - q;

            rOut = hueToRgb(p, q, h + 1.0 / 3.0);
            gOut = hueToRgb(p, q, h);
            bOut = hueToRgb(p, q, h - 1.0 / 3.0);
        }

        // Convert back to integers
        int rResult = (int) Math.round(rOut * 255);
        int gResult = (int) Math.round(gOut * 255);
        int bResult = (int) Math.round(bOut * 255);

        return (rResult << 16) | (gResult << 8) | bResult;
    }

    private static double hueToRgb(double p, double q, double t) {
        if (t < 0)
            t += 1;

        if (t > 1)
            t -= 1;

        if (t < 1.0 / 6.0)
            return p + (q - p) * 6.0 * t;

        if (t < 1.0 / 2.0)
            return q;

        if (t < 2.0 / 3.0)
            return p + (q - p) * (2.0 / 3.0 - t) * 6.0;

        return p;
    }
}
