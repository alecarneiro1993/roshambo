package com.alexcarneiro.roshambo.factories;

import com.alexcarneiro.roshambo.entities.Player;
import com.alexcarneiro.roshambo.enums.PlayerType;
import com.github.javafaker.Faker;

public class PlayerFactory {

  private static final Faker faker = new Faker();

  public static Player createPlayer() {
    Player player = new Player();
    player.setName(faker.name().fullName());
    player.setType(PlayerType.PLAYER);
    player.setImage(faker.internet().image());
    player.setHealth(100);

    return player;
  }

  public static Player createComputerPlayer() {
    Player player = new Player();
    player.setName(faker.name().fullName());
    player.setType(PlayerType.COMPUTER);
    player.setImage(faker.internet().image());
    player.setHealth(100);

    return player;
  }
}