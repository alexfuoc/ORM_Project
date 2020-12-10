package com.fdflib.example.service;

import com.fdflib.example.model.Location;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.ArrayList;
import java.util.List;

/**
 * LocationService Class
 */
public class LocationService extends FdfCommonServices{

    public Location saveLocation(Location location) {
        if(location != null) {
            return this.save(Location.class, location).current;
        }
        return null;
    }

    public Location getLocationById(long id) {
        return getLocationWithHistoryById(id).current;
    }

    /**
     * Get all current locations by tenant
     * @param tenantId
     * @return
     */
    public List<Location> getLocationByTenant(long tenantId){
//        List<FdfEntity<Location>> locationsByTenantWithHistory = getEntitiesByValueForPassedField(Location.class,
//                "tid", Long.toString(tenantId));

        List<Location> locations = getAllCurrent(Location.class, tenantId);

//        for(FdfEntity<Location> locationWithHistory: locationsByTenantWithHistory) {
//            if(locationWithHistory.current != null) {
//                locations.add(locationWithHistory.current);
//            }
//        }
        return locations;
    }

    public FdfEntity<Location> getLocationWithHistoryById(long id) {
        FdfEntity<Location> Location = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            Location = this.getEntityById(Location.class, id);
        }

        return Location;
    }
}