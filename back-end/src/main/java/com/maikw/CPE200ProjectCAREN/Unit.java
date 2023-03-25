package com.maikw.CPE200ProjectCAREN;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maikw.CPE200ProjectCAREN.apiclasses.ApiData_Vector2;
import com.maikw.CPE200ProjectCAREN.behavior_evaluator.BehaviorEvaluator;
import com.maikw.CPE200ProjectCAREN.behavior_evaluator.SyntaxError;
import com.maikw.CPE200ProjectCAREN.behavior_evaluator.nodes.Node;

@JsonIgnoreProperties(value = { "programNode", "variables", "area" })
public class Unit {

    protected double positionX;
    protected double positionY;
    protected int maxHealth;
    protected int currentHealth;
    protected int moveCost;
    protected int moveSpeed;
    protected int attackDamage;
    protected int detectRange;
    protected int attackRange;
    protected int aoeRadius;
    protected int dangerRange;
    protected int lifeSteal;
    protected String type;
    protected String name;
    protected Map<String, Double> variables;
    public Node programNode;
    protected Area area;
    protected String unitClass;
    protected boolean didActionCommand = false;
    protected boolean toSpawn = true;

    // constructor
    public Unit(String name, String type, String geneticCode) {
        variables = new HashMap<>();
        this.name = name;
        this.type = type;
        this.moveCost = Config.moveCost;
        this.detectRange = Config.detectRange;
        this.dangerRange = Config.dangerRange;
        this.lifeSteal = Config.lifeSteal;
        this.aoeRadius = 0;
        BehaviorEvaluator be = new BehaviorEvaluator(geneticCode, this);
        try {
            programNode = be.parseProgram();
        } catch (SyntaxError e) {

            e.printStackTrace();
        }
    }

    // Impure function: Modifies value of range
    public void move(String direction) {
        Unit unit = findClosestUnitDirection("all", direction);
        double range = 0;
        if (unit != null) {
            range = range(this, unit);
        } else {
            range = Double.MAX_VALUE;
        }
        if (range > this.dangerRange) {
            positionEval(direction);
        }
    }

    // Impure function: Modifies state of unit
    public void attack(String direction) {
        String targetClass = "";
        if (this.unitClass.equals("virus"))
            targetClass = "antibody";
        else if (this.unitClass.equals("antibody"))
            targetClass = "virus";
        attackEval(targetClass, direction);

    }

    // Impure function: Modifies state of unit
    public void attack(Unit target) {
        target.takeDamage(attackDamage);
        bloodSteal();

    }

    // Impure function: Modifies value of currentHealth
    private void bloodSteal() {
        if (currentHealth + lifeSteal <= maxHealth) {
            currentHealth += lifeSteal;
        } else {
            currentHealth = maxHealth;
        }
    }

    // Impure function: Modifies value of currentHealth
    public void takeDamage(int dmg) {
        if (dmg >= 0) {
            if (!isAlive()) {
                this.currentHealth = 0;
            } else {
                this.currentHealth -= dmg;
            }
        }
    }

    // Impure function: Modifies state of unit's position
    private void positionEval(String direction) {
        Unit dummy = UnitFactory.createDummy("melee");
        switch (direction) {
            case "up" -> {
                this.positionY += moveSpeed;
                if (range(dummy, this) > 100) {
                    this.positionY -= moveSpeed;
                }
            }
            case "upright" -> {
                this.positionX += moveSpeed;
                this.positionY += moveSpeed;
                if (range(dummy, this) > 100) {
                    this.positionX -= moveSpeed;
                    this.positionY -= moveSpeed;
                }
            }
            case "right" -> {
                this.positionX += moveSpeed;
                if (range(dummy, this) > 100) {
                    this.positionX -= moveSpeed;
                }
            }
            case "downright" -> {
                this.positionX += moveSpeed;
                this.positionY -= moveSpeed;
                if (range(dummy, this) > 100) {
                    this.positionX -= moveSpeed;
                    this.positionY += moveSpeed;
                }
            }
            case "down" -> {
                this.positionY -= moveSpeed;
                if (range(dummy, this) > 100) {
                    this.positionY += moveSpeed;
                }
            }
            case "downleft" -> {
                this.positionX -= moveSpeed;
                this.positionY -= moveSpeed;
                if (range(dummy, this) > 100) {
                    this.positionX += moveSpeed;
                    this.positionY += moveSpeed;
                }
            }
            case "left" -> {
                this.positionX -= moveSpeed;
                if (range(dummy, this) > 100) {
                    this.positionX += moveSpeed;
                }
            }
            case "upleft" -> {
                this.positionX -= moveSpeed;
                this.positionY += moveSpeed;
                if (range(dummy, this) > 100) {
                    this.positionX += moveSpeed;
                    this.positionY -= moveSpeed;
                }
            }
        }

    }

    // Impure function: Modifies value of angle
    public static double getAngle(Unit a, Unit b) {
        double angle = Math.toDegrees(Math.atan2(b.positionY - a.positionY, b.positionX - a.positionX));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    // Impure function: Modifies value of range, angle and state of unit
    // Refactor use stream.forEach replace normal for loop
    public void attackEval(String targetUnit, String direction) {
        Unit target = findClosestUnitDirection(targetUnit, direction);
        if (target != null) {
            double range = range(this, target);
            double angle = getAngle(this, target);
            int directionAngle = directionConverter(direction);
            if (range < attackRange && angle >= (directionAngle - 22.5) && angle <= (directionAngle + 22.5)) {
                this.attack(target);
                if (this.type.equals("aoe")) {
                    List<Unit> units = this.area.getUnits();
                    units.stream().forEach(u -> {
                        if (range(target, u) <= this.aoeRadius) {
                            if (!u.getName().equals(this.name) && !u.getName().equals(target.getName())) {
                                attack(u);
                            }
                        }
                    });
                }
            }
        }
    }

    // Impure function: Modifies value of angle,
    private int senseEval(String classUnit) {
        Unit closestUnit = findClosestUnit(classUnit);
        if (closestUnit == null) {
            return 0;
        }
        double angle = getAngle(this, closestUnit);
        int directionAngle = directionValue(angle, "");
        double min = range(this, closestUnit);
        if (min <= dangerRange) {
            return 10 + directionAngle;
        } else if (min <= attackRange) {
            return 20 + directionAngle;
        } else if (min <= detectRange) {
            return 30 + directionAngle;
        }
        return 0;
    }

    // Impure function: Modifies value of baseDirectionValue, classUnit, angle and
    // range
    private int senseUnitEval(List<Unit> units, String direction) {
        double min = Integer.MAX_VALUE;
        int baseDirectionValue = directionValue(0, direction);
        String classUnit = "";

        for (Unit u : units) {
            double angle = getAngle(this, u);
            int directionValue = directionValue(angle, "");
            double range = range(this, u);
            if (directionValue != baseDirectionValue) {
            } else {
                if (this.detectRange < range) {
                } else {
                    if (range < min) {
                        min = range;
                        classUnit = u.getUnitClass();
                    }
                }
            }
        }

        int classValue = 0;
        if (classUnit.equals("virus")) {
            classValue = 1;
        } else if (classUnit.equals("antibody")) {
            classValue = 2;
        }

        if (min == Integer.MAX_VALUE) {
            return 0;
        } else if (min <= dangerRange) {
            return 10 + classValue;
        } else if (min <= attackRange) {
            return 20 + classValue;
        } else if (min <= detectRange) {
            return 30 + classValue;
        }
        return 0;
    }

    // Impure function: Modifies value of min and closesUnit
    public Unit findClosestUnit(String classUnit) {
        Unit closestUnit = null;
        List<? extends Unit> units = null;
        if (classUnit.equals("virus")) {
            units = this.area.getViruses();
        } else if (classUnit.equals("antibody")) {
            units = this.area.getAntibodies();
        } else if (classUnit.equals("all")) {
            units = this.area.getUnits();
        } else {
            units = new ArrayList<>();
        }
        double min = Integer.MAX_VALUE;
        for (Unit u : units) {
            double range = range(this, u);
            if (this.detectRange > range) {
                if (range < min) {
                    min = range;
                    closestUnit = u;
                }
            }
        }
        return closestUnit;
    }

    // Impure function: Modifies value of direc, range, angle, min, closestUnit and
    // state of units
    public Unit findClosestUnitDirection(String classUnit, String direction) {
        Unit closestUnit = null;
        double direc = directionConverter(direction);
        List<? extends Unit> units = null;
        if (classUnit.equals("virus")) {
            units = this.area.getViruses();
        } else if (classUnit.equals("antibody")) {
            units = this.area.getAntibodies();
        } else if (classUnit.equals("all")) {
            units = this.area.getUnits();
        }
        double min = Integer.MAX_VALUE;

        for (Unit u : units) {
            double range = range(this, u);
            double angle = getAngle(this, u);
            if (direc != 0) {
                if (this.detectRange > range && (angle <= direc + 22.5 && angle >= direc - 22.5)) {
                    if (range < min) {
                        min = range;
                        if (!u.getName().equals(this.getName())) {
                            closestUnit = u;
                        }
                    }
                }
            } else {
                if (this.detectRange > range && (angle >= (360 - 22.5) && angle <= (360))
                        || (angle >= 0.0 && angle <= 22.5)) {
                    if (range < min) {
                        min = range;
                        if (!u.getName().equals(this.getName())) {
                            closestUnit = u;
                        }
                    }
                }
            }
        }
        return closestUnit;
    }

    // Impure function: Modifies value of returnData
    public ApiData_Vector2 getPosition() {
        ApiData_Vector2 returnData = new ApiData_Vector2();
        returnData.setX(getPositions("x"));
        returnData.setY(getPositions("y"));
        return returnData;
    }

    // Impure function: Modifies value of position x and y
    // Refactor: Combining 2 methods of set position into 1 method
    public void setPosition(String set, double num) {
        switch (set) {
            case "setX" -> this.positionX = num;
            case "setY" -> this.positionY = num;
        }
    }

    // Impure function: Modifies value of currentHealth
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    // Impure function: Modifies value of area
    public void setArea(Area area) {
        this.area = area;
    }

    // Impure function: Modifies value of position x
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    // Impure function: Modifies value of position y
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    // Impure function: Modifies value of didActionCommand
    public void setDidActionCommand(boolean state) {
        this.didActionCommand = state;
    }

    // Impure function: Modifies value of toSpawn
    public void setToSpawn(boolean toSpawn) {
        this.toSpawn = toSpawn;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int sense(String mode, String direction) {
        switch (mode) {
            case "virus" -> {
                return senseEval("virus");
            }
            case "antibody" -> {
                return senseEval("antibody");
            }
            case "nearby" -> {
                return senseUnitEval(area.getUnits(), direction);
            }
        }
        return 0;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public static double range(Unit a, Unit b) {
        return Math.sqrt(Math.pow((a.positionX - b.positionX), 2) + Math.pow((a.positionY - b.positionY), 2));
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public static int directionValue(double angle, String direction) {
        if (direction.equals("")) {
            if (angle >= (90 - 22.5) && angle <= (90 + 22.5)) {
                return 1;
            } else if (angle >= (45 - 22.5) && angle <= (45 + 22.5)) {
                return 2;
            } else if ((angle >= (360 - 22.5) && angle <= (360)) || (angle >= 0.0 && angle <= 22.5)) {
                return 3;
            } else if (angle >= (315 - 22.5) && angle <= (315 + 22.5)) {
                return 4;
            } else if (angle >= (270 - 22.5) && angle <= (270 + 22.5)) {
                return 5;
            } else if (angle >= (225 - 22.5) && angle <= (225 + 22.5)) {
                return 6;
            } else if (angle >= (180 - 22.5) && angle <= (180 + 22.5)) {
                return 7;
            } else if (angle >= (135 - 22.5) && angle <= (135 + 22.5)) {
                return 8;
            } else {
                return 0;
            }
        } else {
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

    // Pure function: Doesn't modify state, only return a value base on inputs
    public static int directionConverter(String direction) {
        return switch (direction) {
            case "up" -> 90;
            case "upright" -> 45;
            case "right" -> 0;
            case "downright" -> 315;
            case "down" -> 270;
            case "downleft" -> 225;
            case "left" -> 180;
            case "upleft" -> 135;
            default -> 0;
        };
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    // Refactor: Combining 2 methods of get position into 1 method
    public double getPositions(String position) {
        double po = 0;
        switch (position) {
            case "x" -> po = positionX;
            case "y" -> po = positionY;
        }
        return po;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    // Refactor: Combining 9 methods of get maxHealth, currentHealth, cost, speed,
    // attackDamage, detectRange, dangerRange, attackRange, lifeSteal into 1 method
    public int gets(String wantGet) {
        int get = 0;
        switch (wantGet) {
            case "getMaxHealth" -> get = maxHealth;
            case "getCurrentHealth" -> get = currentHealth;
            case "getMoveCost" -> get = moveCost;
            case "getMoveSpeed" -> get = moveSpeed;
            case "getAttackDamage" -> get = attackDamage;
            case "getDetectRange" -> get = detectRange;
            case "getDangerRange" -> get = dangerRange;
            case "getAttackRange" -> get = attackRange;
            case "getLifeSteal" -> get = lifeSteal;
        }
        return get;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public double getPositionX() {
        return positionX;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public double getPositionY() {
        return positionY;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public Area getArea() {
        return area;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public boolean isAlive() {
        return currentHealth > 0;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public Map<String, Double> getVariables() {
        return variables;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public void evaluate() {
        programNode.evaluate();
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public String getName() {
        return name;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public String getType() {
        return type;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getMaxHealth() {
        return maxHealth;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getCurrentHealth() {
        return currentHealth;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getMoveCost() {
        return moveCost;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getMoveSpeed() {
        return moveSpeed;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getAttackDamage() {
        return attackDamage;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public double getDetectRange() {
        return detectRange;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public double getDangerRange() {
        return dangerRange;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public double getAttackRange() {
        return attackRange;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getLifeSteal() {
        return lifeSteal;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public String getUnitClass() {
        return unitClass;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public boolean getDidActionCommand() {
        return didActionCommand;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public boolean isToSpawn() {
        return toSpawn;
    }
}