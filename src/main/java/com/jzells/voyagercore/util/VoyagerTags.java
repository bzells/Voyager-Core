package com.jzells.voyagercore.util;

import com.gregtechceu.gtceu.api.data.tag.TagUtil;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class VoyagerTags {

    public static final TagKey<Item> HELPERS = TagUtil.createItemTag("helpers");
    public static final TagKey<Item> HELPER_HULLS = TagUtil.createItemTag("helper_hulls");
    public static final TagKey<Item> HELPER_MODULES = TagUtil.createItemTag("helper_modules");
}
