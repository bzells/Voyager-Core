package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.jzells.voyagercore.common.data.VoyagerBlocks;
import com.jzells.voyagercore.common.data.VoyagerItems;
import com.jzells.voyagercore.common.data.recipe.helper.LootTableRecipeLogic;
import com.jzells.voyagercore.common.machine.multiblock.part.VoyagerPartAbilities;
import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;
import com.lowdragmc.lowdraglib.misc.ItemHandlerHelper;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class FishingPortMachine extends WorkableElectricMultiblockMachine {
    public FishingPortMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    private int casingTier = 0;

    public static ModifierFunction recipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof FishingPortMachine fishingPortMachine)) {
            return RecipeModifier.nullWrongType(FishingPortMachine.class, machine);
        } else {
            float speed = switch (fishingPortMachine.casingTier) {
                case 0 -> 4f;
                case 1 -> 1f;
                case 2 -> 1f;
                case 3 -> 0.5f;
                case 4 -> 0.25f;
                case 5 -> 0.125f;
                default -> 1.0f;
            };

            if(fishingPortMachine.getFishNetTier(fishingPortMachine) > fishingPortMachine.casingTier)
            {
                return ModifierFunction.cancel(Component.literal("Pipe Casing Tier Too Low for Net"));
            }

            return ModifierFunction.builder()
                    .modifyAllContents(ContentModifier.multiplier(fishingPortMachine.getParsFromNet(fishingPortMachine)))
                    .parallels(fishingPortMachine.getParsFromNet(fishingPortMachine))
                    .durationMultiplier(speed)
                    .build();
        }
    }


    @Override
    public void afterWorking() {
        if(this.getFishRecipe() != null)
        {
            RecipeHelper.handleRecipeIO(this, this.getFishRecipe(), IO.OUT, Collections.emptyMap());
        }
        super.afterWorking();
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();

        Block pipe_casing = getMultiblockState().getMatchContext().get("pipe_casing");
        this.casingTier = VoyagerVoltageTierUtils.getPipeCasingTier(pipe_casing);

    }

    private int getParsFromNet(FishingPortMachine fishingPortMachine)
    {
        if(RecipeHelper.matchRecipe(fishingPortMachine, fishingPortMachine.getFishNetRecipe()).isSuccess()) return 1;
        if(RecipeHelper.matchRecipe(fishingPortMachine, fishingPortMachine.getFiberFishNetRecipe()).isSuccess()) return 8;
        if(RecipeHelper.matchRecipe(fishingPortMachine, fishingPortMachine.getPIBFishNetRecipe()).isSuccess()) return 64;
        return 0;
    }
    private int getFishNetTier(FishingPortMachine fishingPortMachine)
    {
        if(RecipeHelper.matchRecipe(fishingPortMachine, fishingPortMachine.getFishNetRecipe()).isSuccess()) return 1;
        if(RecipeHelper.matchRecipe(fishingPortMachine, fishingPortMachine.getFiberFishNetRecipe()).isSuccess()) return 3;
        if(RecipeHelper.matchRecipe(fishingPortMachine, fishingPortMachine.getPIBFishNetRecipe()).isSuccess()) return 5;
        return 0;
    }

    protected GTRecipe getFishNetRecipe() {
        return GTRecipeBuilder.ofRaw().notConsumable(VoyagerItems.FISH_NET_STRING).buildRawRecipe();
    }
    protected GTRecipe getFiberFishNetRecipe() {
        return GTRecipeBuilder.ofRaw().notConsumable(VoyagerItems.FISH_NET_FIBER).buildRawRecipe();
    }
    protected GTRecipe getPIBFishNetRecipe() {
        return GTRecipeBuilder.ofRaw().notConsumable(VoyagerItems.FISH_NET_PBI).buildRawRecipe();
    }

    @Nullable
    protected GTRecipe getFishRecipe()
    {
        Level level = this.getLevel();

        if (!(level instanceof ServerLevel serverLevel)) {
            return null;
        }

        MinecraftServer server = level.getServer();

        Tag rData = this.recipeLogic.getLastRecipe().data.get("fish_table");

        if(rData == null)
        {
            return null;
        }

        String table = rData.getAsString();

        ResourceLocation lootTableId = ResourceLocation.fromNamespaceAndPath("minecraft", table);

        LootTable lootTable = server.getLootData().getLootTable(lootTableId);

        BlockPos pos = this.getPos();

        LootParams lootParams = new LootParams.Builder(serverLevel)
                .withParameter(
                        LootContextParams.ORIGIN,
                        Vec3.atCenterOf(pos)
                )
                .withParameter(
                        LootContextParams.TOOL,
                        Items.FISHING_ROD.getDefaultInstance()
                )
                .create(LootContextParamSets.FISHING);

        GTRecipe recipe = this.getRecipeLogic().getLastRecipe();
        int pars;
        if (recipe == null) {
            pars = 1;
        }
        else
        {
            pars = recipe.parallels;
        }


        ObjectArrayList<ItemStack> outputItems = new ObjectArrayList<>();
        for(int i = 0; i < pars; i++)
        {
            ObjectArrayList<ItemStack> outputLoot = lootTable.getRandomItems(lootParams);
            for(ItemStack item : outputLoot)
            {
                outputItems.add(item);
            }
        }

        return GTRecipeBuilder.ofRaw().outputItems(outputItems.toArray(new ItemStack[0])).buildRawRecipe();
    }


}
