package com.maikw.CPE200ProjectCAREN;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Random;
import java.util.function.Supplier;

@JsonIgnoreProperties(value = { "programNode", "variables", "area" })
public class Virus extends Unit{
    // Constructor
    // Refactor: Change random methods use supplier instead
    public Virus(String name, String type, String geneticCode) {
        super(name, type, geneticCode);
        this.unitClass = "virus";

        switch (type) {
            case "melee" -> {
                setupMelee();
            }
            case "ranged" -> {
                setupRanged();
            }
            case "aoe" -> {
                setupAoe();
            }
        }
        this.currentHealth = maxHealth;

        // Generate random value
        Supplier<Double> randomPos = () -> (-100.0) + (100.0 - (-100.0)) * new Random().nextDouble();
        this.positionX = (double) (Math.round(randomPos.get()*10.0)/10.0);
        this.positionY = (double) (Math.round(randomPos.get()*10.0)/10.0);
        while(range(UnitFactory.createDummy("melee"), this) > 100){
            this.positionX = (double) (Math.round(randomPos.get()*10.0)/10.0);
            this.positionY = (double) (Math.round(randomPos.get()*10.0)/10.0);
        }
    }

    // Impure function
    // Impure function: Modifies state of maxHealth, attackDamage, attackRange and moveSpeed
    // Refactor: Separate setup things into method
    public void setupMelee(){
        this.maxHealth = Config.virusMeleeHealth;
        this.attackDamage = Config.virusMeleeDamage;
        this.attackRange = Config.meleeAttackRange;
        this.moveSpeed = Config.meleeMoveSpeed;
    }

    // Impure function: Modifies state of maxHealth, attackDamage, attackRange and moveSpeed
    public void setupRanged(){
        this.maxHealth = Config.virusRangedHealth;
        this.attackDamage = Config.virusRangedDamage;
        this.attackRange = Config.rangedAttackRange;
        this.moveSpeed = Config.rangedMoveSpeed;
    }

    // Impure function: Modifies state of maxHealth, attackDamage, attackRange and moveSpeed
    public void setupAoe(){
        this.maxHealth = Config.virusAoeHealth;
        this.attackDamage = Config.virusAoeDamage;
        this.attackRange = Config.aoeAttackRange;
        this.aoeRadius = 5;
        this.moveSpeed = Config.aoeMoveSpeed;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int creditReward(){
        return Config.creditReward;
    }

}
