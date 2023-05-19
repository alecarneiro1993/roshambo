package services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import enums.Option;
import models.GameTurn;
import entities.Player;
import repositories.PlayerRepository;


@Service
public class GameService {

  @Autowired
  PlayerRepository playerRepository;

  public Option[] getOptions() {
    return Option.values();
  }

  public Iterable<Player> getNewPlayers() {
    playerRepository.resetPlayersHealth();

  if (playerRepository.count() < 2) {
      Player player = new Player("Player 1 (You)", "player", "ryu.png");
      Player computer = new Player("CPU", "computer", "sagat.png");
      return playerRepository.saveAll(List.of(player, computer));
    }

    return playerRepository.findAll();
  }

  public Map process(GameTurn gameTurn) {
    Option computerChoice = Option.getRandom();
    int turnResult = gameTurn.process(computerChoice.getValue());
    int damage = 0;
    
    if (turnResult != 0) {
      damage = gameTurn.generateDamage();
      Player damagedPlayer = playerRepository.findByType(turnResult == 1 ? "computer" : "player");
      damagedPlayer.takeDamage(damage);
      playerRepository.save(damagedPlayer);
    }
    Map result = new HashMap();
    result.put("damageTaken", damage);
    result.put("turnResult", turnResult);
    result.put("computerChoice", computerChoice);
    result.put("players", playerRepository.findAll());
    return result;
  }

  public void resetGame() {
    playerRepository.resetPlayersHealth();
  }
}
