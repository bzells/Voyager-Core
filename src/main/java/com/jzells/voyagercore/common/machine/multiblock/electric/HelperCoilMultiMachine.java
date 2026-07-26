package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.jzells.voyagercore.common.machine.multiblock.part.HelperHolderPartMachine;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class HelperCoilMultiMachine extends CoilWorkableElectricMultiblockMachine {

    public static HelperHolderPartMachine helperHolder;
    @Getter
    private static boolean isSpecialized;

    public HelperCoilMultiMachine(IMachineBlockEntity holder, boolean specialized) {
        super(holder);
        isSpecialized = specialized;
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
