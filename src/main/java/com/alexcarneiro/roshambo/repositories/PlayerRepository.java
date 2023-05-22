package com.alexcarneiro.roshambo.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.alexcarneiro.roshambo.entities.Player;

/**
 * Repository that handles all statements related to
 * the Player table
 */
@Transactional
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    /**
     * Function that finds a Player by its type
     * 
     * @return Player
     */
    Player findByType(String type);

    /**
     * Function checks whether there's a Player with no health
     * @param health - value to compare
     * 
     * @return Boolean
     */
    Boolean existsByHealthEquals(int health);
    
    /**
     * Function that sets all players health back to 100
     */
    @Modifying
    @Query("UPDATE Player p set p.health = 100")
    void resetPlayersHealth();
}
