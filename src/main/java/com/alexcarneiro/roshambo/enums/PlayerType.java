package com.alexcarneiro.roshambo.enums;

import lombok.Getter;

/**
 * Enumerable that represents the possible types of a Player
 */
public enum PlayerType {
  PLAYER("player"),
  COMPUTER("computer");

  @Getter
  private final String value;

  PlayerType(String value) {
    this.value = value;
  }
}