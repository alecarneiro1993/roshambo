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
import java.util.List;
import java.util.ArrayList;
import java.lang.Object;

import entities.Player;
import entities.GameTurn;

import enums.Option;

import repositories.PlayerRepository;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {
    @Autowired
    PlayerRepository playerRepository;

    Map<String, Object> response = new HashMap<>();
    Map data = new HashMap();

    @GetMapping("/options")
    public ResponseEntity getOptions() {
        Map data = new HashMap();
        data.put("options", Option.values());
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/players")
    public ResponseEntity getPlayers() {
        Map data = new HashMap();
        List<Player> players = new ArrayList<Player>();
        playerRepository.deleteAll();
        playerRepository.save(new Player("Player 1 (You)", "player", "ryu.png"));
        playerRepository.save(new Player("CPU", "computer", "sagat.png"));
        data.put("players", playerRepository.findAll());
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/resolve")
    public ResponseEntity resolveTurn(@RequestBody GameTurn gameTurn) {
        Map data = new HashMap();
        Option computerChoice = Option.getRandom();
        int turnValue = gameTurn.resolve(computerChoice.getValue());
        int damage = 0;
        if (turnValue != 0) {
            damage = gameTurn.generateDamage();
            Player damagedPlayer = playerRepository.findByType(turnValue == 1 ? "computer" : "player");
            damagedPlayer.takeDamage(damage);
            playerRepository.save(damagedPlayer);
        }

        data.put("damageTaken", damage);
        data.put("turnResult", turnValue);
        data.put("computerChoice", computerChoice);
        data.put("players", playerRepository.findAll());
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}