package com.maikw.CPE200ProjectCAREN;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {
    Area area = new Area("Area1");
    Antibody[] abs_melee = {UnitFactory.createAntibody("melee"),UnitFactory.createAntibody("melee"),UnitFactory.createAntibody("melee")};
    Antibody[] abs_ranged = {UnitFactory.createAntibody("ranged"),UnitFactory.createAntibody("ranged"),UnitFactory.createAntibody("ranged")};
    Antibody[] abs_aoe = {UnitFactory.createAntibody("aoe"),UnitFactory.createAntibody("aoe"),UnitFactory.createAntibody("aoe")};
    Virus[] vs_melee = {UnitFactory.createVirus("melee"),UnitFactory.createVirus("melee"),UnitFactory.createVirus("melee")};
    Virus[] vs_ranged = {UnitFactory.createVirus("ranged"),UnitFactory.createVirus("ranged"),UnitFactory.createVirus("ranged")};
    Virus[] vs_aoe = {UnitFactory.createVirus("aoe"),UnitFactory.createVirus("aoe"),UnitFactory.createVirus("aoe")};
    List<Virus> viruses = new ArrayList<Virus>();
    List<Antibody> antibodies = new ArrayList<Antibody>();
    List<Unit> units = new ArrayList<Unit>();

    @Test
    void move() {
        Config.readFile("config/config_1.txt");
        Virus unitMelee = UnitFactory.createVirus("melee"); unitMelee.setArea(area); area.addVirus(unitMelee);
        double baseX = 0;
        double baseY = 0;

        // melee move speed -> 3
        unitMelee.setPosition("setX", baseX); unitMelee.setPosition("setY", baseY);
        // up
        unitMelee.move("up");
        assertEquals(unitMelee.getPositions("x"), baseX);
        assertEquals(unitMelee.getPositions("y"), baseY + unitMelee.gets("getMoveSpeed"));
        baseX = unitMelee.getPositions("x"); baseY = unitMelee.getPositions("y");
        // upright
        unitMelee.move("upright");
        assertEquals(unitMelee.getPositions("x"), baseX + unitMelee.gets("getMoveSpeed"));
        assertEquals(unitMelee.getPositions("y"), baseY + unitMelee.gets("getMoveSpeed"));
        baseX = unitMelee.getPositions("x"); baseY = unitMelee.getPositions("y");
        // right
        unitMelee.move("right");
        assertEquals(unitMelee.getPositions("x"), baseX + unitMelee.gets("getMoveSpeed"));
        assertEquals(unitMelee.getPositions("y"), baseY);
        baseX = unitMelee.getPositions("x"); baseY = unitMelee.getPositions("y");
        // downright
        unitMelee.move("downright");
        assertEquals(unitMelee.getPositions("x"), baseX + unitMelee.gets("getMoveSpeed"));
        assertEquals(unitMelee.getPositions("y"), baseY - unitMelee.gets("getMoveSpeed"));
        baseX = unitMelee.getPositions("x"); baseY = unitMelee.getPositions("y");
        // down
        unitMelee.move("down");
        assertEquals(unitMelee.getPositions("x"), baseX);
        assertEquals(unitMelee.getPositions("y"), baseY - unitMelee.gets("getMoveSpeed"));
        baseX = unitMelee.getPositions("x"); baseY = unitMelee.getPositions("y");
        // downleft
        unitMelee.move("downleft");
        assertEquals(unitMelee.getPositions("x"), baseX - unitMelee.gets("getMoveSpeed"));
        assertEquals(unitMelee.getPositions("y"), baseY - unitMelee.gets("getMoveSpeed"));
        baseX = unitMelee.getPositions("x"); baseY = unitMelee.getPositions("y");
        // left
        unitMelee.move("left");
        assertEquals(unitMelee.getPositions("x"), baseX - unitMelee.gets("getMoveSpeed"));
        assertEquals(unitMelee.getPositions("y"), baseY);
        baseX = unitMelee.getPositions("x"); baseY = unitMelee.getPositions("y");
        // upleft
        unitMelee.move("upleft");
        assertEquals(unitMelee.getPositions("x"), baseX - unitMelee.gets("getMoveSpeed"));
        assertEquals(unitMelee.getPositions("y"), baseY + unitMelee.gets("getMoveSpeed"));
        baseX = unitMelee.getPositions("x"); baseY = unitMelee.getPositions("y");

        // ranged move speed -> 2
        area.removeVirus(unitMelee);
        Virus unitRanged = UnitFactory.createVirus("ranged"); unitRanged.setArea(area); area.addVirus(unitRanged);
        baseX = 0; baseY = 0;
        unitRanged.setPosition("setX", baseX); unitRanged.setPosition("setY", baseY);
        // up
        unitRanged.move("up");
        assertEquals(unitRanged.getPositions("x"), baseX);
        assertEquals(unitRanged.getPositions("y"), baseY + unitRanged.gets("getMoveSpeed"));
        baseX = unitRanged.getPositions("x"); baseY = unitRanged.getPositions("y");
        // upright
        unitRanged.move("upright");
        assertEquals(unitRanged.getPositions("x"), baseX + unitRanged.gets("getMoveSpeed"));
        assertEquals(unitRanged.getPositions("y"), baseY + unitRanged.gets("getMoveSpeed"));
        baseX = unitRanged.getPositions("x"); baseY = unitRanged.getPositions("y");
        // right
        unitRanged.move("right");
        assertEquals(unitRanged.getPositions("x"), baseX + unitRanged.gets("getMoveSpeed"));
        assertEquals(unitRanged.getPositions("y"), baseY);
        baseX = unitRanged.getPositions("x"); baseY = unitRanged.getPositions("y");
        // downright
        unitRanged.move("downright");
        assertEquals(unitRanged.getPositions("x"), baseX + unitRanged.gets("getMoveSpeed"));
        assertEquals(unitRanged.getPositions("y"), baseY - unitRanged.gets("getMoveSpeed"));
        baseX = unitRanged.getPositions("x"); baseY = unitRanged.getPositions("y");
        // down
        unitRanged.move("down");
        assertEquals(unitRanged.getPositions("x"), baseX);
        assertEquals(unitRanged.getPositions("y"), baseY - unitRanged.gets("getMoveSpeed"));
        baseX = unitRanged.getPositions("x"); baseY = unitRanged.getPositions("y");
        // downleft
        unitRanged.move("downleft");
        assertEquals(unitRanged.getPositions("x"), baseX - unitRanged.gets("getMoveSpeed"));
        assertEquals(unitRanged.getPositions("y"), baseY - unitRanged.gets("getMoveSpeed"));
        baseX = unitRanged.getPositions("x"); baseY = unitRanged.getPositions("y");
        // left
        unitRanged.move("left");
        assertEquals(unitRanged.getPositions("x"), baseX - unitRanged.gets("getMoveSpeed"));
        assertEquals(unitRanged.getPositions("y"), baseY);
        baseX = unitRanged.getPositions("x"); baseY = unitRanged.getPositions("y");
        // upleft
        unitRanged.move("upleft");
        assertEquals(unitRanged.getPositions("x"), baseX - unitRanged.gets("getMoveSpeed"));
        assertEquals(unitRanged.getPositions("y"), baseY + unitRanged.gets("getMoveSpeed"));
        baseX = unitRanged.getPositions("x"); baseY = unitRanged.getPositions("y");

        // aoe move speed -> 1
        area.removeVirus(unitRanged);
        Virus unitAoe = UnitFactory.createVirus("aoe"); unitAoe.setArea(area); area.addVirus(unitAoe);
        baseX = 0; baseY = 0;
        unitAoe.setPosition("setX", baseX); unitAoe.setPosition("setY", baseY);
        // up
        unitAoe.move("up");
        assertEquals(unitAoe.getPositions("x"), baseX);
        assertEquals(unitAoe.getPositions("y"), baseY + unitAoe.gets("getMoveSpeed"));
        baseX = unitAoe.getPositions("x"); baseY = unitAoe.getPositions("y");
        // upright
        unitAoe.move("upright");
        assertEquals(unitAoe.getPositions("x"), baseX + unitAoe.gets("getMoveSpeed"));
        assertEquals(unitAoe.getPositions("y"), baseY + unitAoe.gets("getMoveSpeed"));
        baseX = unitAoe.getPositions("x"); baseY = unitAoe.getPositions("y");
        // right
        unitAoe.move("right");
        assertEquals(unitAoe.getPositions("x"), baseX + unitAoe.gets("getMoveSpeed"));
        assertEquals(unitAoe.getPositions("y"), baseY);
        baseX = unitAoe.getPositions("x"); baseY = unitAoe.getPositions("y");
        // downright
        unitAoe.move("downright");
        assertEquals(unitAoe.getPositions("x"), baseX + unitAoe.gets("getMoveSpeed"));
        assertEquals(unitAoe.getPositions("y"), baseY - unitAoe.gets("getMoveSpeed"));
        baseX = unitAoe.getPositions("x"); baseY = unitAoe.getPositions("y");
        // down
        unitAoe.move("down");
        assertEquals(unitAoe.getPositions("x"), baseX);
        assertEquals(unitAoe.getPositions("y"), baseY - unitAoe.gets("getMoveSpeed"));
        baseX = unitAoe.getPositions("x"); baseY = unitAoe.getPositions("y");
        // downleft
        unitAoe.move("downleft");
        assertEquals(unitAoe.getPositions("x"), baseX - unitAoe.gets("getMoveSpeed"));
        assertEquals(unitAoe.getPositions("y"), baseY - unitAoe.gets("getMoveSpeed"));
        baseX = unitAoe.getPositions("x"); baseY = unitAoe.getPositions("y");
        // left
        unitAoe.move("left");
        assertEquals(unitAoe.getPositions("x"), baseX - unitAoe.gets("getMoveSpeed"));
        assertEquals(unitAoe.getPositions("y"), baseY);
        baseX = unitAoe.getPositions("x"); baseY = unitAoe.getPositions("y");
        // upleft
        unitAoe.move("upleft");
        assertEquals(unitAoe.getPositions("x"), baseX - unitAoe.gets("getMoveSpeed"));
        assertEquals(unitAoe.getPositions("y"), baseY + unitAoe.gets("getMoveSpeed"));
        baseX = unitAoe.getPositions("x"); baseY = unitAoe.getPositions("y");
        
        // can't move
        baseX = -100; baseY = 0;
        unitAoe.setPosition("setX", baseX); unitAoe.setPosition("setY", baseY);
        // up
        unitAoe.move("up");
        assertEquals(unitAoe.getPositions("x"), baseX);
        assertEquals(unitAoe.getPositions("y"), baseY);
        // down
        unitAoe.move("down");
        assertEquals(unitAoe.getPositions("x"), baseX);
        assertEquals(unitAoe.getPositions("y"), baseY);
        // downleft
        unitAoe.move("downleft");
        assertEquals(unitAoe.getPositions("x"), baseX);
        assertEquals(unitAoe.getPositions("y"), baseY);
        // left
        unitAoe.move("left");
        assertEquals(unitAoe.getPositions("x"), baseX);
        assertEquals(unitAoe.getPositions("y"), baseY);
        // upleft
        unitAoe.move("upleft");
        assertEquals(unitAoe.getPositions("x"), baseX);
        assertEquals(unitAoe.getPositions("y"), baseY);

        baseX = 100; baseY = 0;
        unitAoe.setPosition("setX", baseX); unitAoe.setPosition("setY", baseY);
        // upright
        unitAoe.move("upright");
        assertEquals(unitAoe.getPositions("x"), baseX);
        assertEquals(unitAoe.getPositions("y"), baseY);
        // right
        unitAoe.move("right");
        assertEquals(unitAoe.getPositions("x"), baseX);
        assertEquals(unitAoe.getPositions("y"), baseY);
        // downright
        unitAoe.move("downright");
        assertEquals(unitAoe.getPositions("x"), baseX);
        assertEquals(unitAoe.getPositions("y"), baseY);

    }

    @Test
    void attackDirection() {
        Config.readFile("config/config_1.txt");
        Antibody ab_melee = UnitFactory.createAntibody("melee"); ab_melee.setPosition("setX", 0); ab_melee.setPosition("setY", 1); ab_melee.setArea(area);
        Antibody ab_ranged = UnitFactory.createAntibody("ranged"); ab_ranged.setPosition("setX", -20); ab_ranged.setPosition("setY", -20); ab_ranged.setArea(area);
        Antibody ab_aoe = UnitFactory.createAntibody("aoe"); ab_aoe.setPosition("setX", 0); ab_aoe.setPosition("setY", 10); ab_aoe.setArea(area);
        Virus v_melee = UnitFactory.createVirus("melee"); v_melee.setPosition("setX", 5); v_melee.setPosition("setY", 10); v_melee.setArea(area);
        Virus v_ranged = UnitFactory.createVirus("ranged"); v_ranged.setPosition("setX", -3); v_ranged.setPosition("setY", 1); v_ranged.setArea(area);
        Virus v_aoe = UnitFactory.createVirus("aoe"); v_aoe.setPosition("setX", -20); v_aoe.setPosition("setY", -25); v_aoe.setArea(area);
        area.addAntibody(ab_melee); area.addAntibody(ab_ranged); area.addAntibody(ab_aoe);
        area.addVirus(v_melee); area.addVirus(v_ranged); area.addVirus(v_aoe);

        // melee attack -> attack range = 10, dmg = 20
        ab_melee.attack("left"); // can attack -> ab_melee attack v_ranged
        assertEquals(v_ranged.gets("getCurrentHealth"), v_ranged.gets("getMaxHealth") - ab_melee.gets("getAttackDamage"));
        ab_melee.attack("downright"); // can't attack -> nothing happen

        // ranged attack -> attack range = 30, dmg = 20
        ab_ranged.attack("down"); // can attack -> ab_ranged attack v_aoe
        assertEquals(v_aoe.gets("getCurrentHealth"), v_aoe.gets("getMaxHealth") - ab_ranged.gets("getAttackDamage"));
        ab_ranged.attack("left"); // can't attack -> nothing happen

        // aoe attack -> attack range = 20, dmg = 15
        ab_aoe.attack("right"); // can attack -> ab_aoe attack v_melee
        assertEquals(v_melee.gets("getCurrentHealth"), v_melee.gets("getMaxHealth") - ab_aoe.gets("getAttackDamage"));
        ab_aoe.attack("upleft"); // can't attack -> nothing happen

    }

    @Test
    void attackTarget() {
        Config.readFile("config/config_1.txt");
        Antibody ab_melee = UnitFactory.createAntibody("melee");
        Antibody ab_ranged =  UnitFactory.createAntibody("ranged");
        Antibody ab_aoe =  UnitFactory.createAntibody("aoe");
        Virus v_melee =  UnitFactory.createVirus("melee");
        Virus v_ranged =  UnitFactory.createVirus("ranged");
        Virus v_aoe =  UnitFactory.createVirus("aoe");
        // melee attack -> attack range = 10, dmg = 20
        ab_melee.attack(v_melee);
        assertEquals(v_melee.gets("getCurrentHealth"), v_melee.gets("getMaxHealth") - ab_melee.gets("getAttackDamage"));
        v_melee.attack(ab_melee);
        assertEquals(ab_melee.gets("getCurrentHealth"), ab_melee.gets("getMaxHealth") - v_melee.gets("getAttackDamage"));
        
        // ranged attack -> attack range = 30, dmg = 20
        ab_ranged.attack(v_ranged);
        assertEquals(v_ranged.gets("getCurrentHealth"), v_ranged.gets("getMaxHealth") - ab_ranged.gets("getAttackDamage"));
        v_ranged.attack(ab_ranged);
        assertEquals(ab_ranged.gets("getCurrentHealth"), ab_ranged.gets("getMaxHealth") - v_ranged.gets("getAttackDamage"));

        // aoe attack -> attack range = 20, dmg = 15
        ab_aoe.attack(v_aoe);
        assertEquals(v_aoe.gets("getCurrentHealth"), v_aoe.gets("getMaxHealth") - ab_aoe.gets("getAttackDamage"));
        v_aoe.attack(ab_aoe);
        assertEquals(ab_aoe.gets("getCurrentHealth"), ab_aoe.gets("getMaxHealth") - v_aoe.gets("getAttackDamage"));
    }

    @Test
    void takeDamage() {
        // create dummy unit for testing
        Unit unit = UnitFactory.createDummy("melee");
        unit.setCurrentHealth(100); unit.maxHealth = 100;

        // check max health (100)
        assertEquals(unit.gets("getMaxHealth"), 100);

        // take damage
        unit.takeDamage(1);
        assertEquals(unit.gets("getCurrentHealth"), unit.gets("getMaxHealth") - 1); // check health (99)
        unit.takeDamage(50);
        assertEquals(unit.gets("getCurrentHealth"), unit.gets("getMaxHealth") - 51); // check helath (49)
        unit.takeDamage(-999); // don't receive damage
        assertEquals(unit.gets("getCurrentHealth"), unit.gets("getMaxHealth") - 51); // check helath (49)
        unit.takeDamage(100); // unit is dead
        assertFalse(unit.isAlive());
    }

    @Test
    void sense() {
        Config.readFile("config/config_1.txt");
        Antibody ab_melee = UnitFactory.createAntibody("melee"); ab_melee.setPosition("setX", 0); ab_melee.setPosition("setY", 1); ab_melee.setArea(area);
        Antibody ab_ranged = UnitFactory.createAntibody("ranged"); ab_ranged.setPosition("setX", -20); ab_ranged.setPosition("setY", -20); ab_ranged.setArea(area);
        Antibody ab_aoe = UnitFactory.createAntibody("aoe"); ab_aoe.setPosition("setX", 0); ab_aoe.setPosition("setY", 10); ab_aoe.setArea(area);
        Virus v_melee = UnitFactory.createVirus("melee"); v_melee.setPosition("setX", 5); v_melee.setPosition("setY", 10); v_melee.setArea(area);
        Virus v_ranged = UnitFactory.createVirus("ranged"); v_ranged.setPosition("setX", -3); v_ranged.setPosition("setY", 1); v_ranged.setArea(area);
        Virus v_aoe = UnitFactory.createVirus("aoe"); v_aoe.setPosition("setX", -20); v_aoe.setPosition("setY", -25); v_aoe.setArea(area);
        area.addAntibody(ab_melee); area.addAntibody(ab_ranged); area.addAntibody(ab_aoe);
        area.addVirus(v_melee); area.addVirus(v_ranged); area.addVirus(v_aoe);

        assertEquals(ab_melee.sense("virus",""), 17);
        assertEquals(ab_ranged.sense("virus",""), 15);
        assertEquals(ab_aoe.sense("virus",""), 13);
        assertEquals(v_melee.sense("antibody",""), 17);
        assertEquals(v_ranged.sense("antibody",""), 13);
        assertEquals(v_aoe.sense("antibody",""), 11);
    }

    @Test
    void range() { // formula is Math.sqrt(Math.pow((a.positionX - b.positionX),2) + Math.pow((a.positionY - b.positionY),2))
        // position of virus is always random so if we use method range() to find range it must equal this formula
        Virus a = UnitFactory.createVirus("melee");
        Virus b = UnitFactory.createVirus("ranged");
        Virus c = UnitFactory.createVirus("aoe");
        assertEquals(Unit.range(a, b), Math.sqrt(Math.pow((a.positionX - b.positionX),2) + Math.pow((a.positionY - b.positionY),2)));
        assertEquals(Unit.range(a, c), Math.sqrt(Math.pow((a.positionX - c.positionX),2) + Math.pow((a.positionY - c.positionY),2)));
        assertEquals(Unit.range(b, c), Math.sqrt(Math.pow((b.positionX - c.positionX),2) + Math.pow((b.positionY - c.positionY),2)));
    }

    @Test
    void directionValue() {
        assertEquals(Unit.directionValue(85.346309,""), 1);
        assertEquals(Unit.directionValue(90,""), 1);
        assertEquals(Unit.directionValue(112.4999997,""), 1);

        assertEquals(Unit.directionValue(25.23857903,""), 2);
        assertEquals(Unit.directionValue(45,""), 2);
        assertEquals(Unit.directionValue(67.49389239,""), 2);

        assertEquals(Unit.directionValue(360, ""), 3);
        assertEquals(Unit.directionValue(0, ""), 3);
        assertEquals(Unit.directionValue(22.436070, ""), 3);
        assertEquals(Unit.directionValue(359.304596, ""), 3);

        assertEquals(Unit.directionValue(292.5435623456,""), 4);
        assertEquals(Unit.directionValue(315,""), 4);
        assertEquals(Unit.directionValue(335.23500897,""), 4);

        assertEquals(Unit.directionValue(247.623412, ""), 5);
        assertEquals(Unit.directionValue(270, ""), 5);
        assertEquals(Unit.directionValue(292.23456028, ""), 5);

        assertEquals(Unit.directionValue(202.6134532, ""), 6);
        assertEquals(Unit.directionValue(225, ""), 6);
        assertEquals(Unit.directionValue(247.49586, ""), 6);

        assertEquals(Unit.directionValue(159.4902, ""), 7);
        assertEquals(Unit.directionValue(180, ""), 7);
        assertEquals(Unit.directionValue(200.1354235, ""), 7);

        assertEquals(Unit.directionValue(112.5456 , ""), 8);
        assertEquals(Unit.directionValue(135, ""), 8);
        assertEquals(Unit.directionValue(150.2435, ""), 8);

        assertEquals(Unit.directionValue(0, "up"), 1);
        assertEquals(Unit.directionValue(0, "upright"), 2);
        assertEquals(Unit.directionValue(0, "right"), 3);
        assertEquals(Unit.directionValue(0, "downright"), 4);
        assertEquals(Unit.directionValue(0, "down"), 5);
        assertEquals(Unit.directionValue(0, "downleft"), 6);
        assertEquals(Unit.directionValue(0, "left"), 7);
        assertEquals(Unit.directionValue(0, "upleft"), 8);

    }

    @Test
    void directionConverter() {
        assertEquals(Unit.directionConverter("up"), 90);
        assertEquals(Unit.directionConverter("upright"), 45);
        assertEquals(Unit.directionConverter("right"), 0);
        assertEquals(Unit.directionConverter("downright"), 315);
        assertEquals(Unit.directionConverter("down"), 270);
        assertEquals(Unit.directionConverter("downleft"), 225);
        assertEquals(Unit.directionConverter("left"), 180);
        assertEquals(Unit.directionConverter("upleft"), 135);
    }

    @Test
    void findClosestUnit() {
        Config.readFile("config/config_1.txt");
        Antibody ab_melee = UnitFactory.createAntibody("melee"); ab_melee.setPosition("setX", 0); ab_melee.setPosition("setY", 1); ab_melee.setArea(area);
        Antibody ab_ranged = UnitFactory.createAntibody("ranged"); ab_ranged.setPosition("setX", -20); ab_ranged.setPosition("setY", -20); ab_ranged.setArea(area);
        Antibody ab_aoe = UnitFactory.createAntibody("aoe"); ab_aoe.setPosition("setX", 0); ab_aoe.setPosition("setY", 10); ab_aoe.setArea(area);
        Virus v_melee = UnitFactory.createVirus("melee"); v_melee.setPosition("setX", 5); v_melee.setPosition("setY", 10); v_melee.setArea(area);
        Virus v_ranged = UnitFactory.createVirus("ranged"); v_ranged.setPosition("setX", -3); v_ranged.setPosition("setY", 1); v_ranged.setArea(area);
        Virus v_aoe = UnitFactory.createVirus("aoe"); v_aoe.setPosition("setX", -20); v_aoe.setPosition("setY", -25); v_aoe.setArea(area);
        area.addAntibody(ab_melee); area.addAntibody(ab_ranged); area.addAntibody(ab_aoe);
        area.addVirus(v_melee); area.addVirus(v_ranged); area.addVirus(v_aoe);

        assertEquals(ab_melee.findClosestUnit("virus"), v_ranged); // closest = v_ranged
        assertEquals(v_ranged.findClosestUnit("antibody"), ab_melee); // closest = ab_melee

        assertEquals(ab_ranged.findClosestUnit("virus"), v_aoe); // closest = v_aoe
        assertEquals(v_aoe.findClosestUnit("antibody"), ab_ranged); // closest = ab_ranged

        assertEquals(ab_aoe.findClosestUnit("virus"), v_melee); // closest = v_melee
        assertEquals(v_melee.findClosestUnit("antibody"), ab_aoe); // closest = ab_aoe
    }

    @Test
    void findClosestUnitDirection() {
        Config.readFile("config/config_1.txt");
        Antibody ab_melee = UnitFactory.createAntibody("melee"); ab_melee.setPosition("setX", 0); ab_melee.setPosition("setY", 1); ab_melee.setArea(area);
        Antibody ab_ranged = UnitFactory.createAntibody("ranged"); ab_ranged.setPosition("setX", -20); ab_ranged.setPosition("setY", -20); ab_ranged.setArea(area);
        Antibody ab_aoe = UnitFactory.createAntibody("aoe"); ab_aoe.setPosition("setX", 0); ab_aoe.setPosition("setY", 10); ab_aoe.setArea(area);
        Virus v_melee = UnitFactory.createVirus("melee"); v_melee.setPosition("setX", 5); v_melee.setPosition("setY", 10); v_melee.setArea(area);
        Virus v_ranged = UnitFactory.createVirus("ranged"); v_ranged.setPosition("setX", -3); v_ranged.setPosition("setY", 1); v_ranged.setArea(area);
        Virus v_aoe = UnitFactory.createVirus("aoe"); v_aoe.setPosition("setX", -20); v_aoe.setPosition("setY", -25); v_aoe.setArea(area);
        area.addAntibody(ab_melee); area.addAntibody(ab_ranged); area.addAntibody(ab_aoe);
        area.addVirus(v_melee); area.addVirus(v_ranged); area.addVirus(v_aoe);

        assertEquals(ab_melee.findClosestUnitDirection( "virus","left"), v_ranged); // closest = v_ranged
        assertNull(ab_melee.findClosestUnitDirection("virus", "downright")); // closest = null
        assertEquals(v_ranged.findClosestUnitDirection( "antibody","right"), ab_melee); // closest = ab_melee
        assertNull(v_ranged.findClosestUnitDirection("antibody", "upleft")); // closest = null

        assertEquals(ab_ranged.findClosestUnitDirection("virus", "down"), v_aoe); // closest = v_aoe
        assertNull(ab_ranged.findClosestUnitDirection("virus", "left")); // closest = null
        assertEquals(v_aoe.findClosestUnitDirection("antibody", "up"), ab_ranged); // closest = ab_ranged
        assertNull(v_aoe.findClosestUnitDirection("antibody", "right")); // closest = null

        assertEquals(ab_aoe.findClosestUnitDirection("virus", "right"), v_melee); // closest = v_melee
        assertNull(ab_aoe.findClosestUnitDirection("virus", "upleft")); // closest = null
        assertEquals(v_melee.findClosestUnitDirection("antibody", "left"), ab_aoe); // closest = ab_aoe
        assertNull(v_melee.findClosestUnitDirection("antibody", "downright")); // closest = null
    }

    @Test
    void isAlive() {
        Unit unit = UnitFactory.createDummy("melee");
        unit.setCurrentHealth(100);
        assertTrue(unit.isAlive()); // unit is alive
        unit.takeDamage(150);
        assertFalse(unit.isAlive()); // unit is dead
    }
}
