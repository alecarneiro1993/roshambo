package enums;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

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

  public static List<List<Integer>> getValuesOfCombinations() {    
    return new ArrayList<List<Integer>>(){{
      for (WinningCombination winningCombination : WinningCombination.values()) {
        add(winningCombination.getCombination());
      }
    }};
  }
}