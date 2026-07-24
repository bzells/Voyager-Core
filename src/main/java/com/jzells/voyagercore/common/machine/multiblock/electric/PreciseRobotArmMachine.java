package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.jzells.voyagercore.common.machine.multiblock.part.CrushingWheelPartMachine;
import com.jzells.voyagercore.common.machine.multiblock.part.PreciseRobotArmPartMachine;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.jzells.voyagercore.VoyagerCore.LOGGER;

public class PreciseRobotArmMachine extends WorkableElectricMultiblockMachine {
    public PreciseRobotArmMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    private boolean valid = false;
    protected int tier;


    @Override
    public void onStructureFormed() {
        super.onStructureFormed();

        this.valid = true;

        Integer referenceTier = null;

        for (IMultiPart part : getParts()) {
            if (part instanceof PreciseRobotArmPartMachine preciseRobotArmPartMachine) {

                int tier = preciseRobotArmPartMachine.getTier();

                if (referenceTier == null) {
                    referenceTier = tier;
                    this.tier = tier;

                } else if (tier != referenceTier) {
                    this.valid = false;
                    return;
                }
            }
        }
    }

    public static ModifierFunction recipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof PreciseRobotArmMachine preciseRobotArmMachine)) {
            return RecipeModifier.nullWrongType(PreciseRobotArmMachine.class, machine);
        }

        int recipeTier = recipe.data.getInt("robot_arm_tier");

        if(preciseRobotArmMachine.tier >= recipeTier && preciseRobotArmMachine.valid)
        {
            return ModifierFunction.IDENTITY;
        }



        return ModifierFunction.cancel(Component.literal("Precise Robot Arm Boxes are too low tier\n or not matching"));
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        if (this.valid) {
            textList.add(Component.literal("Tier: §4" + this.tier));
        } else {
            textList.add(Component.literal("Precise Robot Arm Boxes are §4mismatched, structure not valid"));
        }

        super.addDisplayText(textList);
    }

}
