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

import com.alexcarneiro.roshambo.dtos.GameTurnDTO;
import com.alexcarneiro.roshambo.services.GameService;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {    
    @Autowired
    GameService gameService;

    @GetMapping("/options")
    public ResponseEntity getOptions() {
        Map<String, Object> response = new HashMap<>();
        Map data = new HashMap();
        data.put("options", gameService.getOptions());
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/players")
    public ResponseEntity getPlayers() {
        Map<String, Object> response = new HashMap<>();
        Map data = new HashMap(){{
            put("players", gameService.getNewPlayers());
        }};
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resolve")
    public ResponseEntity resolveGameTurn(@RequestBody GameTurnDTO gameTurn) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", gameService.process(gameTurn));
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/reset")
    public ResponseEntity resetGame() {
        Map<String, Object> response = new HashMap<>();
        gameService.resetGame();
        Map data = new HashMap(){{
            put("players", gameService.getNewPlayers());
        }};
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
}