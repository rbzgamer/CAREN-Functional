package com.maikw.CPE200ProjectCAREN;


import com.maikw.CPE200ProjectCAREN.apiclasses.ApiData_Base;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
@RestController
@RequestMapping(path = "/gamehandler") // http://localhost:8080/gamehandler
public class GameHandler {

    protected Map<String,Thread> map ;
    protected Map<String, Game> gameMap;
    private final boolean DEBUG = true;

    @Autowired
    public GameHandler(){
        map = new HashMap<>();
        gameMap = new HashMap<>();
    }


    //api
    @CrossOrigin
    @PostMapping(path = "/checkid") // http://localhost:8080/gamehandler/checkid
    public String checkId(@RequestBody ApiData_Base data){
        String id = data.getId();

        if(map.containsKey(id)){
            if(DEBUG) System.out.println("This id [" + id + "] already exist.");
        }else{
            Random random = new Random(System.currentTimeMillis());
            id = Integer.toString(random.nextInt());
            if(DEBUG) System.out.println("id = " + id);
            while (map.containsKey(id)){
                if(DEBUG) System.out.println("id = " + id);
                id = Integer.toString(random.nextInt());
            }
            return id ;
        }
        return id;
    }


    @CrossOrigin
    @PostMapping(path = "/rungame") // http://localhost:8080/gamehandler/runGame
    public void runGame(@RequestBody ApiData_Base data ){
        String id = data.getId();
        if(DEBUG) System.out.println("ID = " + id);
        if (!map.containsKey(id)) {
            Game game = new Game();
            game.setId(id);
            gameMap.put(id, game);
            map.put(id, new Thread(game));
            game.setId(id);
            if(DEBUG) System.out.println("postset1 id = " + game.id);
        }
        Thread.State tState = map.get(id).getState();
        if(DEBUG) System.out.println("thread state : " + tState);
        if(tState.equals(Thread.State.NEW)){
            if(DEBUG) System.out.println("thread NEW, running new thread");
            map.get(id).setDaemon(true);
            map.get(id).start();
        }else if(tState.equals(Thread.State.TERMINATED)){
            if(DEBUG) System.out.println("thread terminated, creating new thread");
            map.remove(id);
            gameMap.remove(id);

            Game game = new Game();
            game.setId(id);
            gameMap.put(id, game);
            map.put(id, new Thread(game));

            map.get(id).setDaemon(true);
            map.get(id).start();
            game.setId(id);
            if(DEBUG) System.out.println("postset2 id = " + game.id);
        }
    }

    @CrossOrigin
    @PostMapping(path = "/getGame") // http://localhost:8080/gamehandler/getGame
    public Game getGame(@RequestBody ApiData_Base  data){
        if(!map.containsKey(data.getId())){
            runGame(data);
        }
        return gameMap.get(data.getId());
    }

}
