package com.example.whfy;

public class Bulbchoice {

    private int choice;
    private boolean state;

    Bulbchoice() {}
    Bulbchoice(int choice) {
        this.choice = choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getChoice() {
        return choice;
    }

    public void setState(boolean state) { this.choice = choice; }

    public boolean getState() { return state; }
}
