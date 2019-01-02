package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.repositores.ConferenceRoomRepository;
import pl.marcool.intivepatronage.repositores.OrganizationRepository;
import pl.marcool.intivepatronage.repositores.ReservationRepository;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;
    private String message = "The following error was detected:\n";

    public void save(Reservation reservation) throws MyExceptions {
        String isNewIdExist = findById(reservation.getId()).getId();
        if (reservation.getBeginDate().isAfter(reservation.getEndDate()))
            throw new MyExceptions(message + "The end date is earlier than the start date");
        if (organizationRepository.findById(reservation.getOrganizationId()).getName().equals("empty"))
            throw new MyExceptions(message + "There is no organization named '" + reservation.getOrganizationId());
        if (conferenceRoomRepository.findById(reservation.getConferenceRoomId()).getName().equals("empty"))
            throw new MyExceptions(message + "There is no conference room with id '" + reservation.getConferenceRoomId());
        if (!isNewIdExist.equals("empty"))
            throw new MyExceptions(message + "ID '" + isNewIdExist + "' is already used");
        reservationRepository.save(reservation);
    }

    public List getAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(String Id) {
        return reservationRepository.findById(Id);
    }

    public void update(String id, Reservation reservation) throws MyExceptions {
        String isOldIdExist = findById(id).getId();
        String isNewIdExist = findById(reservation.getId()).getId();
        if (isOldIdExist.equals("empty"))
            throw new MyExceptions(message + "Nie znaleziono rezerwacji o Id: '" + isOldIdExist);
        if (!isNewIdExist.equals("empty") & !isNewIdExist.equals(isOldIdExist))
            throw new MyExceptions(message + "ID '" + isNewIdExist + "' is already used");
        reservationRepository.update(id, reservation);
    }

    public void deleteById(String id) {
        reservationRepository.deleteById(id);
    }

    public void deleteAll() {
        reservationRepository.deleteAll();
    }
}