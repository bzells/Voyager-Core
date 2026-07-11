package com.jzells.voyagercore.common.data;

import com.gregtechceu.gtceu.common.data.models.GTModels;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

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
