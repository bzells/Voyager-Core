package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.item.CoverPlaceBehavior;

import net.minecraft.world.item.Item;

import com.jzells.voyagercore.common.item.component.*;
import com.jzells.voyagercore.common.machine.cover.HeatRedstoneCoverDefinition;
import com.jzells.voyagercore.util.VoyagerVoltageTierUtils;
import com.tterrag.registrate.util.entry.ItemEntry;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.gregtechceu.gtceu.api.GTValues.VN;
import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;

public class VoyagerItems {

    public static final Map<Integer, ItemEntry<HelperComponentItem>> HELPERS = new HashMap<>();
    public static final Map<Integer, ItemEntry<HelperComponentItem>> HELPER_HULLS = new HashMap<>();
    public static final Map<ItemEntry<HelperComponentItem>, ItemEntry<HelperComponentItem>> HULL_TO_HELPER = new HashMap<>();

    public static final Map<Integer, ItemEntry<HelperComponentItem>> SPECIALIZED_HELPERS = new HashMap<>();
    public static final Map<Integer, ItemEntry<HelperComponentItem>> SPECIALIZED_HELPER_HULLS = new HashMap<>();
    public static final Map<ItemEntry<HelperComponentItem>, ItemEntry<HelperComponentItem>> SPECIALIZED_HULL_TO_HELPER = new HashMap<>();

    public static final Map<Integer, ItemEntry<HelperComponentItem>> PARAMOUNT_HELPERS = new HashMap<>();
    public static final Map<Integer, ItemEntry<HelperComponentItem>> PARAMOUNT_HELPER_HULLS = new HashMap<>();
    public static final Map<String, ItemEntry<HelperComponentItem>> PARAMOUNT_HULL_TO_HELPER = new HashMap<>();

    public static ItemEntry<ComponentItem> COVER_HEAT_REDSTONE = VOYAGERCORE_REGISTRATE
            .item("heat_redstone_cover", ComponentItem::create)
            .lang("Heat Detector")
            .onRegister(item -> item.attachComponents(
                    new CoverPlaceBehavior(HeatRedstoneCoverDefinition.HEAT_REDSTONE_COVER)))
            .register();

    private static final int[] SPECIALIZED_TIERS = {

            GTValues.HV,
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
            createHelper(tier, "specialized", "Specialized Helper", true);
        }
    }

    static {
        for (int tier : GENERIC_TIERS) {
            createHelper(tier, "generic", "Generic Helper", false);
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
        for (int tier : MODULE_TIERS) {
            createHelperModifierModule("basic_helper_module", "Basic Helper Module", (int) (tier * 1.5), tier / 4,
                    .05f / ((float) tier * .5f), .05f + (((float) tier / 8) * .3f), 0, false, 1, tier);
            createHelperModifierModule("efficiency_helper_module", "Efficiency Helper Module", (int) (tier * 1.5), 0,
                    1.2f / (float) tier, -.4f + (.5f * ((float) tier / 8)), 0, false, 1, tier);
            createHelperModifierModule("speed_helper_module", "Speed Helper Module", (int) (tier * 1.5), 0,
                    -0.25f * ((float) tier) / 4, .2f + (.4f * ((float) tier / 6)), 0, false, 1, tier);
            createHelperModifierModule("output_helper_module", "Output Modifier Helper Module", (int) (tier * 1.5), 0,
                    0.05f * ((float) tier) / 4, -.2f + (.4f * ((float) tier / 8)), .5f, true, 2, tier);
            createHelperModifierModule("parallel_helper_module", "Parallel Modifier Helper Module", (int) (tier * 1.5), tier,
                    0.05f * ((float) tier) / 4, -.8f + (.1f * ((float) tier / 8)), 0f, false, 2, tier);

            ItemEntry<Item> HELPER_MODULE_BASE = VOYAGERCORE_REGISTRATE
                    .item(VN[tier].toLowerCase() + "_helper_module", Item::new).properties(
                            properties -> properties.stacksTo(4))
                    .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[tier]) +
                            " Helper Module Base")
                    .register();
        }
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_EBF = createHelperRecipeModule(
                GTValues.MV, "ebf_helper_recipe_module", "Recipe Helper Module", "gtceu:electric_blast_furnace", false,
                1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_PLAT_LINE = createHelperRecipeModule(
                GTValues.EV, "plat_line_helper_recipe_module", "Recipe Helper Module", "plat_line", true, 1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_DESH_LINE = createHelperRecipeModule(
                GTValues.EV, "desh_line_helper_recipe_module", "Recipe Helper Module", "desh_line", true, 1);

        String grandmaName = "§eGrandma Helper";

        final ItemEntry<HelperComponentItem> HELPER_PARAMOUNT_GRANDMA = createParamountHelper("grandma", "paramount",
                grandmaName, 1, "grandma", 1);
        final ItemEntry<HelperComponentItem> HELPER_PARAMOUNT_EMBASSY = createParamountHelper("embassy", "paramount",
                "§9Embassy Helper", 1, "embassy", 2f);
        final ItemEntry<HelperComponentItem> HELPER_PARAMOUNT_THE_CHEMIST = createParamountHelper("chemist",
                "paramount",
                "§bChemist Helper", 1, "chemist", 4f);

        String heartName = "§cHeart Module";

        for (int i = 1; i < 6; i++) {
            createHelperParamountModule("heart_grandma_helper_module", heartName, i * 2, i, .025f * ((float) (i + 1) / 4),
                    .05f, .05f + ((float) (i - 1) / 20), 1, "grandma", i);
            createHelperParamountModule("xp_grandma_helper_module", "§aXP Module", i * 2, i, .025f * ((float) (i + 1) / 2),
                    -.50f * ((float) i / 2), .025f + ((float) (i - 1) / 40), 1, "grandma", i);
            createHelperParamountModule("baking_sheet_grandma_helper_module", "§7Baking Sheet Module", i * 2, i,
                    .05f * ((float) (i + 1) / 2), 0, .015f + ((float) (i - 1) / 25), 1, "grandma", i);

            createHelperParamountModule("flask_chemist_helper_module", "§bFlask Module", i * 2, i,
                    0, 0, .25f + ((float) i / 12), 2, "chemist", i);

        }
        for (int i = 4; i < 6; i++) {
            createHelperParamountModule("the_networker_embassy_helper_module", "§9The Networker", i, i / 2, 0,
                    -.5f / ((float) i / 2), 0.05f, 3, "embassy", i - 3);
            createHelperParamountModule("the_bandit_embassy_helper_module", "§4The Bandit", i, 0,
                    -.15f * ((float) i / 2), .75f * i, 0.05f * i, 1, "embassy", i - 3);
        }
        for (int i = 1; i < 3; i++) {
            createHelperParamountModule("the_diplomat_embassy_helper_module", "§3The Diplomat", i * 2, 0,
                    .1f * ((float) i / 2), -.25f + ((float) i / 4), 0.119f * i, 2, "embassy", i - 3);

        }

    }

    private static void createHelperModifierModule(String id, String lang,
                                                   int tier, int pars,
                                                   float eutReduce, float speed,
                                                   float outputMod,
                                                   boolean specialized,
                                                   int moduleSpace, int langTier) {
        String tierName = VN[langTier].toLowerCase(Locale.ROOT);
        VOYAGERCORE_REGISTRATE
                .item(tierName + "_" + id, HelperModuleComponentTooltipItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[langTier]) + " " + lang)
                .onRegister(item -> item.attachComponents(
                        new HelperModuleItemModifierComponent(tier, pars, eutReduce, speed, outputMod, specialized,
                                moduleSpace, null, null)))
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/" + tierName + "_helper_module"))
                        .texture("layer1", prov.modLoc("item/" + id)))
                .register();
    }

    private static void createHelperParamountModule(String id, String lang,
                                                    int lvl, int pars,
                                                    float eutReduce, float speed,
                                                    float outputMod,
                                                    int moduleSpace, String paramountData, int levelName) {
        VOYAGERCORE_REGISTRATE
                .item(id + "_" + levelName, HelperModuleComponentTooltipItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(lang + " " + VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[levelName]).substring(0, 2) +
                        levelName)
                .onRegister(item -> item.attachComponents(
                        new HelperModuleItemModifierComponent(lvl, pars, eutReduce, speed, outputMod, false,
                                moduleSpace, true, paramountData)))
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/paramount_helper_module_" + levelName))
                        .texture("layer1", prov.modLoc("item/" + id)))
                .register();
    }

    private static ItemEntry<HelperModuleComponentTooltipItem> createHelperModifierBeamModule(String id, String lang,
                                                                                              int tier, int pars,
                                                                                              float eutReduce,
                                                                                              float speed,
                                                                                              float outputMod,
                                                                                              boolean specialized,
                                                                                              int moduleSpace,
                                                                                              float beamP) {
        String tierName = VN[tier].toLowerCase(Locale.ROOT);
        return VOYAGERCORE_REGISTRATE
                .item(tierName + "_" + id, HelperModuleComponentTooltipItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[tier]) + " " + lang)
                .onRegister(item -> item.attachComponents(
                        new HelperModuleItemBeamComponent(tier, pars, eutReduce, speed, outputMod, specialized,
                                moduleSpace, beamP)))
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/" + tierName + "_beam_helper_module"))
                        .texture("layer1", prov.modLoc("item/" + id)))
                .register();
    }

    private static ItemEntry<HelperModuleComponentTooltipItem> createHelperRecipeModule(int tier, String id,
                                                                                        String lang, String recipe,
                                                                                        boolean specialized,
                                                                                        int recipeCount) {
        String tierName = VN[tier].toLowerCase(Locale.ROOT);
        return VOYAGERCORE_REGISTRATE
                .item(tierName + "_" + id, HelperModuleComponentTooltipItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[tier]) + " " + lang)
                .onRegister(item -> item.attachComponents(
                        new HelperRecipeModuleItemComponent(tier, recipe, recipeCount, specialized)))
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/" + tierName + "_helper_module"))
                        .texture("layer1", prov.modLoc("item/" + id)))
                .register();
    }

    private static void createHelper(int tier, String type, String lang, boolean specialized) {
        String tierName = VN[tier].toLowerCase(Locale.ROOT);

        if (!specialized) {
            ItemEntry<HelperComponentItem> helper = VOYAGERCORE_REGISTRATE
                    .item(tierName + "_" + type + "_helper", HelperComponentItem::new).properties(
                            properties -> properties.stacksTo(1))
                    .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[tier]) + " " + lang)
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
                    .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[tier]) + " " + lang +
                            " Hull")
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
        } else {
            ItemEntry<HelperComponentItem> helper = VOYAGERCORE_REGISTRATE
                    .item(tierName + "_" + type + "_helper", HelperComponentItem::new).properties(
                            properties -> properties.stacksTo(1))
                    .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[tier]) + " " + lang)
                    .onRegister(i -> i.attachComponents(
                            new HelperItemComponent(
                                    GTRecipeTypes.DUMMY_RECIPES,
                                    type,
                                    ((int) (tier / 2)) + 2,
                                    tier,
                                    false, 1, true)))
                    .register();

            ItemEntry<HelperComponentItem> hull = VOYAGERCORE_REGISTRATE
                    .item(tierName + "_" + type + "_helper_hull", HelperComponentItem::new).properties(
                            properties -> properties.stacksTo(1))
                    .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[tier]) + " " + lang +
                            " Hull")
                    .onRegister(i -> i.attachComponents(
                            new HelperItemComponent(
                                    GTRecipeTypes.DUMMY_RECIPES,
                                    type,
                                    ((int) (tier / 2)) + 2,
                                    tier,
                                    true, 1, true)))
                    .register();
            SPECIALIZED_HELPERS.put(tier, helper);
            SPECIALIZED_HELPER_HULLS.put(tier, hull);

            SPECIALIZED_HULL_TO_HELPER.put(hull, helper);
        }
    }

    private static ItemEntry<HelperComponentItem> createParamountHelper(String id, String type, String lang,
                                                                        int baseLevel, String paramountData,
                                                                        float xpScale) {
        ItemEntry<HelperComponentItem> hull = VOYAGERCORE_REGISTRATE
                .item(id + "_" + type + "_helper_hull", HelperComponentItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(lang + " Hull")
                .onRegister(i -> i.attachComponents(
                        new ParamountHelperItemComponent(
                                GTRecipeTypes.DUMMY_RECIPES,
                                2 + baseLevel,
                                baseLevel,
                                true,
                                paramountData, baseLevel, xpScale)))
                .register();

        ItemEntry<HelperComponentItem> helper = VOYAGERCORE_REGISTRATE
                .item(id + "_" + type + "_helper", HelperComponentItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(lang)
                .onRegister(i -> i.attachComponents(
                        new ParamountHelperItemComponent(
                                GTRecipeTypes.DUMMY_RECIPES,
                                2 + baseLevel,
                                baseLevel,
                                false,
                                paramountData, baseLevel, 1)))
                .register();
        PARAMOUNT_HULL_TO_HELPER.put(paramountData, helper);

        return helper;
    }

    public static void init() {}
}
