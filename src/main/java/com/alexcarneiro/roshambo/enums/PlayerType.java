package com.alexcarneiro.roshambo.enums;

import lombok.Getter;

public enum PlayerType {
  PLAYER("player"),
  COMPUTER("computer");

  @Getter
  private final String value;

  PlayerType(String value) {
    this.value = value;
  }
}