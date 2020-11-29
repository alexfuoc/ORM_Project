package com.fdflib.example.model;

import com.fdflib.model.state.CommonState;

public class User extends CommonState {
    public String userColor = null; // HEX COLOR STRING, Randomly assigned on input or picked from color picker
    public String firstName = "";
    public String lastName = "";
    public String email = "";
    public String password = "";

    public User(){ super(); }
}
