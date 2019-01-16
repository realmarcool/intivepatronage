package pl.marcool.intivepatronage.repositores;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.marcool.intivepatronage.models.ConferenceRoom;

import java.util.*;

@Repository
public interface ConferenceRoomRepository extends CrudRepository<ConferenceRoom, String> {

    Optional<ConferenceRoom> findByName(String name);
}