package com.alexcarneiro.roshambo.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.alexcarneiro.roshambo.enums.Option;
import com.alexcarneiro.roshambo.enums.PlayerType;
import com.alexcarneiro.roshambo.dtos.GameTurnDTO;
import com.alexcarneiro.roshambo.entities.Player;
import com.alexcarneiro.roshambo.repositories.PlayerRepository;


@Service
public class GameService {
  private final PlayerRepository playerRepository;

  @Autowired
  public GameService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public Option[] getOptions() {
    return Option.values();
  }

  public Iterable<Player> getNewPlayers() {

  if (playerRepository.count() < 2) {
      Player player = new Player("Player 1 (You)", PlayerType.PLAYER, "ryu.png");
      Player computer = new Player("CPU", PlayerType.COMPUTER, "sagat.png");
      return playerRepository.saveAll(List.of(player, computer));
    }

    return playerRepository.findAll();
  }

  public Map<String, Object> process(GameTurnDTO gameTurn) {
    Option computerChoice = Option.getRandom();
    gameTurn.setComputerValue(computerChoice.getValue());
    int turnResult = gameTurn.process();
    int damage = 0;
    boolean gameOver = false;
    
    if (turnResult != 0) {
      damage = gameTurn.generateDamage();
      Player damagedPlayer = playerRepository.findByType(turnResult == 1 ? "computer" : "player");
      damagedPlayer.takeDamage(damage);
      if (damagedPlayer.getHealth() == 0) {
        gameOver = true;
      }
      playerRepository.save(damagedPlayer);
    }
    Map<String, Object> result = new HashMap<>();
    result.put("message", getTurnMessage(turnResult, damage));
    result.put("computerChoice", computerChoice);
    result.put("players", playerRepository.findAll());
    result.put("gameOver", gameOver);
    return result;
  }

  public void resetGame() {
    playerRepository.resetPlayersHealth();
  }

  private String getTurnMessage(int turnResult, int damage) {
    switch (turnResult) {
      case 1:
        return String.format("WIN: YOU'VE DEALT %s DAMAGE TO THE ENEMY", damage);
      case -1:
        return String.format("LOST: YOU'VE TAKEN %s DAMAGE FROM THE ENEMY", damage);
      default:
        return "DRAW: NO ONE TOOK DAMAGE";
    }
  }
}
