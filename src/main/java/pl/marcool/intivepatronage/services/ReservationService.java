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
        String check = reservationRepository.findById(reservation.getId()).toString();
        if(!organizationRepository.findById(reservation.getOrganizationId()).toString()
                .equals("pusty")) return "Brak organizacji o nazwie '" + reservation.getOrganizationId();
        if(!conferenceRoomRepository.findById(reservation.getConferenceRoomId()).toString()
                .equals("pusty")) return "Brak sali konferencyjnej o id '" + reservation.getConferenceRoomId();
        if (check.equals("pusty")) {
            reservationRepository.save(reservation);
            return "ok";
        }
        return "Uwaga, pole '" + check + "' już istnieje";
    }

    public List getAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(String Id){
        return reservationRepository.findById(Id);
    }

    public void update(String id, Reservation reservation){
        reservationRepository.update(id, reservation);
    }

    public void deleteById(String id){
        reservationRepository.deleteById(id);
    }

    public void deleteAll(){
        reservationRepository.deleteAll();
    }
}