package com.alexcarneiro.roshambo.enums;

import java.util.Random;

/**
 * Enumerable that represents the available options in the game.
 */
public enum Option {
  ROCK,
  PAPER,
  SCISSOR;

  /**
   * Function that randomly returns an Option
   *
   * @return Option
   */
  public static Option getRandom() {
    Random random = new Random();
    Option[] options = Option.values();
    return options[random.nextInt(options.length)];
  }
}