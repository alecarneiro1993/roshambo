package entities;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Set;
import java.util.HashSet;

import entities.Player;

import enums.Option;

public class GameTurn {
  private List<Integer> combination = new ArrayList<Integer>();
  
  private final int damageLimit = 51;
  private final int minimumDamage = 5;

  public GameTurn(String playerChoice, String computerChoice) { // need to remove computerChoice
    this.combination.add(Option.valueOf(playerChoice).getValue());
  }

  private enum WinningCombination {
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

  public List<Integer> getCombination() {
    return this.combination;
  }

  public int resolve(int computerValue) {
    this.combination.add(computerValue);
    int outcome = this.processTurnOutcome();
    
    if (outcome == 0) {
      return 0;
    }
    return outcome;
  }

  public int generateDamage() {
    return (int) ((Math.random() * (damageLimit - minimumDamage)) + minimumDamage);
  }

  private int processTurnOutcome() {
    if (isTie()) {
      return 0;
    }

    List<List<Integer>> winningCombinations = WinningCombination.getValuesOfCombinations();

    if (winningCombinations.contains(this.combination)) {
      return 1;
    }

    return -1;
  }

  private Boolean isTie() {
    Set<Integer> set = new HashSet<Integer>(this.combination);
    return set.size() == 1; 
  }
}
