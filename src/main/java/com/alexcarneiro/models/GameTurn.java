package models;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Set;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonCreator; 
import com.fasterxml.jackson.annotation.JsonProperty;

import entities.Player;

import enums.*;

public class GameTurn {
  private Integer playerValue;
  private Integer computerValue;
  
  private final int maximumDamage = 60;
  private final int minimumDamage = 25;

  @JsonCreator
  public GameTurn(@JsonProperty("playerChoice") String choice) {
    this.playerValue = Option.valueOf(choice).getValue();
  }

  public List<Integer> getCombination() {
    return new ArrayList<Integer>(){{
      add(playerValue);
      add(computerValue);
    }};
  }

  public int process(int computerValue) {
    this.computerValue = computerValue;
    
    return this.processTurnOutcome();
  }

  public int generateDamage() {
    return (int) ((Math.random() * (maximumDamage - minimumDamage)) + minimumDamage);
  }

  private int processTurnOutcome() {
    if (isTie()) {
      return 0;
    }

    List<List<Integer>> winningCombinations = WinningCombination.getValuesOfCombinations();
    return winningCombinations.contains(this.getCombination()) ? 1 : -1;
  }

  private Boolean isTie() {
    return new HashSet<Integer>(this.getCombination()).size() == 1; 
  }
}
