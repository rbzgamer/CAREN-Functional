package com.maikw.CPE200ProjectCAREN;

public class Inventory {

    protected int meleeCount;
    protected int rangedCount;
    protected int aoeCount;

    // Constructor
    public Inventory(){
        this.meleeCount = 0;
        this.rangedCount = 0;
        this.aoeCount = 0;
    }

    // Impure function
    // Impure function: Output depend on the current state of meleeCount, rangedCount and aoeCount (give same input doesn't get the same output)
    public boolean canPickup(String type){
        return switch (type) {
            case "melee" -> this.meleeCount > 0;
            case "ranged" -> this.rangedCount > 0;
            case "aoe" -> this.aoeCount > 0;
            default -> false;
        };
    }

    // Impure function: Modifies state of meleeCount, rangedCount and aoeCount
    // Refactor: Combining 3 methods of decreasing count into 1 method
    public void decreaseCount(String type){
        switch (type) {
            case "melee" -> this.meleeCount--;
            case "ranged" -> this.rangedCount--;
            case "aoe" -> this.aoeCount--;
        }
    }

    // Impure function: Modifies state of meleeCount, rangedCount and aoeCount
    // Refactor: Combining 3 methods of increasing count into 1 method
    public void increaseCount(String type){
        switch (type) {
            case "melee" -> this.meleeCount++;
            case "ranged" -> this.rangedCount++;
            case "aoe" -> this.aoeCount++;
        }
    }

    // Pure function
    // Pure function: Doesn't modify state, only return a value base on inputs
    public Integer getMeleeCount() {
        return this.meleeCount;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public Integer getRangedCount() {
        return this.rangedCount;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public Integer getAoeCount() {
        return this.aoeCount;
    }
}
