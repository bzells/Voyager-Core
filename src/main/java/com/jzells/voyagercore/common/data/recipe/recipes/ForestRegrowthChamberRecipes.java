package com.jzells.voyagercore.common.data.recipe.recipes;

import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ForestRegrowthChamberRecipes {

    public static final void init(Consumer<FinishedRecipe> provider) {
        forestRegrowthChamberRecipes(provider);
    }

    public static void forestRegrowthChamberRecipes(Consumer<FinishedRecipe> provider) {
        List<Item> logs = new ArrayList<>();
        List<Item> saplings = new ArrayList<>();

        logs.add(Items.OAK_LOG);
        logs.add(Items.ACACIA_LOG);
        logs.add(Items.BIRCH_LOG);
        logs.add(Items.CHERRY_LOG);
        logs.add(Items.JUNGLE_LOG);
        logs.add(Items.DARK_OAK_LOG);
        logs.add(Items.MANGROVE_LOG);
        logs.add(Items.SPRUCE_LOG);

        saplings.add(Items.OAK_SAPLING);
        saplings.add(Items.ACACIA_SAPLING);
        saplings.add(Items.BIRCH_SAPLING);
        saplings.add(Items.CHERRY_SAPLING);
        saplings.add(Items.JUNGLE_SAPLING);
        saplings.add(Items.DARK_OAK_SAPLING);
        saplings.add(Items.MANGROVE_PROPAGULE);
        saplings.add(Items.SPRUCE_SAPLING);

        for (int i = 0; i < 8; i++) {
            VoyagerRecipeTypes.FOREST_REGROWTH_CHAMBER
                    .recipeBuilder("forest_regrowth_chamber_" + logs.get(i).toString())
                    .notConsumable(saplings.get(i))
                    .outputItems(new ItemStack(logs.get(i), 16))
                    .circuitMeta(1)
                    .EUt(480)
                    .duration(16 * 20)
                    .save(provider);

            VoyagerRecipeTypes.FOREST_REGROWTH_CHAMBER
                    .recipeBuilder("forest_regrowth_chamber_" + logs.get(i).toString() + "_co2")
                    .notConsumable(saplings.get(i))
                    .outputItems(new ItemStack(logs.get(i), 48))
                    .inputFluids(GTMaterials.CarbonDioxide.getFluid(4000))
                    .outputFluids(GTMaterials.Oxygen.getFluid(1000))
                    .circuitMeta(2)
                    .EUt(480)
                    .duration(16 * 20)
                    .save(provider);

            VoyagerRecipeTypes.FOREST_REGROWTH_CHAMBER
                    .recipeBuilder("forest_regrowth_chamber_" + logs.get(i).toString() + "_fertilizer")
                    .notConsumable(saplings.get(i))
                    .inputItems(new ItemStack(GTItems.FERTILIZER, 4))
                    .outputItems(new ItemStack(logs.get(i), 16))
                    .circuitMeta(3)
                    .EUt(480)
                    .duration(8 * 20)
                    .save(provider);

            VoyagerRecipeTypes.FOREST_REGROWTH_CHAMBER
                    .recipeBuilder("forest_regrowth_chamber_" + logs.get(i).toString() + "_co2" + "_fertilizer")
                    .notConsumable(saplings.get(i))
                    .outputItems(new ItemStack(logs.get(i), 48))
                    .inputItems(new ItemStack(GTItems.FERTILIZER, 4))
                    .inputFluids(GTMaterials.CarbonDioxide.getFluid(4000))
                    .outputFluids(GTMaterials.Oxygen.getFluid(1000))
                    .circuitMeta(4)
                    .EUt(480)
                    .duration(8 * 20)
                    .save(provider);
        }
    }
}
