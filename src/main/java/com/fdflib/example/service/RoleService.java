package com.fdflib.example.service;

import com.fdflib.example.model.Role;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

/**
 * RoleService Class
 */
public class RoleService extends FdfCommonServices{

    public Role saveRole(Role role) {
        if(role != null) {
            return this.save(Role.class, role).current;
        }
        return null;
    }

    public Role getRoleById(long id) {
        return getRoleWithHistoryById(id).current;

    }

    public FdfEntity<Role> getRoleWithHistoryById(long id) {
        FdfEntity<Role> role = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            role = this.getEntityById(Role.class, id);
        }

        return role;
    }
}
