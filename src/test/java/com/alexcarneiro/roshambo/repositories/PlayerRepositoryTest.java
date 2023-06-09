package com.alexcarneiro.roshambo.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.alexcarneiro.roshambo.entities.Player;
import com.alexcarneiro.roshambo.enums.PlayerType;
import com.alexcarneiro.roshambo.factories.PlayerFactory;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PlayerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlayerRepository playerRepository;

    Player player;

    @BeforeEach
    public void beforeEach() {
      player = PlayerFactory.createPlayer();
    }

    @AfterEach
    public void afterEach() {
      playerRepository.deleteAll();
    }

    @Test
    public void findByType() {
        entityManager.persist(player);
        entityManager.flush();

        Player returnedPlayer = playerRepository.findByType(PlayerType.PLAYER);
        assertEquals(player, returnedPlayer);
    }
    
    @Test
    public void existsByHealthEquals() {
        player.setHealth(50);
        entityManager.persist(player);
        entityManager.flush();

        assertTrue(playerRepository.existsByHealthEquals(50));
        assertFalse(playerRepository.existsByHealthEquals(100));
    }
    
    @Test
    public void resetPlayersHealth() {
        player.setHealth(0);
        entityManager.persist(player);
        playerRepository.resetPlayersHealth();

        entityManager.flush();
        entityManager.clear();

        Player databasePlayer = playerRepository.findByType(player.getType());

        assertTrue(databasePlayer.getHealth() == 100);
    }
}
