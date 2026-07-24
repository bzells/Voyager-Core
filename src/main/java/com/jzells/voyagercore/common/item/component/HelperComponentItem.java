package com.jzells.voyagercore.common.item.component;

import com.gregtechceu.gtceu.api.item.ComponentItem;

public class HelperComponentItem extends ComponentItem {
    public HelperComponentItem(Properties properties) {
        super(properties);
        this.attachComponents(new HelperTooltipComponent());
    }

}
