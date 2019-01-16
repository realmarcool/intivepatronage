package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.repositores.OrganizationRepository;
import pl.marcool.intivepatronage.repositores.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ConferenceRoomService conferenceRoomService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    public void save(Reservation reservation) throws MyExceptions {
        if (reservationRepository.findById(reservation.getId()).isPresent())
            throw new MyExceptions("Reservation ID:" + reservation.getId() + " - is already used.");
        isCommonConditionsCorrect("none", reservation);
        reservationRepository.save(reservation);
    }

    public Iterable<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(String id) throws MyExceptions {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isEmpty()) throw new MyExceptions("Reservation ID:" + id + " - not found.");
        return reservation.get();
    }

    public void update(String id, Reservation reservation) throws MyExceptions {
        if (reservationRepository.findById(id).isEmpty())
            throw new MyExceptions("Reservation ID:" + id + " - not found.");
        if (reservationRepository.findById(reservation.getId()).isPresent() & !id.equals(reservation.getId()))
            throw new MyExceptions("Reservation ID:" + reservation.getId() + " - is already used.");
        isCommonConditionsCorrect(id, reservation);
        reservationRepository.deleteById(id);
        reservationRepository.save(reservation);
    }

    public void deleteById(String id) throws MyExceptions {
        if (reservationRepository.findById(id).isEmpty())
            throw new MyExceptions("Reservation ID:" + id + " - not found.");
        reservationRepository.deleteById(id);
    }

    public void deleteAll() {
        reservationRepository.deleteAll();
    }

    private void isCommonConditionsCorrect(String id, Reservation reservation) throws MyExceptions {
        conferenceRoomService.findById(reservation.getConferenceRoomId());
        LocalDateTime reservationBegin = reservation.getBeginDate();
        LocalDateTime reservationEnd = reservation.getEndDate();
        if (organizationRepository.findById(reservation.getOrganizationId()).isEmpty())
            throw new MyExceptions("Organization ID:" + reservation.getOrganizationId() + " - not found.");
        if (reservationBegin.getSecond() != 0 || reservationEnd.getSecond() != 0)
            throw new MyExceptions("Seconds must be set to 0.");
        if (reservationBegin.getNano() != 0 || reservationEnd.getNano() != 0)
            throw new MyExceptions("Nanoseconds must be set to 0.");
        if (reservationBegin.isAfter(reservationEnd))
            throw new MyExceptions("The end date is earlier than the start date.");
        if (reservationBegin.plusMinutes(5).isAfter(reservationEnd))
            throw new MyExceptions("Minimum rental time is 5 minutes.");
        if (reservationBegin.plusHours(2).isBefore(reservationEnd))
            throw new MyExceptions("Maximum rental time is 2 hours.");
        List<Reservation> reservationList = reservationRepository.findAll()
                .stream()
                .filter(tempReservation -> !tempReservation.getId().equals(id))
                .collect(Collectors.toList());
        if (reservationList
                .stream()
                .anyMatch(date -> (reservationBegin.plusMinutes(1).isAfter(date.getBeginDate()) & reservationBegin.isBefore(date.getEndDate())))
                || reservationList
                .stream()
                .anyMatch(date -> (reservationBegin.isBefore(date.getBeginDate()) & reservationEnd.isAfter(date.getBeginDate()))))
            throw new MyExceptions("Conference Room is already reserved at this time.");
    }
}