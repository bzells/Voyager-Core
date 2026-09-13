package com.jzells.voyagercore.common.data.recipe.helper;

import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.Nullable;

public class LootTableRecipeLogic implements GTRecipeType.ICustomRecipeLogic {

    public final String LOOT_TABLE_ID;

    public LootTableRecipeLogic(String lootID) {
        this.LOOT_TABLE_ID = lootID;
    }

    @Override
    public @Nullable GTRecipe createCustomRecipe(IRecipeCapabilityHolder holder) {
        if (!(holder instanceof MetaMachine machine)) {
            return null;
        }

        Level level = machine.getLevel();

        if (!(level instanceof ServerLevel serverLevel)) {
            return null;
        }

        MinecraftServer server = level.getServer();

        ResourceLocation lootTableId = ResourceLocation.fromNamespaceAndPath("minecraft", "gameplay/fishing/fish");

        LootTable lootTable = server.getLootData().getLootTable(lootTableId);

        BlockPos pos = machine.getPos();

        LootParams lootParams = new LootParams.Builder(serverLevel)
                .withParameter(
                        LootContextParams.ORIGIN,
                        Vec3.atCenterOf(pos))
                .withParameter(
                        LootContextParams.TOOL,
                        Items.FISHING_ROD.getDefaultInstance())
                .create(LootContextParamSets.FISHING);

        if (machine instanceof WorkableElectricMultiblockMachine multiblockMachine) {
            GTRecipe recipe = multiblockMachine.getRecipeLogic().getLastRecipe();
            int pars;
            if (recipe == null) {
                pars = 1;
            } else {
                pars = recipe.parallels;
            }

            ObjectArrayList<ItemStack> outputItems = new ObjectArrayList<>();
            System.out.println(pars);
            for (int i = 0; i < pars; i++) {
                ObjectArrayList<ItemStack> outputLoot = lootTable.getRandomItems(lootParams);
                for (ItemStack item : outputLoot) {
                    outputItems.add(item);
                }
            }

            System.out.println(outputItems);

            return VoyagerRecipeTypes.HELPER_ASSEMBLY
                    .recipeBuilder("loot_recipe_" + this.LOOT_TABLE_ID)
                    .EUt(100)
                    .duration(20)
                    .outputItems(outputItems.toArray(new ItemStack[0]))
                    .buildRawRecipe();
        }

        return null;
    }
}
