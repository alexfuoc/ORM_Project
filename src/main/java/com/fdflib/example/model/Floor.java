package com.fdflib.example.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;

/**
 * Floor Class,
 * Will have one location and tenant.
 * Can have 1 or more floors
 */
public class Floor extends CommonState {
    public String description = "";
    public double maxLat = 0; // EX: 128.345804
    public double maxLong = 0;
    public double maxAlt = 0; // In feet
    public double minLat = 0;
    public double minLong = 0;
    public double minAlt = 0;
    public long locationId = -1L;
    //Tenant Id also

    @FdfIgnore
    public Location location = null;

    public Floor(){super();}

}
