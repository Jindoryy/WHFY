package com.example.whfy;

public class Bulbcolor {

    private int hue;

    public Bulbcolor(){}
    public Bulbcolor(int hue) {
        this.hue = hue;
    }

    public void setOn(int hue) {
        this.hue = hue;
    }

    public int getOn() {
        return hue;
    }
}
