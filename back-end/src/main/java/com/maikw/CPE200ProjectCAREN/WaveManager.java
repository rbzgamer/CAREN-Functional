package com.maikw.CPE200ProjectCAREN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaveManager {
    // Refactor: Remove unnecessary constructor (move setup things to here) and remove some unnecessary variable
    protected Integer maxWaveCount = Config.maxWave;
    protected Integer currentWaveCount = 0;
    protected Integer waveCount = 1;
    protected Integer timeBetweenWave = 20;
    protected Map<String, List<Virus>> allWaves = new HashMap<>();
    protected List<Virus> viruses1 = new ArrayList<>();
    protected List<Virus> viruses2 = new ArrayList<>();
    protected List<Virus> viruses3 = new ArrayList<>();

    // Impure function
    // Impure function: Modifies state of viruses by creating a new list of viruses
    private void createUnitVirus(int areaMelee, int areaRanged, int areaAoe) {
        for(int i = 0 ; i < areaMelee;i++){
            viruses1.add(UnitFactory.createVirus( "melee"));
            viruses2.add(UnitFactory.createVirus( "melee"));
            viruses3.add(UnitFactory.createVirus( "melee"));
        }
        for(int i = 0 ; i < areaRanged ;i++){
            viruses1.add(UnitFactory.createVirus( "ranged"));
            viruses2.add(UnitFactory.createVirus( "ranged"));
            viruses3.add(UnitFactory.createVirus( "ranged"));
        }
        for (int i = 0 ; i < areaAoe;i++){
            viruses1.add(UnitFactory.createVirus( "aoe"));
            viruses2.add(UnitFactory.createVirus( "aoe"));
            viruses3.add(UnitFactory.createVirus( "aoe"));
        }

        allWaves.put("Wave_"+ waveCount.toString() +"Area_"+0, viruses1);
        allWaves.put("Wave_"+ waveCount.toString() +"Area_"+1, viruses2);
        allWaves.put("Wave_"+ waveCount.toString() +"Area_"+2, viruses3);
        waveCount++;
        this.viruses1 = new ArrayList<>();
        this.viruses2 = new ArrayList<>();
        this.viruses3 = new ArrayList<>();
    }

    // Impure function: Modifies state of viruses, waveCount and allWaves
    public void addVirus() {
        for (int i = 0 ; i < maxWaveCount ; ++i) {
            createUnitVirus(Config.meleeCountPerWave[i], Config.rangedCountPerWave[i], Config.aoeCountPerWave[i]);
        }
    }

    // Impure function: Modifies state of currentWaveCount
    public void setCurrentWaveCount(int currentWaveCount) {
        this.currentWaveCount = currentWaveCount;
    }

    // Impure function: Modifies state of maxWaveCount
    public void setMaxWaveCount(int maxWaveCount) {
        this.maxWaveCount = maxWaveCount;
    }

    // Pure function
    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getMaxWaveCount() {
        return maxWaveCount;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getCurrentWaveCount() {
        return currentWaveCount;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public int getTimeBetweenWave() {
        return timeBetweenWave;
    }
}
