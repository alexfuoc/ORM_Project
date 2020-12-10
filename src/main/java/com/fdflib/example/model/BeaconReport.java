package com.fdflib.example.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;
import com.fdflib.model.state.FdfTenant;

/**
 * BeaconReport Model
 * All readings from devices location by the beacons
 * 	id int NOT NULL auto_increment,
 * 	deviceId int,
 * 	minorId int,
 *     majorId int,
 *     ts TIMESTAMP,
 *     userId int,
 *     clientId int,
 *     RSSI int NOT NULL,
 *     txPower int NOT NULL,
 *     accuracy double NOT NULL,
 *     remoteTime bigint NOT NULL,
 */

public class BeaconReport extends CommonState {
    public int majorId = 0;
    public int minorId = 0;
    public int RSSI = 0;
    public int txPower = 0;
    public double accuracy = 0;
    public int remoteTime = 0;
    public long tenantId = -1L;
    public long userId = -1L;
    public long locationId = -1L;
    public long deviceId = -1L;

    @FdfIgnore
    public User user = null;
    @FdfIgnore
    public FdfTenant tenant = null;
    @FdfIgnore
    public Location location = null;
    @FdfIgnore
    public Device device = null;

    public BeaconReport(){super();}
}
