package com.maikw.CPE200ProjectCAREN;

public class Shop {
    protected int maxCredit;
    protected int currentCredit;
    protected int meleePrice;
    protected int rangedPrice;
    protected int aoePrice;

    public Shop() {
        this.maxCredit = 9999;
        this.currentCredit = 0;
        this.meleePrice = 100;
        this.rangedPrice = 200;
        this.aoePrice = 150;
    }

    public void buyMelee(){

    }

    public void buyRanged(){

    }

    public void buyAOE(){

    }

    public int getMaxCredit() {
        return maxCredit;
    }

    public int getCurrentCredit() {
        return currentCredit;
    }

    public void setCurrentCredit(int currentCredit) {
        this.currentCredit = currentCredit;
    }

    public int getMeleePrice() {
        return meleePrice;
    }

    public int getRangedPrice() {
        return rangedPrice;
    }

    public int getAoePrice() {
        return aoePrice;
    }
}
