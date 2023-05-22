package com.alexcarneiro.roshambo.dtos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.alexcarneiro.roshambo.enums.Option;
import com.alexcarneiro.roshambo.enums.Outcome;
import com.alexcarneiro.roshambo.enums.WinningCombination;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class GameTurnDTO {
  @Getter @Setter private Integer playerValue;
  @Getter @Setter private Integer computerValue;
  @Getter @Setter public Outcome outcome;
  
  private final int[] damageLimit = { 25, 60 };  

  @JsonCreator
  public GameTurnDTO(@JsonProperty("playerChoice") String choice) {
    try {
      this.playerValue = Option.valueOf(choice).getValue();
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid Choice " + choice, e);
    }
  }

  public List<Integer> getCombination() {
    return new ArrayList<Integer>(){{
      add(playerValue);
      add(computerValue);
    }};
  }

  public void processOutcome() {
    Outcome turnOutcome;
    if (isTie()) {
      turnOutcome = Outcome.DRAW;
    } else {
      List<List<Integer>> values = WinningCombination.getValuesOfCombinations();
      turnOutcome = values.contains(this.getCombination()) ? Outcome.WIN : Outcome.LOSE;
    }
    this.setOutcome(turnOutcome);
  }

  public int generateDamage() {
    if(outcome.getValue() == 0) {
      return 0;
    }

    return (int) ((Math.random() * (damageLimit[1] - damageLimit[0])) + damageLimit[0]);
  }

  private Boolean isTie() {
    return new HashSet<Integer>(this.getCombination()).size() == 1; 
  }
}
