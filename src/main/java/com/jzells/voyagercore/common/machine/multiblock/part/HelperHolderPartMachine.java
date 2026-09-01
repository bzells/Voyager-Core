package com.jzells.voyagercore.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.SlotWidget;
import com.gregtechceu.gtceu.api.item.IComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IWorkableMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifierList;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.utils.GTUtil;

import com.jzells.voyagercore.common.data.VoyagerCoreRecipeModifiers;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.lowdragmc.lowdraglib.utils.Position;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import com.jzells.voyagercore.common.item.component.EnergyModParamountHelperItemComponent;
import com.jzells.voyagercore.common.item.component.HelperComponentItem;
import com.jzells.voyagercore.common.item.component.HelperItemComponent;
import com.jzells.voyagercore.common.item.component.ParamountHelperItemComponent;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.CheckForNull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class HelperHolderPartMachine extends MultiblockPartMachine implements IMachineLife {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            HelperHolderPartMachine.class,
            MultiblockPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    private final HelperHandler helperHandler;

    private int runTime = 0;

    ///First is check, second is needed
    private Boolean[] helperNeeded = {false, false};

    public HelperHolderPartMachine(IMachineBlockEntity holder) {
        super(holder);
        helperHandler = new HelperHandler(this);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public Widget createUIWidget() {
        return new WidgetGroup(new Position(0, 0))
                .addWidget(new SlotWidget(helperHandler, 0, 4, 4)
                        .setBackground(GuiTextures.SLOT));
    }

    /**
     * Method to get the ItemStack inside the HelperHolder, can be used to remove helper, if wanted.
     * 
     * @param remove True removes the item in the Holder
     * @return ItemStack of helper
     */
    public ItemStack getHeldItem(boolean remove) {
        return getHeldItem(0, remove);
    }

    public void setHeldItem(ItemStack heldItem) {
        helperHandler.setStackInSlot(0, heldItem);
    }

    private ItemStack getHeldItem(int slot, boolean remove) {
        ItemStack stackInSlot = helperHandler.getStackInSlot(slot);
        if (remove && stackInSlot != ItemStack.EMPTY) {
            helperHandler.setStackInSlot(slot, ItemStack.EMPTY);
        }
        return stackInSlot;
    }

    public HelperItemComponent getHelperData() {
        var itemstack = getHeldItem(false);
        if (itemstack.getItem() instanceof IComponentItem metaItem) {
            // Should Work, since there's only one component attached
            return (HelperItemComponent) metaItem.getComponents().get(0);
        } else return HelperItemComponent.NULL_HELPER;
    }

    public int getHelperParallels() {
        ItemStack helper = this.getHeldItem(false);
        int pars = 1;
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            if (helper.getOrCreateTagElement("modifiers").contains("parallels"))
                pars = helper.getOrCreateTagElement("modifiers").getInt("parallels");
            return pars;
        }
        return 1;
    }

    public float getHelperEUt() {
        ItemStack helper = this.getHeldItem(false);
        float eut = 1;
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            if (helper.getOrCreateTagElement("modifiers").contains("eut"))
                eut = helper.getOrCreateTagElement("modifiers").getFloat("eut");
            return eut;
        }
        return 1;
    }

    public float getHelperSpeed() {
        ItemStack helper = this.getHeldItem(false);
        float speed = 1;
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            if (helper.getOrCreateTagElement("modifiers").contains("speed"))
                speed = helper.getOrCreateTagElement("modifiers").getFloat("speed");
            return speed;
        }
        return 1;
    }

    public float getOutputModifier() {
        ItemStack helper = this.getHeldItem(false);
        float output = 1;
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            if (helper.getOrCreateTagElement("modifiers").contains("output"))
                output = helper.getOrCreateTagElement("modifiers").getFloat("output");
            return output;
        }
        return 1;
    }

    @CheckForNull
    public ArrayList<String> getRecipes() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            return new ArrayList<>(helper.getOrCreateTagElement("recipes").getAllKeys());
        }
        return null;
    }

    public int getHelperTier() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            for (IItemComponent comp : helperComponentItem.getComponents()) {
                if (comp instanceof HelperItemComponent helperItemComponent) {
                    return helperItemComponent.getTier();
                }
            }
        }
        return 0;
    }

    public boolean getHelperIsSpecialized() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            for (IItemComponent comp : helperComponentItem.getComponents()) {
                if (comp instanceof HelperItemComponent helperItemComponent) {
                    return helperItemComponent.isSpecialized();
                }
            }
        }
        return false;
    }

    public String getHelperSpecialization() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            if (!getHelperIsSpecialized()) return "none";
            for (String key : helper.getTagElement("recipes").getAllKeys()) {
                System.out.println("Keys: " + key);
                return key;
            }
        }
        return "none";
    }

    public boolean getHelperIsParamount() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            for (IItemComponent comp : helperComponentItem.getComponents()) {
                if (comp instanceof ParamountHelperItemComponent) {
                    return true;
                }
            }
        }
        return false;
    }

    @CheckForNull
    public ParamountHelperItemComponent getParamountHelperComponent() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            for (IItemComponent comp : helperComponentItem.getComponents()) {
                if (comp instanceof ParamountHelperItemComponent p) {
                    return p;
                }
            }
        }
        return null;
    }

    public boolean getHelperIsHull() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            for (IItemComponent comp : helperComponentItem.getComponents()) {
                if (comp instanceof HelperItemComponent h) {
                    return h.isHull();
                }
            }
        }
        return false;
    }

    public String getParamountHelperData() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            for (IItemComponent comp : helperComponentItem.getComponents()) {
                if (comp instanceof ParamountHelperItemComponent p) {
                    p.setOwner(helper);
                    return p.getPARAMOUNT_DATA();
                }
            }
        }
        return "not_paramount";
    }

    public int getParamountHelperLevel() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            for (IItemComponent comp : helperComponentItem.getComponents()) {
                if (comp instanceof ParamountHelperItemComponent p) {
                    p.setOwner(helper);
                    return p.getLevel();
                }
            }
        }
        return 0;
    }

    public float getEnergyParamountHelperEUtMod() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            for (IItemComponent comp : helperComponentItem.getComponents()) {
                if (comp instanceof ParamountHelperItemComponent p) {
                    p.setOwner(helper);
                    if (p instanceof EnergyModParamountHelperItemComponent e) return e.getEUtGenMod();
                }
            }
        }
        return 1;
    }

    public float getEnergyParamountHelperEatTimeMod() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            for (IItemComponent comp : helperComponentItem.getComponents()) {
                if (comp instanceof ParamountHelperItemComponent p) {
                    p.setOwner(helper);
                    if (p instanceof EnergyModParamountHelperItemComponent e) return e.getEatTimeMod();
                }
            }
        }
        return 1;
    }

    public float getEnergyParamountHelperOutputMod() {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            for (IItemComponent comp : helperComponentItem.getComponents()) {
                if (comp instanceof ParamountHelperItemComponent p) {
                    p.setOwner(helper);
                    if (p instanceof EnergyModParamountHelperItemComponent e) return e.getOutput();
                }
            }
        }
        return 1;
    }

    private boolean helperNeededCheck(RecipeLogic logic) {
        CompoundTag data = logic.getLastRecipe().data;
        return (data.contains("specialized") || data.contains("paramount"));
    }

    @Override
    public boolean onWorking(IWorkableMultiController controller) {

        ItemStack helper = this.getHeldItem(false);
        RecipeLogic logic = controller.getRecipeLogic();

        if (helperNeeded[0] == false) {
            helperNeeded[0] = true;
            helperNeeded[1] = helperNeededCheck(logic);
        }

        if (helper.isEmpty() && !helperNeeded[1]) {
            logic.setWaiting(Component.literal("Missing Helper! If found, please return to controller."));
        }

        return super.onWorking(controller);
    }


    @Override
    public boolean afterWorking(IWorkableMultiController controller) {
        ItemStack helper = this.getHeldItem(false);
        if (helper.getItem() instanceof HelperComponentItem helperComponentItem) {
            for (IItemComponent comp : helperComponentItem.getComponents()) {
                if (comp instanceof ParamountHelperItemComponent p) {
                    GTRecipe lastRecipe = controller.getRecipeLogic().getLastRecipe();
                    if (lastRecipe != null) {
                        p.setOwner(helper);
                        int tier = controller.getRecipeLogic().getLastRecipe().getOutputEUt().voltage() > 0 ?
                                GTUtil.getTierByVoltage(lastRecipe.getOutputEUt().voltage()) :
                                GTUtil.getTierByVoltage(lastRecipe.getInputEUt().voltage());
                        p.levelHelper(lastRecipe.duration, tier, lastRecipe.parallels);
                    }

                }
            }
        }
        this.helperNeeded[0] = false;
        return super.afterWorking(controller);
    }

    @Override
    public void onMachineRemoved() {
        clearInventory(this.helperHandler.storage);
    }

    public NotifiableItemStackHandler getAsHandler() {
        return helperHandler;
    }

    private class HelperHandler extends NotifiableItemStackHandler {

        public HelperHandler(MetaMachine machine) {
            super(machine, 1, IO.IN, IO.BOTH, size -> new CustomItemStackHandler(size) {

                // Limits number of Items in slot, used in case someone forgets to set stack size of helper.
                @Override
                public int getSlotLimit(int slot) {
                    return 1;
                }
            });
        }
        // Definitely a Better way to do this, but I don't care.
        // Restricts Allowed Items to only those with HelperItemComponent attached

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (stack.isEmpty()) return true;
            boolean isHelperItem = false;
            if (stack.getItem() instanceof IComponentItem metaItem) {
                for (IItemComponent behavior : metaItem.getComponents()) {
                    if (behavior instanceof HelperItemComponent) {
                        isHelperItem = true;
                        break;
                    }
                }
            }
            return isHelperItem;
        }

        // TDL
        // public float getRecipeList(ItemStack helper)
        // {
        // float output = 1;
        // if(helper.getItem() instanceof HelperComponentItem helperComponentItem)
        // {
        // if(helper.getOrCreateTagElement("modifiers").contains("output"))
        // output = helper.getOrCreateTagElement("modifiers").getFloat("output");
        // return output;
        // }
        // return 0;
        // }
    }
}
