package com.jzells.voyagercore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.jzells.voyagercore.common.data.machines.IModuleProvider;
import com.jzells.voyagercore.common.data.machines.IModuleReceiver;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModularMachine extends WorkableElectricMultiblockMachine implements IModuleReceiver {

    private Collection<IModuleProvider> moduleProviders;

    public ModularMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();

        if (moduleProviders != null) {
            this.moduleProviders.forEach(module -> module.setModuleMachine(null));
            this.moduleProviders = null;
        }
        Set<IModuleProvider> modules = getMultiblockState().getMatchContext().getOrCreate("moduleMachines",
                Sets::newHashSet);
        this.moduleProviders = ImmutableSet.copyOf(modules);
        this.moduleProviders.forEach(module -> module.setModuleMachine(this));
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        if (moduleProviders != null) {
            this.moduleProviders.forEach(module -> module.setModuleMachine(null));
            this.moduleProviders = null;
        }
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (moduleProviders == null) return;
        if (moduleProviders.isEmpty()) return;
        for (IModuleProvider module : this.moduleProviders) {
            if (module instanceof WorkableElectricMultiblockMachine wm) {
                String name = wm.getDefinition().getId().toLanguageKey("block");
                Component isf = wm.isFormed() ? Component.literal("IS FORMED").withStyle(ChatFormatting.GREEN) :
                        Component.literal("IS NOT FORMED").withStyle(ChatFormatting.RED);
                textList.add(Component.translatable(name)
                        .append(Component.literal(" is bound to this multiblock and "))
                        .append(isf));
            }
        }
    }
}
