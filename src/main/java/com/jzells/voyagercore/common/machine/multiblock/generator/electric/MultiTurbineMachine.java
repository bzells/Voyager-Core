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
    private final double EFFICIENCY_BOOST;
    @Getter
    private final int tier;
    @Getter
    private List<IRotorHolderMachine> rotorHolders = new ArrayList<>();

    // protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
    // MultiTurbineMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);

    public MultiTurbineMachine(IMachineBlockEntity holder, int tier){
        this(holder, tier,1.5);
    }

    /**
     *
     * @param holder Used in Builder
     * @param tier Needs to be defined
     * @param efficiency Base boost to efficiency, if omitted, defaults to 1.5
     */
    public MultiTurbineMachine(IMachineBlockEntity holder, int tier, double efficiency) {
        super(holder);
        this.tier = tier; //This is needed for IRotorHolderMachine, since the tier in WEMM is based on energyHatch
        this.BASE_EU_OUTPUT = (long) (GTValues.VEX[tier] * 1.5);
        this.EFFICIENCY_BOOST = efficiency;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        setRotorHolders();
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        this.rotorHolders.clear();
    }

    @Override
    public void onPartUnload() {
        super.onPartUnload();
        this.rotorHolders.clear();
    }

    private void setRotorHolders() {
//        rotorHolders = new ArrayList<IRotorHolderMachine>();
        rotorHolders.clear();
        for (IMultiPart part : getParts()) {
            if (part instanceof IRotorHolderMachine rotorHolder) {
                rotorHolders.add(rotorHolder);
            }
        }
//        rotorHolders = list;
    }

    private int getEUBoost(){
        return rotorHolders.stream().mapToInt(IRotorHolderMachine::getTotalPower).sum();
    }

    @Override
    public long getOverclockVoltage() {
//        var rotorHolders = getRotorHolders();
        double total;
        if (rotorHolders.isEmpty()) return 0;
        total =  getEUBoost() / (100.0 );//* getRotorCount());
        return (long) (total * BASE_EU_OUTPUT);
    }

    // Shutup.
    private double productionRotorBoost(IRotorHolderMachine rotorHolder) {
        int maxSpeed = rotorHolder.getMaxRotorHolderSpeed();
        int currentSpeed = rotorHolder.getRotorSpeed();
        if (currentSpeed >= maxSpeed) return 1;
        return Math.pow(1.0 * currentSpeed / maxSpeed, 2);
    }

    protected double productionBoost() {
//        var rotorHolders = getRotorHolders();
        if (rotorHolders.isEmpty()) return 0;
        return rotorHolders.stream()
                .filter(IRotorHolderMachine::hasRotor)
                .mapToDouble(this::productionRotorBoost)
                .reduce(1.0, (a, b) -> a * b);
        // ^^ This could result in practically 0 for speeds >0, also I hate computers.
    }

    public int getRotorCount() {
//        var rotorHolders = getRotorHolders();
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
//        var rotorHolders = getRotorHolders();
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

    /**
     * Due to limitation/design choices in {@link IRotorHolderMachine}
     * @return Percentage duration boost of recipe
     */
    @Override
    public int getTotalEfficiency() {
        int eff = 0;
        for (IRotorHolderMachine rholder : rotorHolders) {
            int reff = rholder.getTotalEfficiency();
            if (reff == -1 ) return -1;
            eff += reff;
        }
        return (int) (eff * EFFICIENCY_BOOST);
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
        double holderEfficiency = turbineMachine.getTotalEfficiency() / (100.0 * Math.max(turbineMachine.getRotorCount(),1));
//        double holderEfficiency = 1.5 * rotorHolders.stream()
//                .map(h -> (double) h.getTotalEfficiency())
//                .reduce(0.0, Double::sum) / (100 * Math.max(turbineMachine.getRotorCount(), 1));
//        VoyagerCore.LOGGER.info("Duration modifier: {}", holderEfficiency);

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
//            var rotorHolders = this.getRotorHolders();
            if (rotorHolders == null || rotorHolders.isEmpty()) return;
            List<IRotorHolderMachine> filtered = rotorHolders.stream()
                    .filter(r -> r.getTotalEfficiency() > 0)
                    .toList();

            if (filtered.isEmpty()) return;

            var efficiency = this.getTotalEfficiency() / Math.max(this.getRotorCount(), 1);


            if (efficiency <= 0) {
                textList.add( Component.literal("Missing Rotor!").withStyle(ChatFormatting.RED));
                return;
            }

//            VoyagerCore.LOGGER.info("{}",GTValues.CLIENT_TIME);
//            TextColor rainbow = TooltipHelper.rainbowColor(2.5f);
//            VoyagerCore.LOGGER.info("{}",rainbow.getValue());
            textList.add(Component.translatable("gtceu.multiblock.turbine.efficiency", efficiency));
            textList.add(Component.literal("EU/t Boost: %s%%".formatted(getEUBoost()/ Math.max(this.getRotorCount(), 1))));//.withStyle(style -> {

//                return style.withColor(rainbow.getValue() - 0xFF000000);
//            }));

//            UnaryOperator<Style> modifunc = style -> {
//                int color = 161616;
//                if (getLevel() != null) color = getLevel().random.nextInt();
//                style.withColor(TextColor.parseColor());
//            }

            for (IRotorHolderMachine rotorHolder : filtered) {
//                textList.add(Component.translatable("gtceu.multiblock.turbine.rotor_speed",
//                        FormattingUtil.formatNumbers(rotorHolder.getRotorSpeed()),
//                        FormattingUtil.formatNumbers(rotorHolder.getMaxRotorHolderSpeed())));
//                textList.add(Component.translatable("gtceu.multiblock.turbine.efficiency",
//                        rotorHolder.getTotalEfficiency()));

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
