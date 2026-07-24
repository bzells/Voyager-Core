package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ClassCanBeRecord")
public class HelperItemComponent implements IItemComponent {

    public static final HelperItemComponent NULL_HELPER = new HelperItemComponent(null, "null");
    @Getter
    private final GTRecipeType BASE_RECIPE_TYPE;
    @Getter
    private int tier;
    @Getter
    private final String TYPE;

    private ArrayList<GTRecipeType> recipeTypes = new ArrayList<>();

    public HelperItemComponent(GTRecipeType recipeType, String type) {
        this.BASE_RECIPE_TYPE = recipeType;
        this.tier = GTValues.ULV;
        this.TYPE = type;


    }
}
