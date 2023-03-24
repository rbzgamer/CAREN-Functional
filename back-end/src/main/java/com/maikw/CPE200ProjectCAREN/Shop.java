package com.maikw.CPE200ProjectCAREN;

public class Shop {
    protected int maxCredit;
    protected int minCredit;
    protected int currentCredit;
    protected int meleePrice;
    protected int rangedPrice;
    protected int aoePrice;
    protected Inventory inventory;

    // Constructor
    public Shop() {
        this.maxCredit = 9999;
        this.minCredit = 0;
        this.currentCredit = Config.startCredit;
        this.meleePrice = Config.meleePrice;
        this.rangedPrice = Config.rangedPrice;
        this.aoePrice = Config.aoePrice;
    }

    // Impure function
    // Impure function: Modifies state of currentCredit and inventory
    // Refactor: Combining 3 methods of buying into 1 method
    public void buy(String type){
        if(canBuy(type)){
            inventory.increaseCount(type);
            switch (type) {
                case "melee" -> currentCredit -= meleePrice;
                case "ranged" -> currentCredit -= rangedPrice;
                case "aoe" -> currentCredit -= aoePrice;
            }
        }
    }

    // Impure function: Output depend on the current state of currentCredit (give same input doesn't get the same output)
    public boolean canBuy(String type){
        return switch (type) {
            case "melee" -> currentCredit >= meleePrice;
            case "ranged" -> currentCredit >= rangedPrice;
            case "aoe" -> currentCredit >= aoePrice;
            default -> false;
        };
    }

    // Impure function: Modifies state of inventory
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }

    // Impure function: Modifies state of currentCredit
    public void setCurrentCredit(int currentCredit) {
        this.currentCredit = currentCredit;
    }

    // Pure function
    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getMaxCredit() {
        return this.maxCredit;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getCurrentCredit() {
        return this.currentCredit;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getMeleePrice() {
        return this.meleePrice;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getRangedPrice() {
        return this.rangedPrice;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getAoePrice() {
        return this.aoePrice;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getMinCredit() {
        return this.minCredit;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public Inventory getInventory() {
        return this.inventory;
    }

}
