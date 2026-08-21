package com.jzells.voyagercore.tools.modifiers;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.Tags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.ListIterator;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DragonLootingModifier extends Modifier implements ProcessLootModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROCESS_LOOT);
    }



    @Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {

        if (generatedLoot.isEmpty()) return;

        TagKey<Block> oreTag = Tags.Blocks.ORES;
        BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);

        if ( blockState != null && blockState.is(oreTag)) {

            int modifierLvl = modifier.intEffectiveLevel();
            ListIterator<ItemStack> iter = generatedLoot.listIterator();

            while (iter.hasNext()) {

                ItemStack itemStack = iter.next();
                TagKey<Item> rawTag = Tags.Items.RAW_MATERIALS;
                TagKey<Item> gemTag = Tags.Items.GEMS;

                if (itemStack.is(rawTag) || itemStack.is(gemTag)) {
                    int stackCount = itemStack.getCount();
                    int maxStack = stackCount * modifierLvl;
                    int loot = 0;
                    for (int i = 0; i < maxStack; i++){
                        if (context.getRandom().nextFloat() < 0.4f) {
                            loot++;
                        }
                    }
                    itemStack.setCount(stackCount + loot);
                    iter.set(itemStack);
                }
            }
        }
    }
}
