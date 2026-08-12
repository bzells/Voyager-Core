package com.jzells.voyagercore.common.data.recipe.helper;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import com.jzells.voyagercore.common.machine.multiblock.part.HelperHolderPartMachine;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ParamountHelperRecipeLogic implements GTRecipeType.ICustomRecipeLogic {

    @Override
    public @Nullable GTRecipe createCustomRecipe(IRecipeCapabilityHolder holder) {
        List<IRecipeHandler<?>> handlers = holder.getCapabilitiesFlat(IO.IN, ItemRecipeCapability.CAP);

        HelperHolderPartMachine helperHolder = null;

        if (holder instanceof WorkableElectricMultiblockMachine machine) {
            for (IMultiPart part : machine.getParts()) {
                if (part instanceof HelperHolderPartMachine h) {
                    helperHolder = h;
                    break;
                }
            }
        }

        if (!helperHolder.getHelperIsParamount()) return null;

        return null;
    }
}
