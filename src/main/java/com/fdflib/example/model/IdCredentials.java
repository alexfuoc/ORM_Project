package com.fdflib.example.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;

import java.util.Date;

public class IdCredentials extends CommonState {
    public String idType = "";
    public Date idDateIssued = null;
    public Date idExperation = null;
    public Date dob = null;
    public String gender = "";
    public String eyeColor = "";
    public double height = 0; //in total inches
    public int weight = 0;
    public boolean isPrimary = false;
    public long addressId = -1L;
    public long userId = -1L;

    @FdfIgnore
    public Address address = null;
    @FdfIgnore
    public User user = null;

    public IdCredentials() { super(); }
}
