package com.fdflib.example.service;

import com.fdflib.example.model.Beacon;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.ArrayList;
import java.util.List;

public class BeaconService extends FdfCommonServices {
    public Beacon saveBeacon(Beacon beacon) {
        if(beacon != null) {
            return this.save(Beacon.class, beacon).current;
        }
        return null;
    }

    public Beacon getBeaconById(long id) {
        return getBeaconWithHistoryById(id).current;
    }

    public List<Beacon> getBeaconsByFloor(long floorId, long tenantId){
        List<FdfEntity<Beacon>> beaconsByFloorWithHistory = getEntitiesByValueForPassedField(Beacon.class,
                "floorId", Long.toString(floorId), tenantId);

        List<Beacon> beacons = new ArrayList<>();

        for(FdfEntity<Beacon> beaconWithHistory: beaconsByFloorWithHistory) {
            if(beaconWithHistory.current != null) {
                beacons.add(beaconWithHistory.current);
            }
        }
        return beacons;
    }


    public FdfEntity<Beacon> getBeaconWithHistoryById(long id) {
        FdfEntity<Beacon> beacon = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            beacon = this.getEntityById(Beacon.class, id);
        }

        return beacon;
    }
}
