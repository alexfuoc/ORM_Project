package com.fdflib.example.service;

import com.fdflib.example.model.IdCredentials;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

/**
 * IdCredentials Service Class
 */
public class IdCredentialsService extends FdfCommonServices{

    public IdCredentials saveRole(IdCredentials id) {
        if(id != null) {
            return this.save(IdCredentials.class, id).current;
        }
        return null;
    }
}