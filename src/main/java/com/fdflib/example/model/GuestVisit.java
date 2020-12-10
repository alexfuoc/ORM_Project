package com.fdflib.example.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;
import com.fdflib.model.state.FdfTenant;

import java.util.Date;

/**
 * GuestVisit Model.
 * Records the data for a visiting guest.
 */
public class GuestVisit extends CommonState {
    public long userId = -1L;
    public long locationId = -1L;
    public long deviceId = -1L;
    public String userState = "Just Arrived";
    public Date checkInTS = null;
    public Date checkOutTS = null;
    public double destinationLat = 0; // EX: 128.345804
    public double destinationLong = 0;
    public double destinationAlt = 0; // In feet

    @FdfIgnore
    public User user = null;
    @FdfIgnore
    public Location location = null;
    @FdfIgnore
    public Device device = null;

    public GuestVisit(){super();}

}
