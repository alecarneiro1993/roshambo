package com.alexcarneiro.roshambo.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Enumerable that represents the possible combinations
 * a human player can win from the computer.
 */
public enum WinningCombination {
  ROCK_SCISSOR(Option.ROCK, Option.SCISSOR),
  PAPER_ROCK(Option.PAPER, Option.ROCK),
  SCISSOR_PAPER(Option.SCISSOR, Option.PAPER);

  @Getter @Setter private List<Option> combination;

  WinningCombination(Option playerChoice, Option computerChoice) {
    this.combination = new ArrayList<Option>(){{
      add(playerChoice);
      add(computerChoice);
    }};
  }

  /**
   * Function that returns all possible winning
   * lists to be compared with the current turn's list
   * 
   * @return List<List<Integer>>
   */
  public static List<List<Option>> getCombinations() {    
    return new ArrayList<List<Option>>(){{
      for (WinningCombination winningCombination : WinningCombination.values()) {
        add(winningCombination.getCombination());
      }
    }};
  }
}