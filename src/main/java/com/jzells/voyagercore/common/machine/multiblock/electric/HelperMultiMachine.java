package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

import net.minecraft.network.chat.Component;

import com.jzells.voyagercore.common.machine.multiblock.part.HelperHolderPartMachine;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HelperMultiMachine extends WorkableElectricMultiblockMachine {

    public static HelperHolderPartMachine helperHolder;
    @Getter
    private static boolean isSpecialized;

    public HelperMultiMachine(IMachineBlockEntity holder, boolean isSpecialized, Object... args) {
        super(holder, args);
        this.isSpecialized = isSpecialized;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        initPartsList();
    }

    // Only one helper holder

    public void initPartsList() {
        for (IMultiPart part : getParts()) {
            if (part instanceof HelperHolderPartMachine h) {
                helperHolder = h;
                return;
            }
        }
    }

    public static ModifierFunction recipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof HelperCoilMultiMachine helperMultiMachine)) {
            return RecipeModifier.nullWrongType(HelperCoilMultiMachine.class, machine);
        }

        ArrayList<String> helperRecipes = helperHolder.getRecipes();

        if (helperRecipes == null) {
            return ModifierFunction.cancel(Component.literal("Helper has no recipes installed"));
        }

        if (!helperRecipes.contains(recipe.recipeType.toString()))
            return ModifierFunction.cancel(Component.literal("Helper is not compatible with this recipe"));

        int pars = helperHolder.getHelperParallels();
        float eutMod = 1 / helperHolder.getHelperEUt();
        float speed = 1 / helperHolder.getHelperSpeed();

        if (isSpecialized()) {
            // get recipe, match it to GTRecipe in params
            return ModifierFunction.IDENTITY;
        } else {
            return ModifierFunction.builder()
                    .modifyAllContents(ContentModifier.multiplier(pars))
                    .eutMultiplier(eutMod)
                    .durationMultiplier(speed)
                    .parallels(pars)
                    .build();
        }
    }
}
