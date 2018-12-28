package pl.marcool.intivepatronage.repositores;

import org.springframework.stereotype.Repository;
import pl.marcool.intivepatronage.models.Reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationRepository {

    Map<String, Reservation> reservationList = new HashMap<>();

    public void save(Reservation reservation) {
        reservationList.put(reservation.getId(), reservation);
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservationList.values());
    }

    public Reservation findById(String id) {
        Reservation empty = new Reservation();
        empty.setId("pusty");
        if (reservationList.containsKey(id)) return reservationList.get(id);
        return empty;
    }

    public void update(String id, Reservation reservation) {
        reservationList.remove(id);
        reservationList.put(reservation.getId(), reservation);
    }

    public void deleteById(String id) {
        reservationList.remove(id);
    }

    public void deleteAll() {
        reservationList.clear();
    }
}
