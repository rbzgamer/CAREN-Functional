package com.maikw.CPE200ProjectCAREN;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    Inventory inventory = new Inventory();

    @Test
    void increaseMeleeCount() {
        inventory.increaseCount("melee");
        inventory.increaseCount("melee");
        inventory.increaseCount("melee");
        assertEquals(inventory.getMeleeCount(), 3);
    }

    @Test
    void decreaseMeleeCount() {
        // increase
        inventory.increaseCount("melee");
        inventory.increaseCount("melee");
        inventory.increaseCount("melee");

        // decrease
        inventory.decreaseCount("melee");
        inventory.decreaseCount("melee");
        assertEquals(inventory.getMeleeCount(), 1);
    }

    @Test
    void increaseRangedCount() {
        inventory.increaseCount("ranged");
        inventory.increaseCount("ranged");
        inventory.increaseCount("ranged");
        assertEquals(inventory.getRangedCount(), 3);
    }

    @Test
    void decreaseRangedCount() {
        // increase
        inventory.increaseCount("ranged");
        inventory.increaseCount("ranged");
        inventory.increaseCount("ranged");

        // decrease
        inventory.decreaseCount("ranged");
        inventory.decreaseCount("ranged");
        assertEquals(inventory.getRangedCount(), 1);
    }

    @Test
    void increaseAoeCount() {
        inventory.increaseCount("aoe");
        inventory.increaseCount("aoe");
        inventory.increaseCount("aoe");
        assertEquals(inventory.getAoeCount(), 3);
    }

    @Test
    void decreaseAoeCount() {
        // increase
        inventory.increaseCount("aoe");
        inventory.increaseCount("aoe");
        inventory.increaseCount("aoe");

        // decrease
        inventory.decreaseCount("aoe");
        inventory.decreaseCount("aoe");
        assertEquals(inventory.getAoeCount(), 1);
    }

    @Test
    void canPickup() {
        // can pick up : there are unit in inventory
        inventory.increaseCount("melee");
        inventory.increaseCount("ranged");
        inventory.increaseCount("aoe");
        assertTrue(inventory.canPickup("melee"));
        assertTrue(inventory.canPickup("ranged"));
        assertTrue(inventory.canPickup("aoe"));

        // can't pick up : there is no unit in inventory
        inventory.decreaseCount("melee");
        inventory.decreaseCount("ranged");
        inventory.decreaseCount("aoe");
        assertFalse(inventory.canPickup("melee"));
        assertFalse(inventory.canPickup("ranged"));
        assertFalse(inventory.canPickup("aoe"));
    }
}
