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

import enums.*;

public class GameTurn {
  private Integer playerValue;
  @Setter private Integer computerValue;
  
  private final int[] damageLimit = { 25, 60 };

  private enum Outcome {
    WIN(1),
    DRAW(0),
    LOSE(-1);

    @Getter private int value;

    Outcome(int value) {
      this.value = value;
    }
  }

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
      return Outcome.DRAW.getValue();
    }

    List<List<Integer>> values = WinningCombination.getValuesOfCombinations();
    return (values.contains(this.getCombination()) ? Outcome.WIN : Outcome.LOSE).getValue();
  }

  public int generateDamage() {
    return (int) ((Math.random() * (damageLimit[1] - damageLimit[0])) + damageLimit[0]);
  }

  private Boolean isTie() {
    return new HashSet<Integer>(this.getCombination()).size() == 1; 
  }
}
