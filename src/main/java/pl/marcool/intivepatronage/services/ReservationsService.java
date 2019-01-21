package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.models.dto.ReservationDTO;
import pl.marcool.intivepatronage.repositores.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class ReservationsService {

    @Autowired
    private RoomsService roomsService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private OrganizationsService organizationsService;

    public ReservationDTO save(ReservationDTO reservationDTO) throws IllegalArgumentException {
        Reservation reservation = dtoToReservation(reservationDTO);
        System.out.println(reservation);
        if (reservationRepository.findById(reservation.getId()).isPresent())
            throw new IllegalArgumentException(reservation.getId() + " - already exist");
        isCommonConditionsCorrect("none", reservation);
        return reservationToDTO(reservationRepository.save(reservation));
    }

    public List<ReservationDTO> getAll() {
        return StreamSupport.stream(reservationRepository.findAll().spliterator(), false)
                .map(t -> reservationToDTO(t))
                .collect(Collectors.toList());
    }

    public ReservationDTO findById(String id) throws IllegalArgumentException {
        return reservationToDTO(reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + " - not found")));
    }

    public ReservationDTO update(String id, ReservationDTO reservationDTO) throws IllegalArgumentException {
        Reservation reservation = dtoToReservation(reservationDTO);
        if (reservationRepository.findById(reservation.getId()).isPresent() & !id.equals(reservation.getId()))
            throw new IllegalArgumentException(reservation.getId() + " - already exist");
        isCommonConditionsCorrect(findById(id).getId(), reservation);
        reservationRepository.deleteById(id);
        return reservationToDTO(reservationRepository.save(reservation));
    }

    public String deleteById(String id) {
        reservationRepository.deleteById(findById(id).getId());
        return "{\"" + id + " - deleted\"}";
    }

    public String deleteAll() {
        reservationRepository.deleteAll();
        return "{\"Entire Reservations database deleted\"}";
    }

    private void isCommonConditionsCorrect(String id, Reservation reservation) throws IllegalArgumentException {
        roomsService.findById(reservation.getConferenceRoomId());
        organizationsService.findById(reservation.getOrganizationId());
        LocalDateTime reservationBegin = reservation.getBeginDate();
        LocalDateTime reservationEnd = reservation.getEndDate();
        if (reservationBegin.getSecond() != 0 || reservationEnd.getSecond() != 0)
            throw new IllegalArgumentException("Seconds must be set to 0");
        if (reservationBegin.getNano() != 0 || reservationEnd.getNano() != 0)
            throw new IllegalArgumentException("Nanoseconds must be set to 0");
        if (reservationBegin.isAfter(reservationEnd))
            throw new IllegalArgumentException("The end date is earlier than the start date");
        if (reservationBegin.plusMinutes(5).isAfter(reservationEnd))
            throw new IllegalArgumentException("Minimum rental time is 5 minutes");
        if (reservationBegin.plusHours(2).isBefore(reservationEnd))
            throw new IllegalArgumentException("Maximum rental time is 2 hours");
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
            throw new IllegalArgumentException("Room is already reserved at this time");
    }

    //ObjectMapper doesn't work with dates, need to manually rewrite fields
    Reservation dtoToReservation(ReservationDTO reservationDTO){
        Reservation reservation = new Reservation();
        reservation.setBeginDate(reservationDTO.getBeginDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setConferenceRoomId(reservationDTO.getConferenceRoomId());
        reservation.setId(reservationDTO.getId());
        reservation.setOrganizationId(reservationDTO.getOrganizationId());
        return reservation;
    }
    ReservationDTO reservationToDTO(Reservation reservation){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setBeginDate(reservation.getBeginDate());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setConferenceRoomId(reservation.getConferenceRoomId());
        reservationDTO.setId(reservation.getId());
        reservationDTO.setOrganizationId(reservation.getOrganizationId());
        return reservationDTO;
    }
}