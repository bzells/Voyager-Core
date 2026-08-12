package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@SuppressWarnings("ClassCanBeRecord")
public class HelperItemComponent implements IItemComponent {

    public static final HelperItemComponent NULL_HELPER = new HelperItemComponent(null, "null", 0, 0, false, 1, false);
    @Getter
    private final GTRecipeType BASE_RECIPE_TYPE;
    @Getter
    @Setter
    private int tier;
    @Getter
    @Setter
    private String TYPE;

    @Getter
    @Setter
    private int MODULE_COUNT;

    @Getter
    protected int MAX_MODULE_COUNT;

    @Getter
    @Setter
    private boolean isHull;

    @Getter
    private final int recipeCount;

    @Getter
    private final boolean isSpecialized;

    private ArrayList<GTRecipeType> recipeTypes = new ArrayList<>();

    public HelperItemComponent(GTRecipeType recipeType, String type, int moduleCount, int tier, boolean isHull,
                               int recipeCount, boolean specialized) {
        this.BASE_RECIPE_TYPE = recipeType;
        this.MAX_MODULE_COUNT = moduleCount;
        this.tier = tier;
        this.TYPE = type;
        this.isHull = isHull;
        this.recipeCount = specialized ? 1 : recipeCount;
        this.isSpecialized = specialized;
    }

    @Getter
    private final String TAG_ELEMENT = "modifiers";
}
