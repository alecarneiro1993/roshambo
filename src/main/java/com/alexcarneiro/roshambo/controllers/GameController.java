package com.alexcarneiro.roshambo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.HashMap;
import java.lang.Object;

import models.GameTurn;
import services.GameService;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {    
    @Autowired
    GameService gameService;

    Map<String, Object> response = new HashMap<>();

    @GetMapping("/options")
    public ResponseEntity getOptions() {
        Map data = new HashMap();
        data.put("options", gameService.getOptions());
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/players")
    public ResponseEntity getPlayers() {
        Map data = new HashMap(){{
            put("players", gameService.getNewPlayers());
        }};
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/resolve")
    public ResponseEntity resolve(@RequestBody GameTurn gameTurn) {
        response.put("data", gameService.process(gameTurn));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/reset")
    public ResponseEntity reset() {
        gameService.resetGame();
        Map data = new HashMap(){{
            put("players", gameService.getNewPlayers());
        }};
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}