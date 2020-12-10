package com.fdflib.example.service;

import com.fdflib.example.model.BeaconReport;
import com.fdflib.service.impl.FdfCommonServices;

public class BeaconReportService extends FdfCommonServices {
    public BeaconReport saveBeaconReport(BeaconReport br) {
        if(br != null) {
            return this.save(BeaconReport.class, br).current;
        }
        return null;
    }
}
