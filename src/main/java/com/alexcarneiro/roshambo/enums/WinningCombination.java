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
  ROCK_SCISSOR(0, 2),
  PAPER_ROCK(1, 0),
  SCISSOR_PAPER(2, 1);

  @Getter @Setter private List<Integer> combination;

  WinningCombination(int playerValue, int computerValue) {
    this.combination = new ArrayList<Integer>(){{
      add(playerValue);
      add(computerValue);
    }};
  }

  /**
   * Function that returns all possible winning
   * lists to be compared with the current turn's list
   * 
   * @return List<List<Integer>>
   */
  public static List<List<Integer>> getValuesOfCombinations() {    
    return new ArrayList<List<Integer>>(){{
      for (WinningCombination winningCombination : WinningCombination.values()) {
        add(winningCombination.getCombination());
      }
    }};
  }
}