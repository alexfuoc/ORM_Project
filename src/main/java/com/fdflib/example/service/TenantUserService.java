package com.fdflib.example.service;

import com.fdflib.example.model.TenantUser;
import com.fdflib.example.model.User;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.ArrayList;
import java.util.List;

public class TenantUserService extends FdfCommonServices {

    public TenantUser saveTenantUser(TenantUser tu) {
        if (tu != null) {
            return this.save(TenantUser.class, tu).current;
        }
        return null;
    }

    public TenantUser getTenantUserById(long id) {
        return getTenantUserWithHistoryById(id).current;

    }

    /**
        Queries for all users by tenant
        Returns each with history as a List<FdfEntity<TenantUser>>
     **/
    public List<FdfEntity<TenantUser>> getTenantUserByTenantWithHistory(long tenantId) {

        List<FdfEntity<TenantUser>> tenantUsersByTenant
                = getEntitiesByValueForPassedField(TenantUser.class, "tenantId", Long.toString(tenantId));

        return tenantUsersByTenant;
    }

    public List<User> getUsersByTenantId(long tenantId) {
        UserService us = new UserService();

        List<FdfEntity<TenantUser>> tenantUsersByTenantWithHistory = getTenantUserByTenantWithHistory(tenantId);
        List<User> users = new ArrayList<>();
        for(FdfEntity<TenantUser> tenantUserWithHistory: tenantUsersByTenantWithHistory) {
            if(tenantUserWithHistory.current != null) {
                users.add(us.getUserById(tenantUserWithHistory.current.userId));
            }
        }
        return users;
    }

    public FdfEntity<TenantUser> getTenantUserWithHistoryById(long id) {
        FdfEntity<TenantUser> TenantUser = new FdfEntity<>();

        // get the test
        if (id >= 0) {
            TenantUser = this.getEntityById(TenantUser.class, id);
        }

        return TenantUser;
    }
}