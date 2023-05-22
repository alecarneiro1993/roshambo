package com.alexcarneiro.roshambo.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.alexcarneiro.roshambo.enums.Option;
import com.alexcarneiro.roshambo.enums.PlayerType;
import com.alexcarneiro.roshambo.enums.Outcome;
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

  public Iterable<Player> getPlayers() {
    if(playerRepository.existsByHealthEquals(0)) {
      playerRepository.resetPlayersHealth();
    }

    return playerRepository.count() < 2 ? this.createPlayers() : playerRepository.findAll();
  }

  public Map<String, Object> process(GameTurnDTO gameTurn) {
    Option computerChoice = Option.getRandom();
    gameTurn.setComputerValue(computerChoice.getValue());
    gameTurn.processOutcome();
    boolean gameOver = false;
    Outcome gameOutcome = gameTurn.getOutcome();
    int damage = gameTurn.generateDamage();
    
    
    if(gameOutcome != Outcome.DRAW) {
      PlayerType playerType = gameOutcome == Outcome.WIN ? PlayerType.COMPUTER : PlayerType.PLAYER;
      Player player = playerRepository.findByType(playerType.getValue());
      player.takeDamage(damage);
      
      if (player.getHealth() == 0) {
        gameOver = true;
      }

      playerRepository.save(player);
    }

    Map<String, Object> result = new HashMap<>();
    result.put("message", getTurnMessage(gameOutcome, damage));
    result.put("computerChoice", computerChoice);
    result.put("players", playerRepository.findAll());
    result.put("gameOver", gameOver);
    return result;
  }

  public void resetGame() {
    playerRepository.resetPlayersHealth();
  }

  private String getTurnMessage(Outcome outcome, int damage) {
    switch (outcome) {
      case WIN:
        return String.format("WIN: YOU'VE DEALT %s DAMAGE TO THE ENEMY", damage);
      case LOSE:
        return String.format("LOST: YOU'VE TAKEN %s DAMAGE FROM THE ENEMY", damage);
      default:
        return "DRAW: NO ONE TOOK DAMAGE";
    }
  }

  private Iterable<Player> createPlayers() {
    playerRepository.deleteAll();
    Player player = new Player("Player 1 (You)", PlayerType.PLAYER, "ryu.png");
    Player computer = new Player("CPU", PlayerType.COMPUTER, "sagat.png");
    return playerRepository.saveAll(List.of(player, computer));
  }
}
