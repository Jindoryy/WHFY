package com.example.whfy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body {

//    @SerializedName("on")
//    @Expose
//    private Boolean on;
//
//    public Boolean getBodyon() {
//        return on;
//    }
//
//    public void setBodyon(Boolean on) {
//        this.on = on;
//    }

    private Boolean on;

    public Body(){}
    public Body(Boolean on) {
        this.on = on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Boolean getOn() {
        return on;
    }
}
