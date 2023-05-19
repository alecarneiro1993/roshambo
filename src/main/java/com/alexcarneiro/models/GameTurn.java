package models;

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
  private List<Integer> combination = new ArrayList<Integer>();
  
  private final int damageLimit = 51;
  private final int minimumDamage = 1;

  @JsonCreator
  public GameTurn(@JsonProperty("playerChoice") String choice) {
    this.combination.add(Option.valueOf(choice).getValue());
  }

  public List<Integer> getCombination() {
    return this.combination;
  }

  public int process(int computerValue) {
    this.combination.add(computerValue);
    this.processTurnOutcome();
    
    return this.processTurnOutcome();
  }

  public int generateDamage() {
    return (int) ((Math.random() * (damageLimit - minimumDamage)) + minimumDamage);
  }

  private int processTurnOutcome() {
    if (isTie()) {
      return 0;
    }

    List<List<Integer>> winningCombinations = WinningCombination.getValuesOfCombinations();
    return winningCombinations.contains(this.combination) ? 1 : -1;
  }

  private Boolean isTie() {
    return new HashSet<Integer>(this.combination).size() == 1; 
  }
}
