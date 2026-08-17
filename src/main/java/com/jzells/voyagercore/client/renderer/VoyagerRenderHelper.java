package com.jzells.voyagercore.client.renderer;

import com.gregtechceu.gtceu.client.renderer.machine.DynamicRender;

import com.jzells.voyagercore.client.renderer.machine.impl.SuperDonutRender;

public class VoyagerRenderHelper {

    public static DynamicRender<?, ?> createSuperDonutRender() {
        return new SuperDonutRender();
    }
}
