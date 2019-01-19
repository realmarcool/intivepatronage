package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.repositores.OrganizationRepository;
import pl.marcool.intivepatronage.repositores.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private RoomService roomService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    public Reservation save(Reservation reservation) throws MyExceptions {
        if (reservationRepository.findById(reservation.getId()).isPresent())
            throw new MyExceptions(400,
                    "{\"Error\":\"" + reservation.getId() + " - already exist\"}");
        isCommonConditionsCorrect("none", reservation);
        return reservationRepository.save(reservation);
    }

    public Iterable<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(String id) throws MyExceptions {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new MyExceptions(404,
                        "{\"Error\":\"" + id + " - not found\"}"));
    }

    public Reservation update(String id, Reservation reservation) throws MyExceptions {
        if (reservationRepository.findById(reservation.getId()).isPresent() & !id.equals(reservation.getId()))
            throw new MyExceptions(400,
                    "{\"Error\":\"" + reservation.getId() + " - already exist\"}");
        isCommonConditionsCorrect(findById(id).getId(), reservation);
        reservationRepository.deleteById(id);
        return reservationRepository.save(reservation);
    }

    public String deleteById(String id) throws MyExceptions {
        reservationRepository.deleteById(findById(id).getId());
        return "{\"" + id + " - deleted\"}";
    }

    public String deleteAll() {
        reservationRepository.deleteAll();
        return "{\"Entire Reservations database deleted\"}";
    }

    private void isCommonConditionsCorrect(String id, Reservation reservation) throws MyExceptions {
        roomService.findById(reservation.getConferenceRoomId());
        organizationRepository.findById(reservation.getOrganizationId());
        LocalDateTime reservationBegin = reservation.getBeginDate();
        LocalDateTime reservationEnd = reservation.getEndDate();
        if (reservationBegin.getSecond() != 0 || reservationEnd.getSecond() != 0)
            throw new MyExceptions(400,
                    "{\"Error\":\"" + "\"Seconds must be set to 0\"}");
        if (reservationBegin.getNano() != 0 || reservationEnd.getNano() != 0)
            throw new MyExceptions(400,
                    "{\"Error\":\"" + "\"Nanoseconds must be set to 0\"}");
        if (reservationBegin.isAfter(reservationEnd))
            throw new MyExceptions(400,
                    "{\"Error\":\"" + "\"The end date is earlier than the start date\"}");
        if (reservationBegin.plusMinutes(5).isAfter(reservationEnd))
            throw new MyExceptions(400,
                    "{\"Error\":\"" + "\"Minimum rental time is 5 minutes\"}");
        if (reservationBegin.plusHours(2).isBefore(reservationEnd))
            throw new MyExceptions(400,
                    "{\"Error\":\"" + "\"Maximum rental time is 2 hours\"}");
        List<Reservation> reservationList = reservationRepository.findAll()
                .stream()
                .filter(tempReservation -> !tempReservation.getId().equals(id))
                .collect(Collectors.toList());
        if (reservationList
                .stream()
                .anyMatch(date -> (reservationBegin.plusMinutes(1).isAfter(date.getBeginDate()) && reservationBegin.isBefore(date.getEndDate())))
                ||
                reservationList
                .stream()
                .anyMatch(date -> (reservationBegin.isBefore(date.getBeginDate()) && reservationEnd.isAfter(date.getBeginDate()))))
            throw new MyExceptions(409,
                    "{\"Error\":\"" + "\"Conference Room is already reserved at this time\"}");
    }
}