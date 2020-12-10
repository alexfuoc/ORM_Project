package com.fdflib.example.service;

import com.fdflib.example.model.Address;
import com.fdflib.service.impl.FdfCommonServices;

public class AddressService extends FdfCommonServices {
    public Address saveAddress(Address address) {
        if(address!= null) {
            return this.save(Address.class, address).current;
        }
        return null;
    }
}
