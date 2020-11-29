package com.fdflib.example.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;

/**
 * Beacon Class
 * Will have many beacons per location
 */
public class Beacon extends CommonState {
    public int minorId = 0;
    public int majorId = 0;
    public int uuid = 0;
    public String name = "";
    public String description = "";
    public double Lat = 0;
    public double Long = 0;
    public double Alt = 0;
    public long floorId = -1L;
    public long locationId = -1L;

    @FdfIgnore
    public Floor floor = null;
    @FdfIgnore
    public Location location = null;

    public Beacon(){super();}
}
