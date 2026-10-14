package com.jzells.voyagercore.util;

import lombok.Getter;

@Getter
public enum DimensionList {

    OVERWORLD("overworld",15,true),
    EARTH_ORBIT("earth_orbit",30,true),
    TWILIGHT("twilight_forest",0,false),
    NETHER("nether",0,false),
    END("end",0,false),
    MOON("moon",20,true),
    MOON_ORBIT("moon_orbit", 40,true),
    MARS("mars",10,true),
    MARS_ORBIT("mars_orbit",20,true),
    GLACIO("glacio",5,true),
    GLACIO_ORBIT("glacio_orbit",10,true),
    VENUS("venus",0,false),
    VENUS_ORBIT("venus_orbit",50,true),
    MERCURY("mercury",40,true),
    MERCURY_ORBIT("mercury_orbit",80,true),
    DEFAULT("default",10,true);

    private final String resourceLocation;
    private final byte solarStrength;
    private final boolean solarAvailable;

    DimensionList(String string, int solarStrength, boolean solarAvailable) {
        this.resourceLocation = string;
        this.solarStrength = (byte) solarStrength;
        this.solarAvailable = solarAvailable;
    }

    public static DimensionList getFromString(String resourceLocation) {
        for (DimensionList dimension : DimensionList.values()) {
            if (dimension.resourceLocation.equals(resourceLocation)) {
                return dimension;
            }
        }
        return DEFAULT;
    }
}
