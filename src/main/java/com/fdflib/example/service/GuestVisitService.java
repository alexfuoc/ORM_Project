package com.fdflib.example.service;

import com.fdflib.example.model.GuestVisit;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.List;

public class GuestVisitService extends FdfCommonServices {
    public GuestVisit saveGuestVisit(GuestVisit visit) {
        if(visit != null) {
            return this.save(GuestVisit.class, visit).current;
        }
        return null;
    }

    /**
     * Gets all current Guest Visits by tenant
     * @param tenantId
     * @return
     */
    public List<GuestVisit> getAllVisitsByTenant(long tenantId) {
        List<GuestVisit> visits = getAllCurrent(GuestVisit.class, tenantId);

        return visits;
    }

    public FdfEntity<GuestVisit> getVisitWithHistoryById(long id) {
        FdfEntity<GuestVisit> gv = new FdfEntity<>();

        // get the visit
        if(id >= 0) {
            gv = this.getEntityById(GuestVisit.class, id);
        }

        return gv;
    }

    public GuestVisit getVisitById(long id) {
        return getVisitWithHistoryById(id).current;

    }
}
