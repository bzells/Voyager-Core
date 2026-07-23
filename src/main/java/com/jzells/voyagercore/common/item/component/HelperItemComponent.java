package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import lombok.Getter;

@SuppressWarnings("ClassCanBeRecord")
public class HelperItemComponent implements IItemComponent {

    public static final HelperItemComponent NULL_HELPER = new HelperItemComponent(null, 0);
    @Getter
    private final GTRecipeType recipeType;
    @Getter
    private final int tier;

    public HelperItemComponent(GTRecipeType recipeType, int tier) {
        this.recipeType = recipeType;
        this.tier = tier;
    }
}
