package com.example.whfy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bulblight {

    private boolean on;
    private int hue;
    private int bri = 254;

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

    public void setBri(int bri) { this.bri = bri; }

    public boolean getOn() {
        return on;
    }

    public int getHue() {
        return hue;
    }

    public int getBri() { return bri; }
}
