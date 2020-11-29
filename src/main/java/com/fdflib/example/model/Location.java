package com.fdflib.example.model;

import com.fdflib.model.state.CommonState;

/**
 * Location Class
 * Long, lat and alt are measured to the 6th decimal.
 * Depending on the device and accuracy the number of decimals could change.
 */
public class Location extends CommonState {
    public String name = "";
    public String description = "";
    public double maxLat = 0; // EX: 128.345804
    public double maxLong = 0;
    public double maxAlt = 0; // In feet
    public double minLat = 0;
    public double minLong = 0;
    public double minAlt = 0;

    public Location() { super(); }
}
