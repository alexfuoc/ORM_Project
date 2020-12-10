package com.fdflib.example.service;

import com.fdflib.example.model.Floor;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.ArrayList;
import java.util.List;

public class FloorService extends FdfCommonServices {

    public Floor saveFloor(Floor floor) {
        if(floor != null) {
            return this.save(Floor.class, floor).current;
        }
        return null;
    }

    public Floor getFloorById(long id) {
        return getFloorWithHistoryById(id).current;
    }
    
    public List<Floor> getFloorsByLocation(long locationId, long tenantId){
        List<FdfEntity<Floor>> floorsByLocationWithHistory = getEntitiesByValueForPassedField(Floor.class,
                "locationId", Long.toString(locationId), tenantId);

        List<Floor> floors = new ArrayList<>();

        for(FdfEntity<Floor> floorWithHistory: floorsByLocationWithHistory) {
            if(floorWithHistory.current != null) {
                floors.add(floorWithHistory.current);
            }
        }
        return floors;
    }


    public FdfEntity<Floor> getFloorWithHistoryById(long id) {
        FdfEntity<Floor> floor = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            floor = this.getEntityById(Floor.class, id);
        }

        return floor;
    }
}
