package com.fdflib.example.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;
import com.fdflib.model.state.FdfTenant;

/**
 * TenantRole class, creates many-to-many connection between
 * Tenants and Roles.
 */
public class TenantRole extends CommonState {
    public long tenantId = -1L;
    public long roleId = -1L;
    public String description = "";

    @FdfIgnore
    public Role role = null;
    @FdfIgnore
    public FdfTenant tenant = null;

    public TenantRole(){super();}
}
