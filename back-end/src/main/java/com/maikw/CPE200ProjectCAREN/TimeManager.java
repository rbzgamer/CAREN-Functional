package com.maikw.CPE200ProjectCAREN;

import java.util.Arrays;
import java.util.List;

public class TimeManager {
    // Refactor: Remove unnecessary constructor (move setup things to here)
    protected double fps = 60.0;
    protected double deltaTime = 1.0;
    public double slowDownMultiplier = 1.0;
    protected double fastForwardMultiplier = 0.025;
    protected String inputType = "";
    protected List<Integer> timeState = Arrays.asList(1, 5, 10, 15, 30);

    // Impure function
    // Impure function: Modifies state of slowDownMultiplier
    public void setSlowDownMultiplier(double slowDownMultiplier) {
        this.slowDownMultiplier = slowDownMultiplier*2;
    }

    // Impure function: Modifies state of FastFowardMultiplier
    public void setFastForwardMultiplier(double fastForwardMultiplier) {
        this.fastForwardMultiplier = fastForwardMultiplier/2;
    }

    // Impure function: Modifies state of inputType
    public void setInputType(String inputType){
        this.inputType = inputType;
    }

    // Pure function
    // Pure function: Doesn't modify state, only return a value base on inputs
    public double getFps(){
        return this.fps;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public double getDeltaTime() {
        return this.deltaTime;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public String getInputType(){
        return this.inputType;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public double getSlowDownMultiplier() {
        return this.slowDownMultiplier;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public double getFastForwardMultiplier() {
        return this.fastForwardMultiplier;
    }
}
