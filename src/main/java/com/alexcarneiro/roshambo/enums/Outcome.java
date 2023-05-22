package com.alexcarneiro.roshambo.enums;

import lombok.Getter;

/**
 * Enumerable that represents the possible outcomes of a GameTurn.
 */
public enum Outcome {
  WIN(1),
  DRAW(0),
  LOSE(-1);

  @Getter
  private int value;

  Outcome(int value) {
    this.value = value;
  }
}