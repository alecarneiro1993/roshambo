package com.alexcarneiro.roshambo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alexcarneiro.roshambo.dtos.GameTurnDTO;
import com.alexcarneiro.roshambo.entities.Player;
import com.alexcarneiro.roshambo.enums.Option;
import com.alexcarneiro.roshambo.enums.Outcome;
import com.alexcarneiro.roshambo.enums.PlayerType;
import com.alexcarneiro.roshambo.repositories.PlayerRepository;


/**
 * Service that handles game related business logic.
 */
@Service
public class GameService {
  private final PlayerRepository playerRepository;
  private final Logger logger = LoggerFactory.getLogger(GameService.class);

  public GameService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  /**
   * Function that returns all possible Options
   */
  public Option[] getOptions() {
    return Option.values();
  }

  /**
   * Function that returns players
   * 
   * If there are no players, create and fetch them
   * If there are players, but one has no more health, reset and fetch them
   * If there are players, fetch them
   * 
   * @return Iterable<Player>
   */
  public Iterable<Player> getPlayers() {
    if(playerRepository.existsByHealthEquals(0)) {
      logger.info("** Resetting Players Health **");
      playerRepository.resetPlayersHealth();
    }

    return playerRepository.count() < 2 ? this.createPlayers() : playerRepository.findAll();
  }

  /**
   * Function that processes a GameTurn and returns
   * essential information, such as the amount of damage
   * a player has taken, the message to be displayed,
   * the computer choice, the updated players and if
   * the game is over or not
   * 
   * @return Map<String, Object>
   */
  public Map<String, Object> process(GameTurnDTO gameTurn) {
    logger.info("** Starting processing of GameTurn **");
    
    Option computerChoice = Option.getRandom();
    logger.info(String.format("** Computer has chosen %s **", computerChoice));
    
    gameTurn.setComputerChoice(computerChoice);
    gameTurn.processOutcome();
    boolean gameOver = false;
    Outcome gameOutcome = gameTurn.getOutcome();
    int damage = gameTurn.generateDamage();
    
    
    if(gameOutcome != Outcome.DRAW) {
      PlayerType playerType = gameOutcome == Outcome.WIN ? PlayerType.COMPUTER : PlayerType.PLAYER;
      Player player = playerRepository.findByType(playerType);

      player.takeDamage(damage);
      
      if (player.isKnockedOut()) {
        gameOver = true;
      }

      playerRepository.save(player);
    }

    logger.info(String.format("""
      ** Turn Results **
      Damage: %s
      Player chose: %s
      Computer chose: %s
      Outcome: %s
      Game Over?: %s
    """, damage, gameTurn.getPlayerChoice(), computerChoice, gameOutcome, gameOver));

    Map<String, Object> result = new HashMap<>();
    result.put("message", getTurnMessage(gameOutcome, damage));
    result.put("computerChoice", computerChoice);
    result.put("players", playerRepository.findAll());
    result.put("gameOver", gameOver);
    return result;
  }

  /**
   * Function that sets the players health to 100
   */
  public void resetGame() {
    playerRepository.resetPlayersHealth();
  }

  /**
   * Function that determines the message to be displayed to the user
   * based on the outcome and damage of the turn.
   * 
   * @param outcome - a member of the Outcome Enumerable
   * @param damage - the amount of dammage a player has taken
   * 
   * @return String
   */
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

  /**
   * Function that deletes all existing playerrs and creates
   * them from scratch
   */
  private Iterable<Player> createPlayers() {
    logger.info("** Creating new Players **");
    playerRepository.deleteAll();
    Player player = new Player("Player 1 (You)", PlayerType.PLAYER, "ryu.png");
    Player computer = new Player("CPU", PlayerType.COMPUTER, "sagat.png");
    return playerRepository.saveAll(List.of(player, computer));
  }
}
