package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.item.ComponentItem;

import com.jzells.voyagercore.common.item.component.tooltip.HelperModuleTooltipComponent;

public class HelperModuleComponentTooltipItem extends ComponentItem {

    public HelperModuleComponentTooltipItem(Properties properties) {
        super(properties);
        this.attachComponents(new HelperModuleTooltipComponent());
    }
}
