package com.alexcarneiro.roshambo.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.alexcarneiro.roshambo.entities.Player;

@Transactional
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Player findByType(String type);

    boolean existsByHealthEquals(int health);
    
    @Modifying
    @Query("UPDATE Player p set p.health = 100")
    void resetPlayersHealth();
}
