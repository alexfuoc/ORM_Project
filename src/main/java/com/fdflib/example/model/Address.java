package com.fdflib.example.model;

import com.fdflib.model.state.CommonState;

/**
 * Address Class
 */
public class Address extends CommonState {
    public String streetAdd1 = "";
    public String streetAdd2 = "";
    public String city = "";
    public String state = "";
    public String zipCode = "";
    public String country = "";

    public Address() {super();}
}
