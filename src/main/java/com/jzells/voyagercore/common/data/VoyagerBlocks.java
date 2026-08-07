package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.common.data.models.GTModels;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import com.jzells.voyagercore.common.block.ReflectorBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import com.jzells.voyagercore.VoyagerCore;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import java.util.function.Supplier;

import static com.jzells.voyagercore.VoyagerCore.VOYAGERCORE_REGISTRATE;

public class VoyagerBlocks {

    static {
        VOYAGERCORE_REGISTRATE.creativeModeTab(() -> VoyagerCreativeModTab.MAIN);
    }
    public static final BlockEntry<Block> CASING_RADIANT_TITANEX = createCasingBlock("radiant_titanex_casing",
            VoyagerCore.id("block/casing/radiant_titanex_casing"));

    public static final BlockEntry<Block> CASING_VENT_RADIANT_TITANEX = createCasingBlock(
            "radiant_titanite_vent_casing", VoyagerCore.id("block/casing/radiant_titanex_vent_casing"));

    public static final BlockEntry<Block> CASING_FOUNDRY = createCasingBlock(
            "foundry_casing", VoyagerCore.id("block/casing/foundry_casing"));

    public static final BlockEntry<Block> CASING_FROST_CONDUCTING = createCasingBlock(
            "frost_conducting_casing", VoyagerCore.id("block/casing/frost_conducting_casing"));

    public static final BlockEntry<Block> CASING_CHEM_PLANT = createCasingBlock(
            "chemical_plant_casing", VoyagerCore.id("block/casing/chemical_plant_casing"));

    public static final BlockEntry<Block> CASING_INDUSTRIAL_MACERATION = createCasingBlock(
            "industrial_maceration_casing", VoyagerCore.id("block/casing/industrial_maceration_casing"));

    public static final BlockEntry<Block> CASING_BEAM_CONTROL = createCasingBlock(
            "beam_control_casing", VoyagerCore.id("block/casing/beam_control_casing"));

    public static final BlockEntry<Block> CASING_TITANITE = createCasingBlock(
            "titanite_casing", VoyagerCore.id("block/casing/titanite_casing"));

    public static final BlockEntry<Block> CASING_SPACE_FARING = createCasingBlock(
            "space_faring_casing", VoyagerCore.id("block/casing/space_faring_casing"));

    public static final BlockEntry<Block> CASING_INDUSTRIAL_CONTROL = createCasingBlock(
            "industrial_control_casing", VoyagerCore.id("block/casing/industrial_control_casing"));

    public static final BlockEntry<Block> CASING_INDUSTRIAL_ASSEMBLY = createCasingBlock(
            "industrial_assembly_casing", VoyagerCore.id("block/casing/industrial_assembly_casing"));

    public static final BlockEntry<Block> CASING_INDUSTRIAL_SOLID = createCasingBlock(
            "solid_industrial_casing", VoyagerCore.id("block/casing/solid_industrial_casing"));

    public static final BlockEntry<Block> CASING_PLATINUM = createCasingBlock(
            "platinum_casing", VoyagerCore.id("block/casing/platinum_casing"));

    public static final BlockEntry<Block> CASING_CONDENSATION_RESISTANT_TUNGSTEN = createCasingBlock(
            "condensation_resistant_tungsten_casing",
            VoyagerCore.id("block/casing/condensation_resistant_tungsten_casing"));

    public static final BlockEntry<Block> CASING_HEATPROOF_HELPER = createCasingBlock(
            "heatproof_helper_casing",
            VoyagerCore.id("block/casing/heatproof_helper_casing"));

    public static final BlockEntry<Block> CASING_CLEAN_ASSEMBLY = createCasingBlock(
            "clean_assembly_casing",
            VoyagerCore.id("block/casing/clean_assembly_casing"));

    // inert_ptfe_helper_casing
    public static final BlockEntry<Block> CASING_OSTRUM = createCasingBlock(
            "ostrum_casing", VoyagerCore.id("block/casing/ostrum_casing"));

    public static final BlockEntry<ReflectorBlock> REFLECTOR_STANDARD = VOYAGERCORE_REGISTRATE
            .block("standard_reflector",ReflectorBlock::new)
            .initialProperties(()->Blocks.IRON_BLOCK)
            .properties(p->p.isValidSpawn((a,b,c,d)->false))
            .addLayer(()->RenderType::solid)
            .exBlockstate(GTModels.cubeAllModel(VoyagerCore.id("block/cooling_lamp")))
            .item(BlockItem::new)
            .build()
            .register();


    /// 0: Casing, 1: Gearbox, 2: Pipe Casing
    public static final BlockEntry<Block>[] STRUCTURE_ARRAY_RHODIUM_PLATED_PALLADIUM = fastBulkBlock("rhodium_plated_palladium");

    /// 0: Casing, 1: Gearbox, 2: Pipe Casing
    public static final BlockEntry<Block>[] STRUCTURE_ARRAY_NAQUADAH_ALLOY = fastBulkBlock("naquadah_alloy");

    /// 0: Casing, 1: Gearbox, 2: Pipe Casing
    public static final BlockEntry<Block>[] STRUCTURE_ARRAY_DARMSTADTIUM = fastBulkBlock("darmstadtium");

    /// 0: Casing, 1: Gearbox, 2: Pipe Casing
    public static final BlockEntry<Block>[] STRUCTURE_ARRAY_NEUTRONIUM = fastBulkBlock("neutronium");

    public static BlockEntry<Block>[] fastBulkBlock(String name) {
        @SuppressWarnings("unchecked") // I have no idea if this is safe or not.
        BlockEntry<Block>[] r = new BlockEntry[3];
        r[0] = fastCreateCasingBlock(name);
        r[1] = fastCreateGearboxBlock(name);
        r[2] = fastCreatePipeCasingBlock(name);
        return r;
    }

    public static BlockEntry<Block> fastCreateCasingBlock(String name) {
        String blockName = "%s_casing".formatted(name);
//        ResourceLocation resourceLocation = VoyagerCore.id("block/casing/industrial_assembly_casing");
        ResourceLocation resourceLocation = VoyagerCore.id("block/casing/solid/casing_%s".formatted(name));
        return createCasingBlock(blockName, resourceLocation);
    }

    public static BlockEntry<Block> fastCreatePipeCasingBlock(String name) {
        String blockName = "%s_pipe_casing".formatted(name);
//        ResourceLocation resourceLocation = VoyagerCore.id("block/casing/industrial_assembly_casing");
        ResourceLocation resourceLocation = VoyagerCore.id("block/casing/pipe/casing_pipe_%s".formatted(name));
        return createCasingBlock(blockName, resourceLocation);
    }

    public static BlockEntry<Block> fastCreateGearboxBlock(String name) {
        String blockName = "%s_gearbox".formatted(name);
//        ResourceLocation resourceLocation = VoyagerCore.id("block/casing/industrial_assembly_casing");
        ResourceLocation resourceLocation = VoyagerCore.id("block/casing/gearbox/casing_gearbox_%s".formatted(name));
        return createCasingBlock(blockName, resourceLocation);
    }

    public static final BlockEntry<Block> COOLING_LAMP = VOYAGERCORE_REGISTRATE.block("cooling_lamp", Block::new)
            .initialProperties(() -> Blocks.SEA_LANTERN)
            .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false)
                    .sound(SoundType.GLASS)
                    .strength(0.3f, 8.0f))
            .addLayer(() -> RenderType::solid)
            .exBlockstate(GTModels.cubeAllModel(VoyagerCore.id("block/cooling_lamp")))
            .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
            .item(BlockItem::new)
            .build()
            .register();

    // Ripped Directly from GTCEu

    public static BlockEntry<Block> createCasingBlock(String name, ResourceLocation texture) {
        return createCasingBlock(name, Block::new, texture, () -> Blocks.IRON_BLOCK,
                () -> RenderType::solid);
    }

    public static BlockEntry<Block> createCasingBlock(String name,
                                                      NonNullFunction<BlockBehaviour.Properties, Block> blockSupplier,
                                                      ResourceLocation texture,
                                                      NonNullSupplier<? extends Block> properties,
                                                      Supplier<Supplier<RenderType>> type) {
        return VOYAGERCORE_REGISTRATE.block(name, blockSupplier)
                .initialProperties(properties)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(type)
                .exBlockstate(GTModels.cubeAllModel(texture))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .item(BlockItem::new)
                .build()
                .register();
    }

    public static void init() {};
}
