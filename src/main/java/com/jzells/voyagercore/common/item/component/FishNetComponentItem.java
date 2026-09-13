package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.item.ComponentItem;

import com.jzells.voyagercore.common.item.component.tooltip.FishNetItemTooltipComponent;

public class FishNetComponentItem extends ComponentItem {

    public FishNetComponentItem(Properties properties) {
        super(properties);
        this.attachComponents(new FishNetItemTooltipComponent());
    }
}
