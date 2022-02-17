package com.maikw.CPE200ProjectCAREN;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maikw.CPE200ProjectCAREN.behavior_evaluator.BehaviorEvaluator;
import com.maikw.CPE200ProjectCAREN.behavior_evaluator.SyntaxError;
import com.maikw.CPE200ProjectCAREN.behavior_evaluator.nodes.Node;

public class Unit {

    protected double positionX = 0.0;
    protected double positionY = 0.0;
    protected int maxHealth = 100;
    protected int currentHealth = 100;
    protected int moveCost = 20;
    protected int attackDamage = 0;
    protected double detectRange = 0.0;
    protected double attackRange = 0.0;
    protected double dangerRange = 0.0;
    protected int lifeSteal = 10;
    protected String type = "";
    protected String name;
    protected Map<String, Double> variables;
    protected Node programNode;
    protected Area area;
    protected String unitClass;

    public Unit(String name, String type, String geneticCode){
        variables = new HashMap<>();
        this.name = name;
        this.type = type;
        this.detectRange = 40;
        this.dangerRange = 5;
        if(type.equals("melee")){
            this.attackDamage = 20;
            this.attackRange = 10;
        }else if(type.equals("ranged")){
            this.attackDamage = 20;
            this.attackRange = 30;
        }else if(type.equals("aoe")){
            this.attackDamage = 15;
            this.attackRange = 10;
        }
        BehaviorEvaluator be = new BehaviorEvaluator(geneticCode,this);
        try{
            programNode = be.parseProgram();
        }catch(SyntaxError e){
            System.out.println(e.getMessage());
        }
    }

    public void move(String direction){
        if (sense("nearby", direction) == 0){
            System.out.println("Unit " + name + " moved " + direction);
        }else{
            System.out.println("There is already a unit there. Unit " + name + " can't move to " + direction);
        }
    }

    public void attack(String direction){
        System.out.println("Unit " + name + " attacked " + direction);
    }

    public void attack(Unit target){
        if(target.currentHealth <= this.attackDamage){
            target.currentHealth = 0;
        }else{
            target.currentHealth -= this.attackDamage;
        }
        System.out.println("Unit " + target.name + " current hp = " + target.currentHealth);
    }    

    public int sense(String mode, String direction){
        System.out.println("Unit " + name + " sensed " + mode + " " + direction);
        switch (mode) {
            case "virus" -> {
                return senseVirusEval(area.getViruses());
            }
            case "antibody" -> {
                return senseAntibodyEval(area.getAntibodies());
            }
            case "nearby" -> {
                return senseUnitEval(area.getUnits(), direction);
            }
        }
        return 0;
    }

    public static double range(Unit a, Unit b){
        return Math.sqrt(Math.pow((a.positionX - b.positionX),2) + Math.pow((a.positionY - b.positionY),2));
    }

    public static int getAngle(Unit a, Unit b){
        int angle = (int) Math.toDegrees(Math.atan2(b.positionY - a.positionY, b.positionX - a.positionX));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    public static int directionValue(double angle, String direction){
        if(direction.equals("")){
            if(angle >= (90-22.5) && angle <= (90+22.5)){
                return 1;
            }else if(angle >= (45-22.5) && angle <= (45+22.5)){
                return 2;
            }else if((angle >= (360-22.5) && angle <= (360)) || (angle >= 0.0 && angle <= 22.5)){
                return 3;
            }else if(angle >= (315-22.5) && angle <= (315+22.5)){
                return 4;
            }else if(angle >= (270-22.5) && angle <= (270+22.5)){
                return 5;
            }else if(angle >= (225-22.5) && angle <= (225+22.5)){
                return 6;
            }else if(angle >= (180-22.5) && angle <= (180+22.5)){
                return 7;
            }else if(angle >= (135-22.5) && angle <= (135+22.5)){
                return 8;
            }else{
                return 0;
            }
        }else{
            return switch (direction) {
                case "up" -> 1;
                case "upright" -> 2;
                case "right" -> 3;
                case "downright" -> 4;
                case "down" -> 5;
                case "downleft" -> 6;
                case "left" -> 7;
                case "upleft" -> 8;
                default -> 0;
            };
        }

    }

    private int senseVirusEval(List<Virus> viruses){
        double min = Integer.MAX_VALUE;
        int directionAngle = 0;
        for (Virus v : viruses) {
            double angle = getAngle(this, v);
            int directionValue = directionValue((int) angle,"");
            double range = range(this, v);
            if (this.detectRange < range) {
            } else {
                if (range < min){
                    min = range;
                    directionAngle = directionValue;
                }
            }
        }
        if (min == Integer.MAX_VALUE) {
            return 0;
        } else if (min <= dangerRange) {
            return 10  + directionAngle;
        } else if (min <= attackRange) {
            return 20 + directionAngle;
        } else if (min <= detectRange) {
            return 30 + directionAngle;
        }
        return 0;
    }

    private int senseAntibodyEval(List<Antibody> antibodies){
        System.out.println("This X: " + this.positionX + " This Y: " + this.positionY);
        double min = Integer.MAX_VALUE;
        int directionAngle = 0;
        for (Antibody a : antibodies) {
            double angle = getAngle(this, a);
            int directionValue = directionValue((int) angle,"");
            double range = range(this, a);
            if (this.detectRange < range) {
            } else {
                if (range < min){
                    min = range;
                    directionAngle = directionValue;
                }
            }
        }
        if (min == Integer.MAX_VALUE) {
            return 0;
        } else if (min <= dangerRange) {
            return 10  + directionAngle;
        } else if (min <= attackRange) {
            return 20 + directionAngle;
        } else if (min <= detectRange) {
            return 30 + directionAngle;
        }
        return 0;
    }

    private int senseUnitEval(List<Unit> units, String direction){
        double min = Integer.MAX_VALUE;
        int directionAngle = 0;
        int baseDirectionValue = directionValue(0, direction);
        String classUnit = "";
        for (Unit u : units) {
            double angle = getAngle(this, u);
            int directionValue = directionValue((int) angle,"");
            double range = range(this, u);
            if (directionValue != baseDirectionValue) {
            }else{
                if (this.detectRange < range) {
                } else {
                    if (range < min) {
                        min = range;
                        directionAngle = directionValue;
                        classUnit = u.getUnitClass();
                    }
                }
            }
        }
        if (min == Integer.MAX_VALUE) {
            return 0;
        } else if (min <= dangerRange) {
            if(classUnit.equals("Virus")){
                return 10 + 1;
            }else if(classUnit.equals("Antibody")){
                return 10 + 2;
            }
        } else if (min <= attackRange) {
            if(classUnit.equals("Virus")){
                return 20 + 1;
            }else if(classUnit.equals("Antibody")){
                return 20 + 2;
            }
        } else if (min <= detectRange) {
            if(classUnit.equals("Virus")){
                return 30 + 1;
            }else if(classUnit.equals("Antibody")){
                return 30 + 2;
            }
        }
        return 0;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public Area getArea() {
        return area;
    }

    public boolean isAlive(){
        return currentHealth <= 0;
    }

    public Map<String, Double> getVariables(){
        return variables;
    }


    public void evaluate(){
        programNode.evaluate();
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMoveCost() {
        return moveCost;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public double getDetectRange() {
        return detectRange;
    }

    public double getDangerRange() {
        return dangerRange;
    }

    public double getAttackRange() {
        return attackRange;
    }

    public int getLifeSteal() {
        return lifeSteal;
    }

    public String getUnitClass() {
        return unitClass;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

}
