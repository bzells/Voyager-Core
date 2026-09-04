package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.item.component.IItemComponent;

import lombok.Getter;

@Getter
public class FishNetItemComponent implements IItemComponent {

    private final int tier;
    private final int pars;

    public FishNetItemComponent(int tier, int pars) {
        this.tier = tier;
        this.pars = pars;
    }
}
