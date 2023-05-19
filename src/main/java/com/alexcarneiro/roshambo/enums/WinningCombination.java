package enums;

import java.util.List;
import java.util.ArrayList;

public enum WinningCombination {
  ROCK_SCISSOR(0, 2),
  PAPER_ROCK(1, 0),
  SCISSOR_PAPER(2, 1);

  private List<Integer> combination = new ArrayList<Integer>();

  WinningCombination(int playerValue, int computerValue) {
    this.combination.add(playerValue);
    this.combination.add(computerValue);
  }

  public List<Integer> getCombination() { 
      return combination;
  }

  public static List<List<Integer>> getValuesOfCombinations() {
    List<List<Integer>> combinations = new ArrayList<List<Integer>>();
    for (WinningCombination combination : WinningCombination.values()) {
      combinations.add(combination.getCombination());
    }
    return combinations;
  }
}