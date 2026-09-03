package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.item.CoverPlaceBehavior;

import net.minecraft.world.item.Item;

import com.jzells.voyagercore.common.item.component.*;
import com.jzells.voyagercore.common.machine.cover.HeatRedstoneCoverDefinition;
import com.jzells.voyagercore.util.VoyagerTags;
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
            createHelperModifierModule("basic_helper_module", "Basic Helper Module", tier, tier / 4,
                    .05f / ((float) tier * .5f), .05f + (((float) tier / 8) * .3f), 0, false, 1, tier);
            createHelperModifierModule("efficiency_helper_module", "Efficiency Helper Module", tier, 0,
                    1.2f / (float) tier, -.4f + (.5f * ((float) tier / 8)), 0, false, 1, tier);
            createHelperModifierModule("speed_helper_module", "Speed Helper Module", (int) tier, 0,
                    -0.20f * ((float) tier) / 4, .2f + (.2f * ((float) tier / 6)), 0, false, 1, tier);
            createHelperModifierModule("output_helper_module", "Output Modifier Helper Module", tier, 0,
                    0.05f * ((float) tier) / 4, -.8f + (.3f * ((float) tier / 8)), .5f, true, 2, tier);

            ItemEntry<Item> HELPER_MODULE_BASE = VOYAGERCORE_REGISTRATE
                    .item(VN[tier].toLowerCase() + "_helper_module", Item::new).properties(
                            properties -> properties.stacksTo(4))
                    .lang(VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[tier]) +
                            " Helper Module Base")
                    .register();
        }
        for (int tier = GTValues.HV; tier < GTValues.UV; tier++) {
            createHelperModifierModule("parallel_helper_module", "Parallel Modifier Helper Module", tier,
                    tier,
                    0.05f * ((float) tier) / 4, -.8f + (.1f * ((float) tier / 8)), 0f, false, 2, tier);
        }

        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_EBF = createHelperRecipeModule(
                GTValues.MV, "ebf_helper_recipe_module", "Recipe Helper Module", "gtceu:electric_blast_furnace", false,
                1);

        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_HELPER_WHEEL = createHelperRecipeModule(
                GTValues.HV, "helper_wheel_recipe_module", "Recipe Helper Module", "gtceu:large_helper_wheel", false,
                1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_CENTRIFUGE = createHelperRecipeModule(
                GTValues.EV, "centrifuge_helper_recipe_module", "Recipe Helper Module", "gtceu:centrifuge", false,
                1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_THERMAL_CENTRIFUGE = createHelperRecipeModule(
                GTValues.EV, "thermal_centrifuge_helper_recipe_module", "Recipe Helper Module",
                "gtceu:thermal_centrifuge", false,
                1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_MACERATOR = createHelperRecipeModule(
                GTValues.EV, "macerator_helper_recipe_module", "Recipe Helper Module", "gtceu:macerator", false,
                1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_ORE_WASHER = createHelperRecipeModule(
                GTValues.IV, "ore_washer_helper_recipe_module", "Recipe Helper Module", "gtceu:ore_washer", false,
                1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_AUTOCLAVE = createHelperRecipeModule(
                GTValues.IV, "autoclave_helper_recipe_module", "Recipe Helper Module", "gtceu:autoclave", false,
                1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_ELECTROLYZER = createHelperRecipeModule(
                GTValues.EV, "electrolyzer_helper_recipe_module", "Recipe Helper Module", "gtceu:electrolyzer", false,
                1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_ASSEMBLER = createHelperRecipeModule(
                GTValues.LuV, "assembler_helper_recipe_module", "Recipe Helper Module", "gtceu:assembler", false,
                1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_OVEN = createHelperRecipeModule(
                GTValues.EV, "oven_helper_recipe_module", "Recipe Helper Module", "gtceu:oven", false,
                1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_SMD_ASSEMBLER = createHelperRecipeModule(
                GTValues.HV, "smd_assembler_helper_recipe_module", "Recipe Helper Module", "smd_assembly", true,
                1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_PLAT_LINE = createHelperRecipeModule(
                GTValues.EV, "plat_line_helper_recipe_module", "Specialized Helper Module", "plat_line", true, 1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_DESH_LINE = createHelperRecipeModule(
                GTValues.EV, "desh_line_helper_recipe_module", "Specialized Helper Module", "desh_line", true, 1);
        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_PETROCHEM = createHelperRecipeModule(
                GTValues.EV, "petrochem_helper_recipe_module", "Specialized Helper Module", "petrochem", true, 1);

        final ItemEntry<HelperModuleComponentTooltipItem> RECIPE_MODULE_HELPER_CHEMIST = createHelperRecipeModule(
                GTValues.EV, "chemist_helper_recipe_module", "Specialized Helper Module", "chemist", true,
                1);

        String grandmaName = "§eGrandma Helper";

        final ItemEntry<HelperComponentItem> HELPER_PARAMOUNT_GRANDMA = createParamountHelper("grandma", "paramount",
                grandmaName, 1, "grandma", .5f, 1);
        final ItemEntry<HelperComponentItem> HELPER_PARAMOUNT_EMBASSY = createParamountHelper("embassy", "paramount",
                "§9Embassy Helper", 1, "embassy", 2f, GTValues.IV);
        final ItemEntry<HelperComponentItem> HELPER_PARAMOUNT_THE_CHEMIST = createParamountHelper("chemist",
                "paramount",
                "§bChemist Helper", 1, "chemist", 4f, GTValues.HV);
        final ItemEntry<HelperComponentItem> HELPER_PARAMOUNT_COILTRONICS = createParamountHelper("coiltronics",
                "paramount",
                "§eCoiltronics Helper", 1, "coiltronics", .25f, GTValues.LuV);
        final ItemEntry<HelperComponentItem> HELPER_PARAMOUNT_HUNGRY = createEnergyParamountHelper("hungry",
                "paramount",
                "§2Hungry Helper", 1, "hungry", .5f, 1f, 1f);
        final ItemEntry<HelperComponentItem> HELPER_PARAMOUNT_COMPUTER = createParamountHelper("computer",
                "paramount",
                "§2Computer Helper", 1, "computer", 1f, GTValues.LuV);

        String heartName = "§cHeart Module";

        for (int i = 1; i < 6; i++) {
            createHelperParamountModule("heart_grandma_helper_module", heartName, i * 2, 0,
                    .025f * ((float) (i + 1) / 4),
                    .05f, .05f + ((float) (i - 1) / 15), 2, "grandma", i, i + 3);
            createHelperParamountModule("xp_grandma_helper_module", "§aXP Module", i * 2, i,
                    .025f * ((float) (i + 1) / 2),
                    -.15f - (.1f * ((float) i / 2)), .025f + ((float) (i - 1) / 40), 2, "grandma", i, i + 2);
            createHelperParamountModule("baking_sheet_grandma_helper_module", "§7Baking Sheet Module", i, i,
                    .05f * ((float) (i + 1) / 2), 0, .015f + ((float) (i - 1) / 30), 3, "grandma", i, i + 2);

            createHelperParamountModule("flask_chemist_helper_module", "§bFlask Module", i * 2, i,
                    0, 0, .25f + ((float) i / 12), 2, "chemist", i, i + 4);

        }
        for (int i = 4; i < 6; i++) {
            createHelperParamountModule("the_networker_embassy_helper_module", "§9The Networker", i, i / 2, 0,
                    -.5f / ((float) i / 2), 0.05f, 3, "embassy", i - 3, i + 1);
            createHelperParamountModule("the_bandit_embassy_helper_module", "§4The Bandit", i, 0,
                    -.25f * ((float) i / 2), .35f * ((float) i / 2), 0.05f * i, 3, "embassy", i - 3, i + 2);
        }
        for (int i = 1; i < 3; i++) {
            createHelperParamountModule("the_diplomat_embassy_helper_module", "§3The Diplomat", i * 2, 0,
                    .1f * ((float) i / 2), -.25f + ((float) i / 4), 0.119f * i, 2, "embassy", i, i + 4);
            createHelperParamountModule("coil_coiltronics_helper_module", "§6Coil Upgrade Module", i * 2, i,
                    .1f * ((float) (i + 1) / 2), .05f * (i + 1), 0, 2, "coiltronics", i + 1, i + 6);

        }
        createHelperParamountModule("coil_coiltronics_helper_module", "§6Coil Upgrade Module", 1, 0,
                .05f, .05f, 0, 2, "coiltronics", 1, GTValues.LuV);
        createHelperParamountModule("the_debater_embassy_helper_module", "§3The Debater", 1, 0,
                .1f, 0.15f, 0.05f, 2, "embassy", 1, GTValues.EV);
        for (int i = 1; i < 5; i++) {
            createHelperEUModifierModule("satiating_hungry_helper_module", "§3Satiating Module", i * 2, i / 2,
                    -.05f - (0.05f * i),
                    -.075f - (0.05f * i), .05f + (0.05f * i), 1, i, "hungry", i * 3);
            // grandmas_love_hungry_helper_module

        }
        createHelperEUModifierModule("grandmas_love_hungry_helper_module", "§6Grandmas Love Module", 6, 2, .5f, .50f,
                2f,
                3, 1, "hungry", 10);
        createHelperEUModifierModule("grandmas_love_hungry_helper_module", "§6Grandmas Love Module", 8, 8, .75f, 1f,
                3f,
                3, 2, "hungry", 15);
        createHelperEUModifierModule("grandmas_love_hungry_helper_module", "§6Grandmas Love Module", 10, 16, 1f, 2f,
                5f,
                3, 3, "hungry", 20);

        createHelperEUModifierModule("stomach_hungry_helper_module", "§6Stomach Module", 1, 0, 0.05f, 0.15f, 0,
                1, 1, "hungry", 1);

        createHelperEUModifierModule("stomach_hungry_helper_module", "§6Stomach Module", 5, 0, 0.075f, 0.20f, .05f,
                1, 2, "hungry", 2);

        createHelperEUModifierModule("stomach_hungry_helper_module", "§6Stomach Module", 6, 0, 0.125f, 0.25f, .1f,
                1, 3, "hungry", 3);






    }
    public static final ItemEntry<FishNetComponentItem> FISH_NET_STRING = createFishNetItem("fish_net", "String Fish Net", 1, 1);
    public static final ItemEntry<FishNetComponentItem> FISH_NET_FIBER = createFishNetItem("fiber_fish_net", "Fiber Fish Net", 3, 8);
    public static final ItemEntry<FishNetComponentItem> FISH_NET_PBI = createFishNetItem("pbi_fish_net", "PBI Plastic Fish Net", 5, 64);


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
                                moduleSpace)))
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/" + tierName + "_helper_module"))
                        .texture("layer1", prov.modLoc("item/" + id)))
                .tag(VoyagerTags.HELPER_MODULES)
                .register();
    }

    private static void createHelperEUModifierModule(String id, String lang,
                                                     int tier, int pars,
                                                     float eutm, float eatm,
                                                     float outputMod,
                                                     int moduleSpace, int levelName, String paramount_data, int lvl) {
        VOYAGERCORE_REGISTRATE
                .item(id + "_" + levelName, HelperModuleComponentTooltipItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(lang + " " +
                        VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[levelName]).substring(0, 2) +
                        levelName)
                .onRegister(item -> item.attachComponents(
                        new EnergyHelperModuleItemModifierComponent(tier, pars, moduleSpace, true, paramount_data, eutm,
                                eatm, outputMod, lvl)))
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/paramount_helper_module_" + levelName))
                        .texture("layer1", prov.modLoc("item/" + id)))
                .tag(VoyagerTags.HELPER_MODULES)
                .register();
    }

    private static void createHelperParamountModule(String id, String lang,
                                                    int lvl, int pars,
                                                    float eutReduce, float speed,
                                                    float outputMod,
                                                    int moduleSpace, String paramountData, int levelName, int tier) {
        VOYAGERCORE_REGISTRATE
                .item(id + "_" + levelName, HelperModuleComponentTooltipItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(lang + " " +
                        VoyagerVoltageTierUtils.getVoltageTierColorStringShortForm(VN[levelName]).substring(0, 2) +
                        levelName)
                .onRegister(item -> item.attachComponents(
                        new HelperModuleItemModifierComponent(tier, pars, eutReduce, speed, outputMod, false,
                                moduleSpace, true, paramountData, lvl)))
                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/paramount_helper_module_" + levelName))
                        .texture("layer1", prov.modLoc("item/" + id)))
                .tag(VoyagerTags.HELPER_MODULES)
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
                .tag(VoyagerTags.HELPER_MODULES)
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
                .tag(VoyagerTags.HELPER_MODULES)
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
                    .tag(VoyagerTags.HELPERS)
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
                    .tag(VoyagerTags.HELPER_HULLS)
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
                    .tag(VoyagerTags.HELPERS)
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
                    .tag(VoyagerTags.HELPER_HULLS)
                    .register();
            SPECIALIZED_HELPERS.put(tier, helper);
            SPECIALIZED_HELPER_HULLS.put(tier, hull);

            SPECIALIZED_HULL_TO_HELPER.put(hull, helper);
        }
    }

    private static ItemEntry<HelperComponentItem> createParamountHelper(String id, String type, String lang,
                                                                        int baseLevel, String paramountData,
                                                                        float xpScale, int baseTier) {
        ItemEntry<HelperComponentItem> hull = VOYAGERCORE_REGISTRATE
                .item(id + "_" + type + "_helper_hull", HelperComponentItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(lang + " Hull")
                .onRegister(i -> i.attachComponents(
                        new ParamountHelperItemComponent(
                                GTRecipeTypes.DUMMY_RECIPES,
                                3,
                                baseTier,
                                true,
                                paramountData, baseLevel, xpScale)))
                .tag(VoyagerTags.HELPER_HULLS)
                .register();

        ItemEntry<HelperComponentItem> helper = VOYAGERCORE_REGISTRATE
                .item(id + "_" + type + "_helper", HelperComponentItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(lang)
                .onRegister(i -> i.attachComponents(
                        new ParamountHelperItemComponent(
                                GTRecipeTypes.DUMMY_RECIPES,
                                3,
                                baseTier,
                                false,
                                paramountData, baseLevel, xpScale)))
                .tag(VoyagerTags.HELPERS)
                .register();
        PARAMOUNT_HULL_TO_HELPER.put(paramountData, helper);

        return helper;
    }

    private static ItemEntry<HelperComponentItem> createEnergyParamountHelper(String id, String type, String lang,
                                                                              int baseLevel, String paramountData,
                                                                              float xpScale, float baseEUt,
                                                                              float baseEatTime) {
        ItemEntry<HelperComponentItem> hull = VOYAGERCORE_REGISTRATE
                .item(id + "_" + type + "_helper_hull", HelperComponentItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(lang + " Hull")
                .onRegister(i -> i.attachComponents(
                        new EnergyModParamountHelperItemComponent(
                                GTRecipeTypes.DUMMY_RECIPES,
                                2 + baseLevel,
                                baseLevel,
                                true,
                                paramountData, baseLevel, xpScale, baseEUt, baseEatTime)))
                .tag(VoyagerTags.HELPER_HULLS)
                .register();

        ItemEntry<HelperComponentItem> helper = VOYAGERCORE_REGISTRATE
                .item(id + "_" + type + "_helper", HelperComponentItem::new).properties(
                        properties -> properties.stacksTo(1))
                .lang(lang)
                .onRegister(i -> i.attachComponents(
                        new EnergyModParamountHelperItemComponent(
                                GTRecipeTypes.DUMMY_RECIPES,
                                2 + baseLevel,
                                baseLevel,
                                false,
                                paramountData, baseLevel, xpScale, baseEUt, baseEatTime)))
                .tag(VoyagerTags.HELPERS)
                .register();
        PARAMOUNT_HULL_TO_HELPER.put(paramountData, helper);

        return helper;
    }

    private static ItemEntry<FishNetComponentItem> createFishNetItem(String name, String lang, int tier, int pars)
    {

        return VOYAGERCORE_REGISTRATE.item(name, FishNetComponentItem::new)
                .lang(lang)
                .onRegister(i -> i.attachComponents(
                        new FishNetItemComponent(tier, pars)
                ))
                .register();
    }

    public static void init() {}
}
