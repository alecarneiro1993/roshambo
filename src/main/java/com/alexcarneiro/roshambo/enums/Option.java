package enums;

import java.util.Random;

public enum Option {
  ROCK(0),
  PAPER(1),
  SCISSOR(2);

  private int value;

  Option(int value) {
    this.value = value;
  }

  public int getValue() { 
      return value;
  }

  public static Option getRandom() {
    Random random = new Random();
    Option[] options = Option.values();
    return options[random.nextInt(options.length)];
  }
}