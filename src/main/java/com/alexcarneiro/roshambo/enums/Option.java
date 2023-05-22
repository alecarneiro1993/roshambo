package com.alexcarneiro.roshambo.enums;

import java.util.Random;

import lombok.Getter;

/**
 * Enumerable that represents the available options in the game.
 */
public enum Option {
  ROCK(0),
  PAPER(1),
  SCISSOR(2);

  @Getter
  private int value;

  Option(int value) {
    this.value = value;
  }

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