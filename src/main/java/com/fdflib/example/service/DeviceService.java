package com.fdflib.example.service;

import com.fdflib.example.model.Device;
import com.fdflib.service.impl.FdfCommonServices;

public class DeviceService extends FdfCommonServices {
    public Device saveDevice(Device device) {
        if(device != null) {
            return this.save(Device.class, device).current;
        }
        return null;
    }
}
