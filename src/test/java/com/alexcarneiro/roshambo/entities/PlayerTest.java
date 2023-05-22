package com.alexcarneiro.roshambo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alexcarneiro.roshambo.factories.PlayerFactory;

public class PlayerTest {

    Player player;


    @BeforeEach
    public void beforeEach() {
      player = PlayerFactory.createPlayer();
    }

    @Test
    public void takeDamage() {
        int playerHealth = player.getHealth();
        int damage = 20;

        player.takeDamage(damage);

        assertEquals(playerHealth - damage, player.getHealth());
    }

    @Test
    public void isKnockedOut() {
        assertFalse(player.isKnockedOut());

        player.setHealth(0);

        assertTrue(player.isKnockedOut());
    }
}
