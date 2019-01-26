package pl.marcool.intivepatronage.repositores;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.marcool.intivepatronage.models.Reservation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, String> {
    List<Reservation> findAll();

    List<Reservation> findByConferenceRoomIdAndBeginDateBeforeAndEndDateAfterAndIdIsNotContaining
            (String conferenceRoomId, LocalDateTime date1, LocalDateTime date2, String id);

    List<Reservation> findByConferenceRoomIdAndBeginDateAfterAndBeginDateBeforeAndIdIsNotContaining
            (String conferenceRoomId, LocalDateTime date1, LocalDateTime date2, String id);

}
