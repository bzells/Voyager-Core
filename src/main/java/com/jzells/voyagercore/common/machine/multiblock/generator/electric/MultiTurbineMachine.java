package com.jzells.voyagercore.common.machine.multiblock.generator.electric;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.ITurbineMachine;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.ITieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IRotorHolderMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.ingredient.EnergyStack;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import com.jzells.voyagercore.VoyagerCore;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultiTurbineMachine extends WorkableElectricMultiblockMachine implements ITurbineMachine, ITieredMachine {

    public static final int MIN_DURABILITY_TO_WARN = 10; // Copy of psf from LTM, because it can be changed.
    private final long BASE_EU_OUTPUT;
    @Getter
    private final int tier;

    // protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
    // MultiTurbineMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);

    public MultiTurbineMachine(IMachineBlockEntity holder, int tier) {
        super(holder);
        this.tier = tier;
        this.BASE_EU_OUTPUT = GTValues.V[tier] * 2;
    }

    private ArrayList<IRotorHolderMachine> getRotorHolders() {
        var rotorHolders = new ArrayList<IRotorHolderMachine>();
        for (IMultiPart part : getParts()) {
            if (part instanceof IRotorHolderMachine rotorHolder) {
                rotorHolders.add(rotorHolder);
            }
        }
        return rotorHolders;
    }

    @Override
    public long getOverclockVoltage() {
        var rotorHolders = getRotorHolders();
        long total;
        if (rotorHolders.isEmpty()) return 0;
        total = rotorHolders.stream().mapToInt(IRotorHolderMachine::getTotalPower).sum() /
                (100 * (long) getRotorCount());
        return total * BASE_EU_OUTPUT;
    }

    // Shutup.
    private double productionRotorBoost(IRotorHolderMachine rotorHolder) {
        int maxSpeed = rotorHolder.getMaxRotorHolderSpeed();
        int currentSpeed = rotorHolder.getRotorSpeed();
        if (currentSpeed >= maxSpeed) return 1;
        return Math.pow(1.0 * currentSpeed / maxSpeed, 2);
    }

    protected double productionBoost() {
        var rotorHolders = getRotorHolders();
        if (rotorHolders.isEmpty()) return 0;
        return rotorHolders.stream()
                .filter(IRotorHolderMachine::hasRotor)
                .mapToDouble(this::productionRotorBoost)
                .reduce(1.0, (a, b) -> a * b);
        // ^^ This could result in practically 0 for speeds >0, also I hate computers.
    }

    public int getRotorCount() {
        var rotorHolders = getRotorHolders();
        int count = 0;
        if (rotorHolders.isEmpty()) return 0;
        for (IRotorHolderMachine holder : rotorHolders) {
            if (holder.hasRotor()) count++;
        }
        return count;
    }

    @Override
    public boolean hasRotor() {
        return getRotorCount() > 0;
    }

    /***
     * @return Sum Speed of all rotors, or zero if no rotors.
     */
    @Override
    public int getRotorSpeed() {
        var rotorHolders = getRotorHolders();
        int count = 0;
        if (rotorHolders.isEmpty()) return 0;
        for (IRotorHolderMachine holder : rotorHolders) {
            if (holder.hasRotor()) count += holder.getRotorSpeed();
        }
        return count;
    }

    @Override
    public int getMaxRotorHolderSpeed() {
        return 0;
    }

    @Override
    public int getTotalEfficiency() {
        return 0;
    }

    @Override
    public long getCurrentProduction() {
        return isActive() && recipeLogic.getLastRecipe() != null ?
                recipeLogic.getLastRecipe().getOutputEUt().voltage() : 0;
    }

    @Override
    public int getRotorDurabilityPercent() {
        return 0;
    }

    public static ModifierFunction recipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof MultiTurbineMachine turbineMachine)) {
            return RecipeModifier.nullWrongType(MultiTurbineMachine.class, machine);
        }

        var rotorHolders = turbineMachine.getRotorHolders();
        if (rotorHolders.isEmpty()) return ModifierFunction.NULL;

        EnergyStack EUt = recipe.getOutputEUt();
        long turbineMaxVoltage = turbineMachine.getOverclockVoltage();
        double holderEfficiency = 1.5 * rotorHolders.stream()
                .map(h -> (double) h.getTotalEfficiency())
                .reduce(0.0, Double::sum) / (100 * Math.max(turbineMachine.getRotorCount(), 1));
        VoyagerCore.LOGGER.info("Duration modifier: {}", holderEfficiency);

        if (EUt.isEmpty() || turbineMaxVoltage <= EUt.voltage() || holderEfficiency <= 0) return ModifierFunction.NULL;

        int maxParallel = (int) (turbineMaxVoltage / EUt.getTotalEU());
        if (turbineMaxVoltage % EUt.getTotalEU() != 0) maxParallel++;

        int actualParallel = ParallelLogic.getParallelAmountFast(turbineMachine, recipe, maxParallel);
        double eutMultiplier = (maxParallel == actualParallel) ?
                turbineMachine.productionBoost() * turbineMaxVoltage / EUt.voltage() :
                turbineMachine.productionBoost() * actualParallel;

        return ModifierFunction.builder()
                .inputModifier(ContentModifier.multiplier(actualParallel))
                .outputModifier(ContentModifier.multiplier(actualParallel))
                .eutMultiplier(eutMultiplier)
                .parallels(actualParallel)
                .durationMultiplier(holderEfficiency)
                .build();

        // return ModifierFunction.IDENTITY; //TODO work on this. too tired
    }

    @Override
    public boolean regressWhenWaiting() {
        return false;
    }

    @Override
    public boolean canVoidRecipeOutputs(RecipeCapability<?> capability) {
        return true;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (isFormed()) {
            var rotorHolders = getRotorHolders();
            if (rotorHolders.isEmpty()) return;

            List<IRotorHolderMachine> filtered = rotorHolders.stream()
                    .filter(r -> r.getTotalEfficiency() > 0)
                    .toList();

            if (filtered.isEmpty()) return;

            for (IRotorHolderMachine rotorHolder : filtered) {
                textList.add(Component.translatable("gtceu.multiblock.turbine.rotor_speed",
                        FormattingUtil.formatNumbers(rotorHolder.getRotorSpeed()),
                        FormattingUtil.formatNumbers(rotorHolder.getMaxRotorHolderSpeed())));
                textList.add(Component.translatable("gtceu.multiblock.turbine.efficiency",
                        rotorHolder.getTotalEfficiency()));

                int rotorDurability = rotorHolder.getRotorDurabilityPercent();
                if (rotorDurability > MIN_DURABILITY_TO_WARN) {
                    textList.add(Component.translatable("gtceu.multiblock.turbine.rotor_durability", rotorDurability));
                } else {
                    textList.add(Component.translatable("gtceu.multiblock.turbine.rotor_durability", rotorDurability)
                            .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
                }
            }

            long maxProduction = getOverclockVoltage();
            long currentProduction = getCurrentProduction();

            if (isActive()) {
                textList.add(3, Component.translatable("gtceu.multiblock.turbine.energy_per_tick",
                        FormattingUtil.formatNumbers(currentProduction),
                        FormattingUtil.formatNumbers(maxProduction)));
            }

        }
    }
}
