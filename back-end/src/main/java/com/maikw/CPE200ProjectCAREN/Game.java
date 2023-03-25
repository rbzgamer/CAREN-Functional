package com.maikw.CPE200ProjectCAREN;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game implements Runnable{
    public String id;
    protected Shop shop ;
    protected Integer state  = 1 ;
    protected Boolean notSpawnedYet;
    protected List<Area> areas;
    protected Inventory inventory ;
    protected WaveManager waveManager ;
    protected TimeManager timeManager ;
    protected GeneticCodeManager geneticCodeManager;
    protected List<Antibody> queueAntibobyArea1 ;
    protected List<Antibody> queueAntibobyArea2 ;
    protected List<Antibody> queueAntibobyArea3 ;
    protected List<Virus> queueVirusArea1 ;
    protected List<Virus> queueVirusArea2 ;
    protected List<Virus> queueVirusArea3 ;

    // Constructor
    // Refactor: Use List.of() instead of manual
    public Game(){
        Config.readFile("config/config_1.txt");
        this.shop = new Shop();
        this.notSpawnedYet = true ;
        this.areas = new ArrayList<Area>();
        this.inventory = new Inventory();
        this.waveManager = new WaveManager();
        this.timeManager = new TimeManager();
        this.queueAntibobyArea1 = new ArrayList<Antibody>();
        this.queueAntibobyArea2 = new ArrayList<Antibody>();
        this.queueAntibobyArea3 = new ArrayList<Antibody>();
        this.queueVirusArea1 = new ArrayList<Virus>();
        this.queueVirusArea2 = new ArrayList<Virus>();
        this.queueVirusArea3 = new ArrayList<Virus>();
        this.areas = List.of(
                new Area("area1", queueVirusArea1),
                new Area("area2", queueVirusArea2),
                new Area("area3", queueVirusArea3)
        );
        this.geneticCodeManager = new GeneticCodeManager();

        shop.setInventory(inventory);
    }

    // Impure function: Modifies everything
    // Refactor: Use Math.min instead of if statement , combining putVirusToAllArea() and evaluateAreas() into method
    public void startGameLoop(){
        waitState(5);
        waveManager.addVirus();

        while (hasAntibodies() || notSpawnedYet){

            while(timeManager.inputType.equals("pause")) {
                waitState(1);
                toAddAntiboby();
                pickUpAntiUnit();
            }

            waitState(timeManager.timeState.get(0)); // speed of loop

            while (notSpawnedYet && areas.stream().anyMatch(area -> area.getAntibodies().isEmpty())){
                waitState(1);
                pickUpAntiUnit();
                toAddAntiboby();
                toAddViruse();
            }
            notSpawnedYet = false;
            toAddAntiboby();
            toAddViruse();

            if(!notSpawnedYet && areas.stream().allMatch(area -> area.getViruses().isEmpty()) ) {
                if (waveManager.currentWaveCount < waveManager.maxWaveCount) {
                    waveManager.currentWaveCount++;

                    for (int i = 0; i < timeManager.timeState.get(4); i++) {
                        waitState(1);
                        toAddAntiboby();
                        pickUpAntiUnit();
                    }   // 30 secs

                    putVirusToAllArea();
                }
            }

            // loop check if virus dead in area
            for(int i = 0 ; i < 3; ++i){
                Iterator<Virus> vsIterator = areas.get(i).getViruses().iterator();
                while(vsIterator.hasNext()){
                    Virus vs = vsIterator.next();
                    if(!vs.isAlive()){
                        this.shop.setCurrentCredit(Math.min(this.shop.getCurrentCredit() + vs.creditReward(), shop.getMaxCredit()));
                        vsIterator.remove();
                        areas.get(i).removeVirus(vs);
                    }
                }
            }
            evaluateAreas();
        }

    }

    // Impure function: Modifies state of areas, viruses and waveManager
    private void putVirusToAllArea(){
        putVirusToArea(0);
        putVirusToArea(1);
        putVirusToArea(2);
    }

    // Impure function: Modifies state of areas
    private void evaluateAreas(){
        areas.get(0).evaluate();
        areas.get(1).evaluate();
        areas.get(2).evaluate();
    }

    // Impure function: Doesn't modify state, But output depend on the current state of antibodies in area
    private boolean hasAntibodies(){
        return areas.stream().anyMatch(area -> !area.getAntibodies().isEmpty());
    }

    // Impure function: Modifies state of all of queueAntibodies
    private void toAddAntiboby(){
        areas.get(0).addAllAntibody(queueAntibobyArea1);
        queueAntibobyArea1.clear();
        areas.get(1).addAllAntibody(queueAntibobyArea2);
        queueAntibobyArea2.clear();
        areas.get(2).addAllAntibody(queueAntibobyArea3);
        queueAntibobyArea3.clear();
    }

    // Impure function: Modifies state of antibodies
    private void pickUpAntiUnit(){
        for(int i = 0 ; i < 3; ++i){
            Iterator<Antibody> abIterator = areas.get(i).getAntibodies().iterator();
            while(abIterator.hasNext()){
                Antibody ab = abIterator.next();
                if(!ab.isAlive() && !ab.toSpawn){
                    abIterator.remove();
                    areas.get(i).removeAntibody(ab);
                }
            }
        }
    }

    // Impure function: Modifies state of all of queueViruses
    private void toAddViruse(){
        areas.get(0).addAllVirus(queueVirusArea1);
        queueVirusArea1.clear();
        areas.get(1).addAllVirus(queueVirusArea2);
        queueVirusArea2.clear();
        areas.get(2).addAllVirus(queueVirusArea3);
        queueVirusArea3.clear();
    }

    // Impure function: Modifies state of areas, viruses and waveManager
    public void putVirusToArea(int area){
        areas.get(area).addAllVirus(waveManager.allWaves.get("Wave_"+waveManager.currentWaveCount.toString()+"Area_"+area));
    }

    // Impure function: Modifies state of thread
    private void waitState(int time){
       try{
           for(int i = 1 ; i <= time ; i++) {
               if(timeManager.inputType.equals("slowdown")){
                   Thread.sleep((int)(1000*timeManager.slowDownMultiplier));
               }
               else if(timeManager.inputType.equals("fastforward")) {
                   Thread.sleep((int)(1000*timeManager.fastForwardMultiplier));
               }
               else{
                   Thread.sleep((int)(1000*0.125));
               }
           }
       }catch (InterruptedException ex){
            ex.printStackTrace();
       }
    }

    // Impure function: Modifies state of game loop
    @Override
    public void run() {
       startGameLoop();
    }

    // Impure function: Modifies state of id
    public void setId(String id) {
        this.id = id;
    }

    // Impure function: Modifies state of state
    public void setState(int state){
        this.state = state;
    }

    // Pure function
    // Pure function: Doesn't modify state, only return a value base on inputs
    public Integer getState(){
        return state;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public GeneticCodeManager getGeneticCodeManager(){ return geneticCodeManager; }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public Boolean getNotSpawnedYet() {
        return notSpawnedYet;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public TimeManager getTimeManager() {
        return timeManager;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public Inventory getInventory() {
        return inventory;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public List<Area> getAreas() {
        return areas;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public WaveManager getWaveManager() {
        return waveManager;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public Shop getShop() {
        return shop;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public List<Antibody> getQueueAntibobyArea1() {
        return queueAntibobyArea1;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public List<Antibody> getQueueAntibobyArea2() {
        return queueAntibobyArea2;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public List<Antibody> getQueueAntibobyArea3() {
        return queueAntibobyArea3;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public List<Virus> getQueueVirusArea1() {
        return queueVirusArea1;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public List<Virus> getQueueVirusArea2() {
        return queueVirusArea2;
    }

    // Pure function: Doesn't modify state, only return a value base on inputs
    public List<Virus> getQueueVirusArea3() {
        return queueVirusArea3;
    }

}
