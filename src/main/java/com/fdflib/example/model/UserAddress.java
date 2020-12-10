package com.fdflib.example.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;

/**
 * UserAddress class, creates many-to-many connection between
 * Address and User.
 */
public class UserAddress extends CommonState {
    public long addressId = -1L;
    public long userId = -1L;
    public String description = "";


    @FdfIgnore
    public User user = null;

    @FdfIgnore
    public Address address = null;

    public UserAddress(){super();}
}