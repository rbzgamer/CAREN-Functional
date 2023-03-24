package com.maikw.CPE200ProjectCAREN;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Area {

    protected List<Unit> units;
    protected List<Virus> viruses;
    protected List<Antibody> antibodies;
    protected double radius = 100;
    protected String name;
    protected List<Virus> rebornQueue;

    // Constructor
    public Area(String name){
        this(name, new ArrayList<>());
    }

    public Area(String name, List<Virus> rebornQueue){
        this.units = new ArrayList<Unit>();
        this.viruses = new ArrayList<Virus>();
        this.antibodies = new ArrayList<Antibody>();
        this.rebornQueue = rebornQueue;
        this.name = name;
    }

    // Impure function
    // Impure function: Modifies state of units
    public void addUnit(Unit dummy){
        this.units.add(dummy);
    }

    // Impure function: Modify state of viruses and units
    public void addVirus(Virus dummy){
        dummy.setArea(this);
        this.viruses.add(dummy);
        this.addUnit(dummy);
    }

    // Impure function: Modify state of viruses
    /**
     *
     * @param dummy รับเป็น list ของ Virus เพื่อที่ง่ายต่อการใช้งานที่เก็บมา
     */
    public void addAllVirus(List<Virus> dummy){
        for(Virus virus : dummy){
            this.addVirus(virus);
        }
    }

    // Impure function: Modifies state of antibodies and units
    public void addAntibody(Antibody dummy){
        dummy.setArea(this);
        this.antibodies.add(dummy);
        this.addUnit(dummy);
    }

    // Impure function: Modify state of antibodies
    /**
     *
     * @param dummy รับเป็น list ของ Antibody เพื่อที่ง่ายต่อการใช้งานที่เก็บมา
     */
    public void addAllAntibody(List<Antibody> dummy){
        for(Antibody antibody : dummy){
            this.addAntibody(antibody);
        }
    }

    // Impure function: Modifies state of units
    public void removeUnit(Unit dummy){
        this.units.remove(dummy);
        dummy.setArea(null);
    }

    // Impure function: Modifies state of viruses
    public void removeVirus(Virus dummy){
        this.viruses.remove(dummy);
        this.removeUnit(dummy);
        dummy.setArea(null);
    }

    // Impure function: Modifies state of antibodies
    public void removeAntibody(Antibody dummy){
        this.antibodies.remove(dummy);
    }

    // Impure function: Output depend on antibodies and viruses size in area (give same input doesn't get the same output)
    // 3 level: 0 = green light -> all good , 1 = yellow light -> antibodies:viruses = 1:3 , 2 = red light -> area taken
    public int alertLevel(){
        if(antibodies.isEmpty()){
            return 2;
        }else if(3*this.antibodies.size() <= this.viruses.size()){
            return 1;
        }else{
            return 0;
        }
    }

    // Impure function: Modifies state of units
    public void evaluate() {
        Iterator<Unit> unitIterator = units.iterator();
        while (unitIterator.hasNext()) {
            Unit unit = unitIterator.next();
            if (unit.isAlive()) {
                unit.setDidActionCommand(false);
                unit.evaluate();
            } else { // if Unit is DEAD
                if (unit.unitClass.equals("antibody")) {
                    unitIterator.remove();
                    if (unit.toSpawn) {
                        ((Antibody) unit).virusToSpawn(rebornQueue);
                    } else {
                        unit.setArea(null);
                    }
                    removeAntibody((Antibody) unit);
                }
            }
        }
    }

    // Impure function: Modifies viruses
    public void snapViruses() {
        units.removeAll(viruses);
        viruses.clear();
    }

    // Pure function
    // Pure function: Doesn't modify state, only return a value base on inputs
    // Refactor: Using stream instead of for loop
    public boolean canPlace(double positionX, double positionY){
        return units.stream().noneMatch(u -> u.getPositionX() == positionX && u.getPositionY() == positionY);
    }

    // Impure function: Doesn't modify state, But output depend on value of alertLevel (give same input doesn't get the same output)
    public boolean isTaken(){
        return this.alertLevel() == 2;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getAlertLevel(){
        return this.alertLevel();
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public List<Unit> getUnits() {
        return this.units;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public List<Virus> getViruses() {
        return this.viruses;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public List<Antibody> getAntibodies() {
        return this.antibodies;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public String getName() {
        return this.name;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public double getRadius(){
        return this.radius;
    }

}