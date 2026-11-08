package com.jzells.voyagercore.util.debug;

import net.minecraft.world.phys.Vec3;

import java.awt.*;

public record DebugVector(Vec3 start, Vec3 end, Color color, boolean drawArrow) {}
