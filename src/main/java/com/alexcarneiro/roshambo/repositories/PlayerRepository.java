
package repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import entities.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Player findByType(String type);
    
    @Transactional
    @Modifying
    @Query("UPDATE Player p set p.health = 100")
    void resetPlayersHealth();
}
