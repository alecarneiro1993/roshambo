package com.alexcarneiro.roshambo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexcarneiro.roshambo.dtos.GameTurnDTO;
import com.alexcarneiro.roshambo.services.GameService;

/**
* Provides endpoints to game related requests.
*/
@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {    
    @Autowired
    GameService gameService;

    
    /**
     * GET Request that returns the available options of the game
     * @return ResponseEntity<?>
     * { data: { options: ["ROCK", "PAPER", "SCISSOR"] } }
     *
     */
    @GetMapping("/options")
    public ResponseEntity<?> getOptions() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("options", gameService.getOptions());
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET Request that returns the players of the game
     * @return ResponseEntity<?>
     * { data: { players: [Player, Player] } }
     * 
     */
    @GetMapping("/players")
    public ResponseEntity<?> getPlayers() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>(){{
            put("players", gameService.getPlayers());
        }};
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    /**
     * POST Request that processes the GameTurn
     * @param playerChoice the player's choice. 
     * @return ResponseEntity<?>
     * { 
     *   data: {
     *     message: String,
     *     computerChoice: String,
     *     players: [Player, Player],
     *     gameOver: Boolean
     *   } 
     * }
     * 
     */
    @PostMapping("/resolve")
    public ResponseEntity<?> resolveGameTurn(@RequestBody GameTurnDTO gameTurn) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", gameService.process(gameTurn));
        return ResponseEntity.ok(response);
    }
    
    /**
     * POST Request that resets the players health
     * @return ResponseEntity<?>
     * { data: { players: [Player, Player] } }
     * 
     */
    @PostMapping("/reset")
    public ResponseEntity<?> resetGame() {
        Map<String, Object> response = new HashMap<>();
        gameService.resetGame();
        Map<String, Object> data = new HashMap<>(){{
            put("players", gameService.getPlayers());
        }};
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
}