package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;

public class ApiaryMachine extends WorkableElectricMultiblockMachine {
    public ApiaryMachine(IMachineBlockEntity holder){
        super(holder);
    }

    public static class ApiaryRecipeLogic extends RecipeLogic {
        public ApiaryRecipeLogic(ApiaryMachine machine){
            super(machine);
        }


    }
}

//TODO: RecipeLogic for this hunk of crap. Should be relatively Easy(tm) to create a recipe map
//Issue arrises if we want this to be able to breed bees as well.
