package com.example.whfy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bulblight {

    private Boolean on;

    public Bulblight(){}
    public Bulblight(Boolean on) {
        this.on = on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Boolean getOn() {
        return on;
    }
}
