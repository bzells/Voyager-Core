package com.jzells.voyagercore.tools.data;

import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.MeleeAttributeModule;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class VoyagerCoreModifierProvider extends AbstractModifierProvider implements IConditionBuilder {
    public VoyagerCoreModifierProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void addModifiers() {
        buildModifier(VCTconModifierIds.dragon_strength)
                .addModule(StatBoostModule.multiplyAll(ToolStats.ATTACK_DAMAGE).amount(0,1))
                .build();
    }

    @Override
    public String getName() {
        return "VoyagerCore TCon Materials";
    }
}
