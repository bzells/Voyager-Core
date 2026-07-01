package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.cover.CoverBehavior;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import net.minecraft.network.chat.Component;

import com.jzells.voyagercore.common.data.VoyagerMaterials;
import com.jzells.voyagercore.common.machine.cover.HeatRedstoneCover;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BeamMachine extends WorkableElectricMultiblockMachine {

    private float beamConcentration = 0;
    private float usedBeamConcentration = 0;
    private float heat = 0;
    int runningTimer = 0;
    int waitingTimer = 0;
    int interval = 200;
    private float baseHeat;

    public BeamMachine(IMachineBlockEntity holder, float baseBeamConcentration, float baseHeat) {
        super(holder);
        this.beamConcentration = baseBeamConcentration;
        this.heat = baseHeat;
        this.baseHeat = baseHeat;
    }

    @Override
    public boolean onWorking() {
        boolean val = super.onWorking();

        ++this.runningTimer;
        if (this.runningTimer > interval) {
            this.runningTimer %= interval;
        }

        if (this.runningTimer % interval == 0) {

            GTRecipe coolantRecipe = this.getCoolantRecipe();

            if (RecipeHelper.matchRecipe(this, coolantRecipe).isSuccess() &&
                    RecipeHelper.handleRecipeIO(this, coolantRecipe, IO.IN, this.recipeLogic.getChanceCaches())
                            .isSuccess()) {
                this.heat = (float) Math.max(this.heat - 0.02, this.baseHeat);
            } else {
                if (this.heat < 1.1)
                    this.heat += 0.02F;
            }
        }

        return val;
    }

    public float getBeamConcentration() {
        return usedBeamConcentration;
    }

    protected GTRecipe getCoolantRecipe() {
        return GTRecipeBuilder.ofRaw().inputFluids(VoyagerMaterials.Cryotheum.getFluid(100)).buildRawRecipe();
    }

    public static ModifierFunction recipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof BeamMachine beamMachine)) {
            return RecipeModifier.nullWrongType(BeamMachine.class, machine);
        }

        for (CoverBehavior cover : beamMachine.getCoverContainer().getCovers()) {
            if (cover instanceof HeatRedstoneCover heatRedstoneCover) {
                heatRedstoneCover.setHeat(beamMachine.heat);
                System.out.println("Heat:" + beamMachine.heat);
            }
        }

        beamMachine.usedBeamConcentration = beamMachine.beamConcentration;

        long machineEUt = beamMachine.getEnergyContainer().getHighestInputVoltage();
        long recipeEUt = recipe.getInputEUt().voltage();
        int eutMult = 1;

        if (recipeEUt > 0 && machineEUt >= 4 * recipeEUt) {
            int steps = 0;

            long value = machineEUt / recipeEUt;
            while (value >= 4) {
                value /= 4;
                steps++;
                eutMult *= 4;
            }
            if (beamMachine.heat <= .9f) {
                beamMachine.usedBeamConcentration += steps * 0.3f;
            }

        }

        // heat applies AFTER overclock
        beamMachine.usedBeamConcentration = beamMachine.applyHeatToConcentration(beamMachine.usedBeamConcentration);

        if (beamMachine.heat >= 1.05f) {
            return ModifierFunction.builder()
                    .outputModifier(ContentModifier.multiplier(0))
                    .durationMultiplier(beamMachine.getRecipeTimeMultiplier())
                    .eutMultiplier(beamMachine.getEUtMultiplier() * eutMult)
                    .build();
        }

        return ModifierFunction.builder()
                .eutMultiplier(beamMachine.getEUtMultiplier() * eutMult)
                .durationMultiplier(beamMachine.getRecipeTimeMultiplier())
                .build();
    }

    public float getHeat() {
        return heat;
    }

    private float getEUtMultiplier() {
        float EUtMultiplier = 1.0f;

        if (heat > 0.1f) {
            int heatSteps = Math.min((int) ((heat - 0.1f) / 0.1f), 8);
            EUtMultiplier -= heatSteps * 0.08f;
        }

        return EUtMultiplier;
    }

    private float getBeamConcentrationAfterHeat() {
        float heat = this.heat;
        float concentration = this.usedBeamConcentration;
        if (heat > 0.9f) {
            int concentrationSteps = (int) ((heat - 0.9f) / 0.1f);
            concentration *= (1.0f - concentrationSteps * 0.07f);
        }

        return concentration;
    }

    private float getRecipeTimeMultiplier() {
        float concentration = this.usedBeamConcentration;
        float multiplier = 1.0f;

        if (concentration > 0.1f) {
            int steps = (int) ((concentration - 0.1f) / 0.05f);
            multiplier *= (float) Math.pow(0.85f, steps);
        }

        return multiplier;
    }

    private float applyHeatToConcentration(float concentration) {
        if (heat <= 0.9f) return concentration;

        for (float i = .9f; i < this.heat; i += 0.01F) {
            concentration -= 0.07f;
        }

        return Math.max(this.beamConcentration, concentration);
    }

    @Override
    public void onWaiting() {
        super.onWaiting();
        ++this.waitingTimer;
        if (this.waitingTimer > interval) {
            this.waitingTimer %= interval;
        }
        if (this.waitingTimer % interval == 0) {
            this.heat = (float) Math.max(this.heat - 0.02, this.baseHeat);
        }
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        textList.add(Component.literal("Lens Heat: %.2f%%".formatted(this.heat * 100)));
        textList.add(Component.literal("Beam Concentration: %.2f%%".formatted(this.usedBeamConcentration * 100)));
        super.addDisplayText(textList);
    }
}
