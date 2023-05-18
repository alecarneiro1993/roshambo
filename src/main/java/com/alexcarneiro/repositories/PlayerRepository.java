
package repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import entities.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Player findByType(String type);
}
