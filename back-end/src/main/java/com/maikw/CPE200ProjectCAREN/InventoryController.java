package com.maikw.CPE200ProjectCAREN;

import com.maikw.CPE200ProjectCAREN.apiclasses.ApiData_AreaAndPosition;
import com.maikw.CPE200ProjectCAREN.apiclasses.ApiData_Base;
import com.maikw.CPE200ProjectCAREN.apiclasses.ApiData_Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/inventory")
public class InventoryController {
    private final GameHandler gameHandler;

    @Autowired
    public InventoryController(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @CrossOrigin
    @PostMapping(path = "/getinventory")
    public Inventory getInventory(@RequestBody ApiData_Base data){
        Inventory inventory = gameHandler.getGame(data).getInventory();
        return inventory;
    }

    @CrossOrigin
    @PostMapping(path = "/storeunit")
    public String storeUnit(@RequestBody ApiData_Unit data){
        Game game = gameHandler.getGame(data);
        Inventory inventory = game.getInventory();
        Shop shop = game.getShop();
        Area area1 = gameHandler.getGame(data).getAreas().get(0);
        Area area2 = gameHandler.getGame(data).getAreas().get(1);
        Area area3 = gameHandler.getGame(data).getAreas().get(2);
        String name = data.getName();
        Unit unit = null;
        for(Unit u : area1.getUnits()){
            if(u.getName().equals(name)) unit = u;
        }
        if(unit == null) {
            for (Unit u : area2.getUnits()) {
                if (u.getName().equals(name)) unit = u;
            }
            if(unit == null){
                for(Unit u : area3.getUnits()){
                    if(u.getName().equals(name)) unit = u;
                }
            }
        }
        assert unit != null;
        if (unit.getUnitClass().equals("antibody")) {
            if(shop.getCurrentCredit() - unit.moveCost >= shop.getMinCredit()){
                shop.setCurrentCredit(shop.getCurrentCredit() - unit.moveCost);
                unit.setToSpawn(false);
                unit.setCurrentHealth(0);
                switch (unit.getType()) {
                    case "melee" -> {
                        inventory.increaseCount("melee");
                        return "Store Melee Success";
                    }
                    case "ranged" -> {
                        inventory.increaseCount("ranged");
                        return "Store Ranged Success";
                    }
                    case "aoe" -> {
                        inventory.increaseCount("aoe");
                        return "Store AOE Success";
                    }
                }
            }
        }
        return "Unsuccessful something went wrong.";
    }

    @CrossOrigin
    @PostMapping(path = "/pickupunit")
    public String pickupUnit(@RequestBody ApiData_AreaAndPosition data){
        int areaNumber = data.getArea();
        String type = data.getType();
        double positionX = data.getPositionX();
        double positionY = data.getPositionY();
        Game game = gameHandler.getGame(data);
        Inventory inventory = game.getInventory();
        Area area = null;
        if(areaNumber == 1){
            area = game.getAreas().get(0);
        }else if(areaNumber == 2){
            area = game.getAreas().get(1);
        }else if(areaNumber == 3){
            area = game.getAreas().get(2);
        }

        assert area != null;
        if(area.canPlace(positionX, positionY)){
            Antibody ab = UnitFactory.createAntibody(type, game.getGeneticCodeManager());

            switch (type) {
                case "melee" -> {
                    if(inventory.canPickup(type)){
                        inventory.decreaseCount("melee");
                        if(areaNumber == 1){
                            game.getQueueAntibobyArea1().add(ab);
                        }else if(areaNumber == 2){
                            game.getQueueAntibobyArea2().add(ab);
                        }else {
                            game.getQueueAntibobyArea3().add(ab);
                        }
                        ab.setPosition("setX", positionX); ab.setPosition("setY", positionY);
                        ab.setToSpawn(true);
                        return "Pick up Melee Success";
                    }
                }
                case "ranged" -> {
                    if(inventory.canPickup(type)){
                        inventory.decreaseCount("ranged");
                        if(areaNumber == 1){
                            game.getQueueAntibobyArea1().add(ab);
                        }else if(areaNumber == 2){
                            game.getQueueAntibobyArea2().add(ab);
                        }else {
                            game.getQueueAntibobyArea3().add(ab);
                        }
                        ab.setPosition("setX", positionX); ab.setPosition("setY", positionY);
                        ab.setToSpawn(true);
                        return "Pick up Ranged Success";
                    }
                }
                case "aoe" -> {
                    if(inventory.canPickup(type)){
                        inventory.decreaseCount("aoe");
                        if(areaNumber == 1){
                            game.getQueueAntibobyArea1().add(ab);
                        }else if(areaNumber == 2){
                            game.getQueueAntibobyArea2().add(ab);
                        }else {
                            game.getQueueAntibobyArea3().add(ab);
                        }
                        ab.setPosition("setX", positionX); ab.setPosition("setY", positionY);
                        ab.setToSpawn(true);
                        return "Pick up AOE Success";
                    }
                }
            }
        }
        return "Unsuccessful something went wrong";
    }
}
