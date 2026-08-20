package com.jzells.voyagercore.tools.data;

import com.jzells.voyagercore.common.data.VoyagerCoreRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.function.Consumer;

import static com.jzells.voyagercore.tools.VCTConModifiers.*;

public class VoyagerCoreModifierRecipeProvider extends VoyagerCoreRecipeProvider {

    public VoyagerCoreModifierRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }


    @Override
    public String getName() {
        return "Voyager Core Tinkers Recipe Provider";
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        addModifierRecipes(consumer);
    }

    public void addModifierRecipes(Consumer<FinishedRecipe> consumer){

        // Ripped Straight from tcon's ModifierRecipeProvider
        // modifiers
        String upgradeFolder = "tools/modifiers/upgrade/";
        String abilityFolder = "tools/modifiers/ability/";
        String slotlessFolder = "tools/modifiers/slotless/";
        String defenseFolder = "tools/modifiers/defense/";
        String compatFolder = "tools/modifiers/compat/";
        String worktableFolder = "tools/modifiers/worktable/";
        // salvage
        String salvageFolder = "tools/modifiers/salvage/";
        String upgradeSalvage = salvageFolder + "upgrade/";
        String abilitySalvage = salvageFolder + "ability/";
        String defenseSalvage = salvageFolder + "defense/";
        String compatSalvage = salvageFolder + "compat/";

        ModifierRecipeBuilder.modifier(dragon_looting)
                .addInput(Tags.Items.INGOTS_NETHERITE)
                .setSlots(SlotType.ABILITY,1)
                .allowCrystal()
                .setTools(TinkerTags.Items.HARVEST)
                .save(consumer, prefix(dragon_looting, abilityFolder));
    }

    public ResourceLocation prefix(LazyModifier modifier, String prefix) {
        return prefix(modifier.getId(), prefix);
    }
}
