package com.maikw.CPE200ProjectCAREN;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(value = { "programNode", "variables", "area" })
public class Antibody extends Unit{
    // Constructor
    public Antibody(String name, String type, String geneticCode) {
        super(name, type, geneticCode);
        this.unitClass = "antibody";

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
    }

    // Separate setup things into method
    private void setupMelee(){
        this.maxHealth = Config.antibodyMeleeHealth;
        this.attackDamage = Config.antibodyMeleeDamage;
        this.attackRange = Config.meleeAttackRange;
        this.moveSpeed = Config.meleeMoveSpeed;
    }

    private void setupRanged(){
        this.maxHealth = Config.antibodyRangedHealth;
        this.attackDamage = Config.antibodyRangedDamage;
        this.attackRange = Config.rangedAttackRange;
        this.moveSpeed = Config.rangedMoveSpeed;
    }

    private void setupAoe(){
        this.maxHealth = Config.antibodyAoeHealth;
        this.attackDamage = Config.antibodyAoeDamage;
        this.attackRange = Config.aoeAttackRange;
        this.aoeRadius = 5;
        this.moveSpeed = Config.aoeMoveSpeed;
    }

    // Impure function: modifies state of Antibody and virusQueue
    public void virusToSpawn(List<Virus> q){
        Virus reborn = UnitFactory.createVirus(this.getType());
        this.area.removeAntibody(this);
        q.add(reborn);
        reborn.setPositionX(this.getPositionX());
        reborn.setPositionY(this.getPositionY());

    }
    
    // Pure function: doesn't modify state, only return a value base on inputs
    public String getUnitClass(){
        return "antibody";
    }
    
}
