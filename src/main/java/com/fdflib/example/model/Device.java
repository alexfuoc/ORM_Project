package com.fdflib.example.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;
import com.fdflib.model.state.FdfTenant;

/**
 * Device Model. Holds the data for the device
 * and user attached to the device.
 */
public class Device extends CommonState {
    public long userId = -1L;
    public String description = "";
    public String deviceType = "";

    @FdfIgnore
    public User user = null;


    public Device(){super();}
}
