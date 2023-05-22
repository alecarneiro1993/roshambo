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

/**
 * Representation of a GameTurn, consisting of:
 * playerValue - integer value of the player's choice
 * computerValue - integer value of the computer's choice
 * outcome - turn outcome
 */
@NoArgsConstructor
public class GameTurnDTO {
  @Getter @Setter private Integer playerValue;
  @Getter @Setter private Integer computerValue;
  @Getter @Setter public Outcome outcome;
  
  /**
  * Variable that holds the minimum and maximum damage
  * one player can dish out in a single turn
  */
  private final int[] damageLimit = { 25, 60 };  

  @JsonCreator
  public GameTurnDTO(@JsonProperty("playerChoice") String choice) {
    try {
      this.playerValue = Option.valueOf(choice).getValue();
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid Choice " + choice, e);
    }
  }

  /**
   * Function that gets both player and computer values
   * and returns them in a list
   * 
   * @return List<Integer>
   */
  public List<Integer> getCombination() {
    return new ArrayList<Integer>(){{
      add(playerValue);
      add(computerValue);
    }};
  }

  /**
   * Function that determines the outcome based on the
   * combination of the integer values.
   * 
   * The enum WinningCombination contains all possible
   * combinations for a human Player to win.
   * 
   * The outcome is based on whether both values are equal
   * and if it matches any of the WinningCombinations.
   */
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

  /**
   * Function that determines the amount of damage
   * the player that lost the turn will receive.
   * 
   * If the outcome is a draw, no damage is done.
   * Otherwise a random number is generated using
   * the limits specified by damageLimit.
   *
   * @return int
   */
  public int generateDamage() {
    if(outcome.getValue() == 0) {
      return 0;
    }

    return (int) ((Math.random() * (damageLimit[1] - damageLimit[0])) + damageLimit[0]);
  }

  /**
   * Function that determines if the current turn
   * is a tie.
   * 
   * A Tie is decided if both player and computer
   * values are equal.
   *
   * @return Boolean
   */
  private Boolean isTie() {
    return new HashSet<Integer>(this.getCombination()).size() == 1; 
  }
}
