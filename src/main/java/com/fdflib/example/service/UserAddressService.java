package com.fdflib.example.service;

import com.fdflib.example.model.UserAddress;
import com.fdflib.service.impl.FdfCommonServices;

public class UserAddressService extends FdfCommonServices {
    public UserAddress saveUserAddress(UserAddress userAddress) {
        if(userAddress!= null) {
            return this.save(UserAddress.class, userAddress).current;
        }
        return null;
    }
}
