package com.alexcarneiro.roshambo.enums;

import java.util.Random;

import lombok.Getter;

public enum Option {
  ROCK(0),
  PAPER(1),
  SCISSOR(2);

  @Getter
  private int value;

  Option(int value) {
    this.value = value;
  }

  public static Option getRandom() {
    Random random = new Random();
    Option[] options = Option.values();
    return options[random.nextInt(options.length)];
  }
}