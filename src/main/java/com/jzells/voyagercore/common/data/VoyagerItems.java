package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.item.CoverPlaceBehavior;

import com.jzells.voyagercore.common.item.component.*;
import com.jzells.voyagercore.common.machine.cover.HeatRedstoneCoverDefinition;
import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;
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
            GTValues.IV,
            GTValues.LuV,
            GTValues.ZPM,
            GTValues.UV

    };

    private static final int[] GENERIC_TIERS = {

            GTValues.MV,
            GTValues.HV,
            GTValues.EV,
            GTValues.IV,
            GTValues.LuV,
            GTValues.ZPM,
            GTValues.UV

    };

    static {
        for (int tier : SPECIALIZED_TIERS) {
            createHelper(tier, "specialized", "Specialized Helper");
        }
    }

    static {
        for (int tier : GENERIC_TIERS) {
            createHelper(tier, "generic", "Generic Helper");
        }
    }


    private static final int[] MODULE_TIERS = {

            GTValues.MV,
            GTValues.HV,
            GTValues.EV,
            GTValues.IV,
            GTValues.LuV,
            GTValues.ZPM,
            GTValues.UV

    };

    static {
        for(int tier : MODULE_TIERS)
        {
            createHelperModifierModule("basic_helper_module", "Basic Helper Module", tier, 0, .15f/((float)tier * .5f), .05f + (((float)tier / 8) * .3f), 0, false, 1);
            createHelperModifierModule("efficiency_helper_module", "Efficiency Helper Module", tier, 0, 1.2f /(float)tier, -.25f + (.5f * ((float)tier / 8)), 0, false, 1);
            createHelperModifierModule("speed_helper_module", "Speed Helper Module", tier, 0, -0.25f * ((float)tier) / 4, .2f + (.4f * ((float)tier / 6)), 0, false, 1);
            createHelperModifierModule("output_helper_module", "Output Modifier Helper Module", tier, 0, 0.05f * ((float)tier) / 4, -.2f + (.4f * ((float)tier / 8)), .5f, true, 2);
            createHelperModifierModule("parallel_helper_module", "Parallel Modifier Helper Module", tier, tier, 0.05f * ((float)tier) / 4, -.5f + (.1f * ((float)tier / 8)), 0f, false, 2);


            ItemEntry<Item> HELPER_MODULE_BASE = VOYAGERCORE_REGISTRATE
                    .item(GTValues.VN[tier].toLowerCase() + "_helper_module", Item::new).properties(
                            properties -> properties.stacksTo(4))
                    .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(GTValues.VN[tier]) + " Helper Module Base")
                    .register();
        }
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_EBF = createHelperRecipeModule(GTValues.MV, "ebf_helper_recipe_module", "Recipe Helper Module", "gtceu:electric_blast_furnace", false, 1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_PLAT_LINE = createHelperRecipeModule(GTValues.EV, "plat_line_helper_recipe_module", "Recipe Helper Module", "plat_line", true, 1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_DESH_LINE = createHelperRecipeModule(GTValues.EV, "desh_line_helper_recipe_module", "Recipe Helper Module", "desh_line", true, 1);

    }


    private static ItemEntry<HelperModuleComponentTooltipItem> createHelperModifierModule(String id,String lang, int tier, int pars, float eutReduce, float speed, float outputMod, boolean specialized, int moduleSpace)
    {
        String tierName = GTValues.VN[tier].toLowerCase(Locale.ROOT);
        return VOYAGERCORE_REGISTRATE
                .item(tierName + "_" + id, HelperModuleComponentTooltipItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(GTValues.VN[tier]) + " " + lang)
                .onRegister(item -> item.attachComponents(
                        new HelperModuleItemModifierComponent(tier, pars, eutReduce, speed, outputMod, specialized, moduleSpace)))
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/" + tierName + "_helper_module"))
                        .texture("layer1", prov.modLoc("item/" + id)))
                .register();
    }

    private static ItemEntry<HelperModuleComponentTooltipItem> createHelperModifierBeamModule(String id,String lang, int tier, int pars, float eutReduce, float speed, float outputMod, boolean specialized, int moduleSpace, float beamP)
    {
        String tierName = GTValues.VN[tier].toLowerCase(Locale.ROOT);
        return VOYAGERCORE_REGISTRATE
                .item(tierName + "_" + id, HelperModuleComponentTooltipItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(GTValues.VN[tier]) + " " + lang)
                .onRegister(item -> item.attachComponents(
                        new HelperModuleItemBeamComponent(tier, pars, eutReduce, speed, outputMod, specialized, moduleSpace, beamP)))
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/" + tierName + "_beam_helper_module"))
                        .texture("layer1", prov.modLoc("item/" + id)))
                .register();
    }

    private static ItemEntry<HelperModuleComponentTooltipItem> createHelperRecipeModule(int tier, String id, String lang, String recipe, boolean specialized, int recipeCount)
    {
        String tierName = GTValues.VN[tier].toLowerCase(Locale.ROOT);
        return VOYAGERCORE_REGISTRATE
                .item(tierName + "_" + id, HelperModuleComponentTooltipItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(GTValues.VN[tier]) + " " + lang)
                .onRegister(item -> item.attachComponents(
                        new HelperRecipeModuleItemComponent(tier, recipe, recipeCount, specialized)))
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/" + tierName + "_helper_module"))
                        .texture("layer1", prov.modLoc("item/" + id)))
                .register();
    }

    private static void createHelper(int tier, String type, String lang)
    {
        String tierName = GTValues.VN[tier].toLowerCase(Locale.ROOT);

        ItemEntry<HelperComponentItem> helper = VOYAGERCORE_REGISTRATE
                .item(tierName + "_" + type + "_helper", HelperComponentItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(GTValues.VN[tier]) + " " + lang)
                .onRegister(i -> i.attachComponents(
                        new HelperItemComponent(
                                GTRecipeTypes.DUMMY_RECIPES,
                                type,
                                ((int) (tier / 2)) + 1,
                                tier,
                                false, ((int) (tier / 4)) + 1, false)))
                .register();

        ItemEntry<HelperComponentItem> hull = VOYAGERCORE_REGISTRATE
                .item(tierName + "_" + type + "_helper_hull", HelperComponentItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(GTValues.VN[tier]) + " " + lang + " Hull")
                .onRegister(i -> i.attachComponents(
                        new HelperItemComponent(
                                GTRecipeTypes.DUMMY_RECIPES,
                                type,
                                ((int) (tier / 2)) + 1,
                                tier,
                                true, ((int) (tier / 4)) + 1, false)))
                .register();

        HELPERS.put(tier, helper);
        HELPER_HULLS.put(tier, hull);

        HULL_TO_HELPER.put(hull, helper);
    }

    public static void init() {}
}
