package models;

import lombok.Setter;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Set;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonCreator; 
import com.fasterxml.jackson.annotation.JsonProperty;

import enums.*;

public class GameTurn {
  private Integer playerValue;
  @Setter private Integer computerValue;
  
  private final int[] damageLimit = { 25, 60 };

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

  public int process() {
    if (isTie()) {
      return 0;
    }

    List<List<Integer>> winningCombinations = WinningCombination.getValuesOfCombinations();
    return winningCombinations.contains(this.getCombination()) ? 1 : -1;
  }

  public int generateDamage() {
    return (int) ((Math.random() * (damageLimit[1] - damageLimit[0])) + damageLimit[0]);
  }

  private Boolean isTie() {
    return new HashSet<Integer>(this.getCombination()).size() == 1; 
  }
}
