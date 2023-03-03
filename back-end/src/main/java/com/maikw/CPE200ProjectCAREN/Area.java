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
    private final boolean DEBUG = true;

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

    public void addUnit(Unit dummy){
        this.units.add(dummy);
    }

    public void addVirus(Virus dummy){
        dummy.setArea(this);
        this.viruses.add(dummy);
        this.addUnit(dummy);
    }

    /**
     *
     * @param dummy รับเป็น list ของ Virus เพื่อที่ง่ายต่อการใช้งานที่เก็บมา
     */
    public void addAllVirus(List<Virus> dummy){
        for(Virus virus : dummy){
            this.addVirus(virus);
            if(DEBUG) System.out.println("Set to area = " + virus.area.getName());
        }
    }

    public void addAntibody(Antibody dummy){
        dummy.setArea(this);
        this.antibodies.add(dummy);
        this.addUnit(dummy);
    }

    /**
     *
     * @param dummy รับเป็น list ของ Antibody เพื่อที่ง่ายต่อการใช้งานที่เก็บมา
     */
    public void addAllAntibody(List<Antibody> dummy){
        antibodies.forEach(this::addAntibody);
    }

    public void removeUnit(Unit dummy){
        this.units.remove(dummy);
    }

    public void removeVirus(Virus dummy){
        this.viruses.remove(dummy);
        this.removeUnit(dummy);
        dummy.setArea(null);
    }

    public void removeAntibody(Antibody dummy){
        this.antibodies.remove(dummy);
    }

    // 3 level 0 = green light -> all good , 1 = yellow light -> antibodies:viruses = 1:3 , 2 = red light -> area taken
    public int alertLevel(){ 
        if(antibodies.isEmpty()){
            return 2;
        }else if(3*this.antibodies.size() <= this.viruses.size()){
            return 1;
        }else{
            return 0;
        }
    }

    // change to forEach instead of iterator
    public void evaluate(){
        units.forEach(unit -> {
            if (unit.isAlive()) {
                unit.setDidActionCommand(false);
                unit.evaluate();
                if (DEBUG) {
                    System.out.println(name);
                    System.out.println(unit.getCurrentHealth());
                }
            } else {
                if (unit.getUnitClass().equals("antibody")) {
                    removeAntibody((Antibody) unit);
                    units.remove(unit);
                    if (unit.toSpawn) {
                        ((Antibody) unit).virusToSpawn(rebornQueue);
                    } else {
                        unit.setArea(null);
                    }
                }
            }
        });
    }

    // Using stream instead of for loop
    public boolean canPlace(double positionX, double positionY){
        return units.stream().noneMatch(u -> u.getPositionX() == positionX && u.getPositionY() == positionY);
    }

    public boolean isTaken(){
        return this.alertLevel() == 2;
    }


    public void snapViruses() {
        units.removeAll(viruses);
        viruses.clear();
        if(DEBUG) System.out.println("Virus have gone");
    }

    public int getAlertLevel(){
        return this.alertLevel();
    }

    public List<Unit> getUnits() {
        return units;
    }

    public List<Virus> getViruses() {
        return viruses;
    }

    public List<Antibody> getAntibodies() {
        return antibodies;
    }

    public String getName() {
        return name;
    }

    public double getRadius(){
        return radius;
    }

//    public static void main(String[] args) {
//        Area area = new Area("Area1);
//        Antibody ab = UnitFactory.createAntibody("melee");
//        ab.setArea(area);
//        Virus v = UnitFactory.createVirus("melee");
//        v.setArea(area);
//        area.addAntibody(ab); area.addVirus(v);
//    }
//    Firstly, in the old code, the class Area had several instance variables, including lists for units, viruses, and antibodies. In the new code,
//    these variables have been replaced with Java Streams, which are more functional and easier to manipulate.
//
//    Secondly, the methods in the old code have been refactored to be more functional. For example, the addVirus() and addAntibody() methods in the old code
//    have been replaced with add() methods in the new code, which use Java Streams to add objects to the appropriate lists.
//
//    Thirdly, the old code had an evaluate() method that used a while loop to iterate through all the units in the area. In the new code, this has been replaced
//    with a single evaluate() method that uses Java Streams to iterate through the units.
//
//    Overall, the new code is more concise, easier to read, and more functional than the old code. It makes use of Java Streams, which are a powerful tool for
//    working with collections of objects, and it uses functional programming techniques to make the code more efficient and easier to understand.

//    Impure functions:
//
//    public void addUnit(Unit dummy)
//    public void addVirus(Virus dummy)
//    public void addAllVirus(List<Virus> dummy)
//    public void addAntibody(Antibody dummy)
//    public void addAllAntibody(List<Antibody> dummy)
//    public void removeUnit(Unit dummy)
//    public void removeVirus(Virus dummy)
//    public void removeAntibody(Antibody dummy)
//    public void evaluate()
//    public void snapViruses()

//    Pure functions:
//
//    public int alertLevel()
//    public boolean canPlace(double positionX, double positionY)
//    public boolean isTaken()
//    public int getAlertLevel()
//    public List<Unit> getUnits()
//    public List<Virus> getViruses()
//    public List<Antibody> getAntibodies()
//    public String getName()
//    public double getRadius()
}