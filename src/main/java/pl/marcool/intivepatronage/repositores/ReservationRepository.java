package pl.marcool.intivepatronage.repositores;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.marcool.intivepatronage.models.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, String> {
    List<Reservation> findAll();
}
