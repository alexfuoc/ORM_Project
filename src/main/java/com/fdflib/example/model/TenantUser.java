package com.fdflib.example.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;
import com.fdflib.model.state.FdfTenant;

/**
 * TenantUser class, creates many-to-many connection between
 * Tenants and Users.
 */
public class TenantUser extends CommonState {
    public long tenantId = -1L;
    public long userId = -1L;
    public String description = "";

    @FdfIgnore
    public User user = null;
    @FdfIgnore
    public FdfTenant tenant = null;

    public TenantUser(){super();}
}