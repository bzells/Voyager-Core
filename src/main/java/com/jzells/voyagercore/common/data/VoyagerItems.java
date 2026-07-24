package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.item.CoverPlaceBehavior;

import com.jzells.voyagercore.common.item.component.HelperComponentItem;
import com.jzells.voyagercore.common.item.component.HelperItemComponent;
import com.jzells.voyagercore.common.item.component.HelperModuleItemComponent;
import com.jzells.voyagercore.common.machine.cover.HeatRedstoneCoverDefinition;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;

public class VoyagerItems {

    public static final Map<Integer, ItemEntry<HelperComponentItem>> HELPERS = new HashMap<>();
    public static final Map<Integer, ItemEntry<HelperComponentItem>> HELPER_HULLS = new HashMap<>();
    public static final Map<ItemEntry<HelperComponentItem>, ItemEntry<HelperComponentItem>> HULL_TO_HELPER = new HashMap<>();

    public static ItemEntry<ComponentItem> COVER_HEAT_REDSTONE = VOYAGERCORE_REGISTRATE
            .item("heat_redstone_cover", ComponentItem::create)
            .lang("Heat Detector")
            .onRegister(item -> item.attachComponents(
                    new CoverPlaceBehavior(HeatRedstoneCoverDefinition.HEAT_REDSTONE_COVER)))
            .register();


    private static final int[] SPECIALIZED_TIERS = {

            GTValues.EV,

    };

    static {
        for (int tier : SPECIALIZED_TIERS) {
            String tierName = GTValues.VN[tier].toLowerCase(Locale.ROOT);

            ItemEntry<HelperComponentItem> helper = VOYAGERCORE_REGISTRATE
                    .item(tierName + "_specialized_helper", HelperComponentItem::new).properties(
                            properties ->
                                    properties.stacksTo(1)
                    )
                    .lang(GTValues.VN[tier] + " Specialized Helper")
                    .onRegister(i -> i.attachComponents(
                            new HelperItemComponent(
                                    GTRecipeTypes.CHEMICAL_RECIPES,
                                    "",
                                    ((int)(tier / 2)) + 1,
                                    tier,
                                    false)))
                    .register();

            ItemEntry<HelperComponentItem> hull = VOYAGERCORE_REGISTRATE
                    .item(tierName + "_specialized_helper_hull", HelperComponentItem::new).properties(
                            properties ->
                                    properties.stacksTo(1)
                    )
                    .lang(GTValues.VN[tier] + " Specialized Helper Hull")
                    .onRegister(i -> i.attachComponents(
                            new HelperItemComponent(
                                    GTRecipeTypes.CHEMICAL_RECIPES,
                                    "specialized",
                                    3,
                                    tier,
                                    true)))
                    .register();

            HELPERS.put(tier, helper);
            HELPER_HULLS.put(tier, hull);

            HULL_TO_HELPER.put(hull, helper);
        }
    }

    public static ItemEntry<ComponentItem> EV_BASIC_HELPER_MODULE = VOYAGERCORE_REGISTRATE
            .item("ev_basic_helper_module", ComponentItem::create)
            .lang("EV Basic Helper Module")
            .onRegister(item -> item.attachComponents(
                    new HelperModuleItemComponent(GTValues.EV, "basic")))
            .register();

    public static ItemEntry<ComponentItem> IV_BASIC_HELPER_MODULE = VOYAGERCORE_REGISTRATE
            .item("iv_basic_helper_module", ComponentItem::create)
            .lang("IV Basic Helper Module")
            .onRegister(item -> item.attachComponents(
                    new HelperModuleItemComponent(GTValues.IV, "basic")))
            .register();

    public static void init() {}
}
