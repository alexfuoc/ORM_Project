package com.fdflib.example.service;

import com.fdflib.example.model.Role;
import com.fdflib.example.model.TenantRole;
import com.fdflib.example.model.UserRole;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.ArrayList;
import java.util.List;

public class TenantRoleService extends FdfCommonServices {

    public TenantRole saveTenantRole(TenantRole tr) {
        if (tr != null) {
            return this.save(TenantRole.class, tr).current;
        }
        return null;
    }

    public TenantRole getTenantRoleById(long id) {
        return getTenantRoleWithHistoryById(id).current;

    }

    public FdfEntity<TenantRole> getTenantRoleWithHistoryById(long id) {
        FdfEntity<TenantRole> TenantRole = new FdfEntity<>();

        // get the test
        if (id >= 0) {
            TenantRole = this.getEntityById(TenantRole.class, id);
        }

        return TenantRole;
    }

    /**
     * Gets all of the Roles attached to a tenant
     * @param tenantId
     * @return roles
     */
    public List<Role> getRolesByTenant(long tenantId) {
        RoleService rs = new RoleService();

        List<FdfEntity<TenantRole>> tenantRolesByTenant
                = getEntitiesByValueForPassedField(TenantRole.class, "tenantId", Long.toString(tenantId));
        List<Role> roles = new ArrayList<>();

        for(FdfEntity<TenantRole> tenantRoleWithHistory: tenantRolesByTenant){
            if(tenantRoleWithHistory.current != null){
                roles.add(rs.getRoleById(tenantRoleWithHistory.current.roleId));
            }
        }
        return roles;
    }
}