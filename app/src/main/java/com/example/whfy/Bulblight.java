package com.example.whfy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bulblight {

    private boolean on;
    private int hue;

    public Bulblight(){}
    public Bulblight(boolean on) {
        this.on = on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public boolean getOn() {
        return on;
    }

    public int getHue() {
        return hue;
    }
}
