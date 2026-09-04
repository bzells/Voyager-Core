package com.jzells.voyagercore.common.machine.multiblock.electric;

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
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ForestRegrowthChamberMachine extends WorkableElectricMultiblockMachine {

    private int casingTier;

    public ForestRegrowthChamberMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();

        Block pipe_casing = getMultiblockState().getMatchContext().get("pipe_casing");
        this.casingTier = VoyagerVoltageTierUtils.getPipeCasingTier(pipe_casing);
    }

    public static ModifierFunction recipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof ForestRegrowthChamberMachine forestRegrowthChamberMachine)) {
            return RecipeModifier.nullWrongType(FishingPortMachine.class, machine);
        } else {

            int basePars = 9;

            int outputMod = (int) Math.pow(2, forestRegrowthChamberMachine.casingTier);

            if (forestRegrowthChamberMachine.hasAxe()) {
                return ModifierFunction.builder()
                        .modifyAllContents(ContentModifier.multiplier(outputMod * basePars))
                        .parallels(basePars)
                        .durationMultiplier(forestRegrowthChamberMachine.axeSpeed())
                        .build();
            }
            return ModifierFunction.cancel(Component.literal("Needs an axe to chop trees!"));

        }
    }

    private float axeSpeed() {
        List<ItemStack> axeItems = RecipeHelper.getInputItems(this.getAxeRecipe());
        for (ItemStack stack : axeItems) {
            if (stack.is(ItemTags.AXES)) {
                return stack.getDestroySpeed(Blocks.OAK_LOG.defaultBlockState()) / 20;
            }
        }
        return 1;
    }

    private GTRecipe getAxeRecipe() {
        return GTRecipeBuilder.ofRaw().notConsumable(Ingredient.of(ItemTags.AXES)).buildRawRecipe();
    }

    private boolean hasAxe() {
        return RecipeHelper.matchRecipe(this, this.getAxeRecipe()).isSuccess();
    }
}
