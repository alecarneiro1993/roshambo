package com.alexcarneiro.roshambo.enums;

import lombok.Getter;

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