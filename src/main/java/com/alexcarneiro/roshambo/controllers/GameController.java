package com.alexcarneiro.roshambo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import models.Player;


@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {

    @GetMapping("/options")
    public ResponseEntity getOptions() {
        Map<String, Player.Options[]> response = new HashMap<>();
        response.put("data", Player.Options.values());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/players")
    public ResponseEntity getPlayers() {
        Map<String, List<Player>> response = new HashMap<>();
        List<Player> players = new ArrayList<Player>();
        Player player = new Player("Player 1 (You)", "player", "ryu.png");
        Player computer = new Player("CPU", "computer", "sagat.png");
        players.add(player);
        players.add(computer);
        response.put("data", players);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}