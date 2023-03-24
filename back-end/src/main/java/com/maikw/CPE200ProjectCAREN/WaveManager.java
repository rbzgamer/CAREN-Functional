package com.maikw.CPE200ProjectCAREN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaveManager {
    // Refactor: Remove unnecessary constructor (move setup things to here) and remove some unnecessary variable
    private Integer maxWaveCount = Config.maxWave;
    private Integer currentWaveCount = 0;
    private Integer waveCount = 1;
    private Integer timeBetweenWave = 20;
    private Map<String, List<Virus>> allWaves = new HashMap<>();

    // Impure function
    // Impure function: 
    private List<Virus> createVirusList(int areaMelee, int areaRanged, int areaAOE) {
        List<Virus> viruses = new ArrayList<>();
        for (int i = 0; i < areaMelee; i++) {
            viruses.add(UnitFactory.createVirus("melee"));
        }
        for (int i = 0; i < areaRanged; i++) {
            viruses.add(UnitFactory.createVirus("ranged"));
        }
        for (int i = 0; i < areaAOE; i++) {
            viruses.add(UnitFactory.createVirus("aoe"));
        }
        return viruses;
    }

    public void addVirus() {
        for (int i = 0; i < maxWaveCount; i++) {
            List<Virus> viruses1 = createVirusList(Config.meleeCountPerWave[i], Config.rangedCountPerWave[i], Config.aoeCountPerWave[i]);
            List<Virus> viruses2 = createVirusList(Config.meleeCountPerWave[i], Config.rangedCountPerWave[i], Config.aoeCountPerWave[i]);
            List<Virus> viruses3 = createVirusList(Config.meleeCountPerWave[i], Config.rangedCountPerWave[i], Config.aoeCountPerWave[i]);
            allWaves.put("Wave_" + waveCount + "_Area_0", viruses1);
            allWaves.put("Wave_" + waveCount + "_Area_1", viruses2);
            allWaves.put("Wave_" + waveCount + "_Area_2", viruses3);
            waveCount++;
        }
    }

    public void setCurrentWaveCount(int currentWaveCount) {
        this.currentWaveCount = currentWaveCount;
    }

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
