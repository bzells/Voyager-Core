package com.jzells.voyagercore.common.data.recipe.recipes;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;

import net.minecraft.data.recipes.FinishedRecipe;

import com.jzells.voyagercore.common.data.VoyagerItems;
import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;
import com.jzells.voyagercore.common.item.component.HelperComponentItem;
import com.jzells.voyagercore.common.item.component.HelperItemComponent;
import com.jzells.voyagercore.util.VoyagerTags;
import com.tterrag.registrate.util.entry.ItemEntry;

import java.util.function.Consumer;

public class DummyRecipes {

    public static final void init(Consumer<FinishedRecipe> provider) {
        helperAssemblyJei(provider);
    }

    // TagKey<Item> HELPER_MODULES = VoyagerTags.HELPER_MODULES;

    public static void helperAssemblyJei(Consumer<FinishedRecipe> provider) {
        for (ItemEntry hull : VoyagerItems.HELPER_HULLS.values()) {
            if (hull.get() instanceof ComponentItem componentItem)
                if (componentItem instanceof HelperComponentItem helperItemComponent) {
                    for (IItemComponent comp : helperItemComponent.getComponents()) {
                        if (comp instanceof HelperItemComponent helpercomponent) {
                            VoyagerRecipeTypes.HELPER_ASSEMBLY_JEI
                                    .recipeBuilder("hull_to_helper_" + hull.get().toString())
                                    .inputItems(hull)
                                    .inputItems(VoyagerTags.HELPER_MODULES)
                                    .outputItems(VoyagerItems.HULL_TO_HELPER.get(hull))
                                    .duration(5 * 20)
                                    .EUt(GTValues.V[helpercomponent.getTier()])
                                    .save(provider);
                        }
                    }

                }

        }

        for (ItemEntry helper : VoyagerItems.HELPERS.values()) {
            if (helper.get() instanceof ComponentItem componentItem)
                if (componentItem instanceof HelperComponentItem helperItemComponent) {
                    for (IItemComponent comp : helperItemComponent.getComponents()) {
                        if (comp instanceof HelperItemComponent helpercomponent) {
                            VoyagerRecipeTypes.HELPER_ASSEMBLY_JEI
                                    .recipeBuilder("helper_to_helper_" + helper.get().toString())
                                    .inputItems(helper)
                                    .inputItems(VoyagerTags.HELPER_MODULES)
                                    .outputItems(helper)
                                    .duration(5 * 20)
                                    .EUt(GTValues.V[helpercomponent.getTier()])
                                    .save(provider);
                        }
                    }

                }

        }
    }
}
