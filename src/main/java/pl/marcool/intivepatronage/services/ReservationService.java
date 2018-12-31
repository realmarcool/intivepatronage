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

    public String save(Reservation reservation) {
        String checkNewId = findById(reservation.getId()).getId();
        if(reservation.getStart().isAfter(reservation.getEnd())) return "Data zakończenia jest wcześniejsza niż rozpoczęcia";
        if(organizationRepository.findById(reservation.getOrganizationId()).getName()
                .equals("pusty")) return "Brak organizacji o nazwie '" + reservation.getOrganizationId();
        if(conferenceRoomRepository.findById(reservation.getConferenceRoomId()).getName()
                .equals("pusty")) return "Brak sali konferencyjnej o id '" + reservation.getConferenceRoomId();
        if (checkNewId.equals("pusty")) {
            reservationRepository.save(reservation);
            return "ok";
        }
        return "Uwaga, pole '" + checkNewId + "' już istnieje";
    }

    public List getAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(String Id){
        return reservationRepository.findById(Id);
    }

    public String update(String id, Reservation reservation) {

        //Czy stare Id istnieje
        String checkOldId = findById(id).getId();
        //Czy nowe Id istnieje
        String checkNewId = findById(reservation.getId()).getId();
        String errorMessage = "";

        //Jeżeli Id jest już zajęte
        if (!checkNewId.equals("pusty")&!checkNewId.equals(checkOldId)) errorMessage = "Id: '" + checkNewId + "' jest już zajęte!";
        //Jeżeli nie znaleziono rezerwacji
        if (checkOldId.equals("pusty")) errorMessage = "Nie znaleziono rezerwacji o Id: '" + checkOldId;
        if ((checkNewId.equals("pusty")||checkNewId.equals(checkOldId)) & !checkOldId.equals("pusty")) {
            reservationRepository.update(id, reservation);
            return "ok";
        } else return errorMessage;
    }

    public void deleteById(String id){
        reservationRepository.deleteById(id);
    }

    public void deleteAll(){
        reservationRepository.deleteAll();
    }
}