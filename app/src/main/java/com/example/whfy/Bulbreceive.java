package com.example.whfy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Bulbreceive {

    @SerializedName("/lights/2/state/on")
    @Expose
    private Boolean lights2StateOn;

    public Boolean getLights2StateOn() {
        return lights2StateOn;
    }

    public void setLights2StateOn(Boolean lights2StateOn) {
        this.lights2StateOn = lights2StateOn;
    }
}