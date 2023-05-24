package com.alexcarneiro.roshambo.dtos;

import java.util.ArrayList;
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
 * playerChoice - one of the possible Options
 * computerChoice - one of the possible Options
 * outcome -  outcome
 */
@NoArgsConstructor
public class GameTurnDTO {
  @Getter @Setter private Option playerChoice;
  @Getter @Setter private Option computerChoice;
  @Getter @Setter public Outcome outcome;
  
  /**
  * Variable that holds the minimum and maximum damage
  * one player can dish out in a single turn
  */
  private final int[] damageLimit = { 25, 60 };  

  @JsonCreator
  public GameTurnDTO(@JsonProperty("playerChoice") String choice) {
    try {
      this.playerChoice = Option.valueOf(choice);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid Choice " + choice, e);
    }
  }

  /**
   * Function that gets both player and computer values
   * and returns them in a list
   * 
   * @return List<Option>
   */
  public List<Option> getCombination() {
    return new ArrayList<Option>(){{
      add(playerChoice);
      add(computerChoice);
    }};
  }

  /**
   * Function that determines the outcome based on the
   * combination of the Option values.
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
      List<List<Option>> values = WinningCombination.getCombinations();
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
    if(Outcome.DRAW == outcome) {
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
    return playerChoice == computerChoice;
  }
}
