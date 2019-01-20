package pl.marcool.intivepatronage.repositores;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.marcool.intivepatronage.models.Room;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, String> {
    Optional<Room> findByName(String name);
}