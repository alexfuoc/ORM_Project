package com.fdflib.example.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;

/**
 * UserRole class, creates many-to-many connection between
 * role and user.
 */
public class UserRole extends CommonState {
    public long userId = -1L;
    public long roleId = -1L;
    public String description = "";

    @FdfIgnore
    public Role role = null;
    @FdfIgnore
    public User user = null;

    public UserRole(){super();}
}
