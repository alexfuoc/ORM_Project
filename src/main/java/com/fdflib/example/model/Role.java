package com.fdflib.example.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;

/**
 * Role class
 */
public class Role extends CommonState{

    public String name = "";
    public String description = "";

    public Role() {
        super();
    }
}
