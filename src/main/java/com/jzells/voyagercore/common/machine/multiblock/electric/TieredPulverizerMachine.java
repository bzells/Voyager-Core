package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;

import com.jzells.voyagercore.common.machine.multiblock.part.CrushingWheelPartMachine;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TieredPulverizerMachine extends FluidBasicMulti implements ITieredMachine {

    public TieredPulverizerMachine(IMachineBlockEntity holder, FluidStack fluidStack) {
        super(holder, fluidStack);
    }

    protected int tier;
    protected boolean valid;

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();

        this.valid = true;

        Integer referenceTier = null;

        for (IMultiPart part : getParts()) {
            if (part instanceof CrushingWheelPartMachine wheel) {

                int tier = wheel.getCrushingTier();

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
        if (!(machine instanceof TieredPulverizerMachine pulverizer)) {
            return RecipeModifier.nullWrongType(TieredPulverizerMachine.class, machine);
        }

        if (pulverizer.valid) {

            int recipeTier = recipe.data.getInt("crushing_wheel_tier");
            int pulverizerTier = pulverizer.tier;

            if (recipeTier > pulverizerTier) {
                return ModifierFunction.NULL;
            }

            int crushOC = pulverizerTier - recipeTier;

            return ModifierFunction.builder()
                    .outputModifier(ContentModifier.multiplier(crushOC + 1))
                    .build();

        }

        return ModifierFunction.NULL;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        if (this.valid) {
            textList.add(Component.literal("Crushing Wheel Tier: §4" + this.tier));
        } else {
            textList.add(Component.literal("Crushing wheels are §4mismatched, structure not valid"));
        }

        super.addDisplayText(textList);
    }

    private int getCrushingOverclock(@NotNull GTRecipe recipe, TieredPulverizerMachine pulverizer) {
        return pulverizer.tier - recipe.data.getInt("crushing_wheel_tier");
    }
}
