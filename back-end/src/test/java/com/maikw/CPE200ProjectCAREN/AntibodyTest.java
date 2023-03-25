package com.maikw.CPE200ProjectCAREN;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AntibodyTest {
    List<Virus> queueVirus = new ArrayList<Virus>();
    Virus virusGod = UnitFactory.createVirus("melee");
    Antibody antibody = UnitFactory.createAntibody("melee");
    Area area = new Area("Saiyan");

    @Test
    void virusToSpawn() {
        area.addAntibody(antibody);
        antibody.setPosition("setX", 0); antibody.setPosition("setY", 10);
        while(antibody.gets("getCurrentHealth") > 0){
            virusGod.attack(antibody);
        }
        antibody.virusToSpawn(queueVirus);
        assertEquals(queueVirus.size(), 1);
        assertEquals(queueVirus.get(0).getPositions("x"),0);
        assertEquals(queueVirus.get(0).getPositions("y"), 10);
        assertEquals(area.getAntibodies().size(),0);
    }
}
