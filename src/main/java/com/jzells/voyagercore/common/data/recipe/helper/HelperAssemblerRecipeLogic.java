package com.jzells.voyagercore.common.data.recipe.helper;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.jzells.voyagercore.common.data.VoyagerRecipeTypes;
import com.jzells.voyagercore.common.item.component.HelperComponentItem;
import com.jzells.voyagercore.common.item.component.HelperItemComponent;
import com.jzells.voyagercore.common.item.component.HelperModuleItemComponent;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.jzells.voyagercore.common.data.VoyagerItems.HELPERS;
import static com.jzells.voyagercore.common.data.VoyagerItems.HULL_TO_HELPER;

public class HelperAssemblerRecipeLogic implements GTRecipeType.ICustomRecipeLogic {


    @Override
    public @Nullable GTRecipe createCustomRecipe(IRecipeCapabilityHolder holder) {

        List<IRecipeHandler<?>> handlers =
                holder.getCapabilitiesFlat(IO.IN, ItemRecipeCapability.CAP);

        if(handlers.isEmpty()) return null;

        IRecipeHandler<?> handler = handlers.get(0);

        List<Object> contents = handler.getContents();

        ItemStack helperItem = ItemStack.EMPTY;
        ItemStack moduleItem = ItemStack.EMPTY;

        String data = null;

        HelperItemComponent helperItemComponent = null;
        HelperModuleItemComponent helperModuleItemComponent = null;

        for (Object object : contents) {
            if (!(object instanceof ItemStack stack) || stack.isEmpty())
                continue;
            if (!(stack.getItem() instanceof ComponentItem componentItem)) {
                continue;
            }

            HelperItemComponent helper = getComponent(stack, HelperItemComponent.class);
            if (helper != null) {
                helperItem = stack;
                helperItemComponent = helper;
                continue;
            }

            HelperModuleItemComponent module = getComponent(stack, HelperModuleItemComponent.class);
            if (module != null) {
                moduleItem = stack;
                helperModuleItemComponent = module;
            }

        }

        if(helperItemComponent != null && helperModuleItemComponent != null && helperModuleItemComponent.canApply(helperItem, helperItemComponent))
        {
            ItemStack outputHelper;


            if(helperItemComponent.isHull())
            {
                ItemEntry<HelperComponentItem> helper =
                        HELPERS.get(helperItemComponent.getTier());

                outputHelper = new ItemStack(helper.get());
            } else {
                outputHelper = helperItem.copy();
            }

            if (helperItem.hasTag()) {
                assert helperItem.getTag() != null;
                outputHelper.setTag(helperItem.getTag().copy());
            }

            helperModuleItemComponent.apply(outputHelper);

            helperItem.setCount(1);
            moduleItem.setCount(1);





            return VoyagerRecipeTypes.HELPER_ASSEMBLY
                    .recipeBuilder("helper_module_apply")
                    .inputItems(helperItem, moduleItem)
                    .EUt(GTValues.VA[helperModuleItemComponent.getGT_TIER()])
                    .duration(20 * 5)
                    .outputItems(outputHelper)
                    .buildRawRecipe();
        }

        return null;
    }

    public static <T extends IItemComponent> T getComponent(ItemStack stack, Class<T> c) {
        if (!(stack.getItem() instanceof ComponentItem item))
            return null;

        for (IItemComponent component : item.getComponents()) {
            if (c.isInstance(component)) {
                return c.cast(component);
            }
        }

        return null;
    }
}
