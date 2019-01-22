package pl.marcool.intivepatronage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcool.intivepatronage.models.Reservation;
import pl.marcool.intivepatronage.models.dto.ReservationDTO;
import pl.marcool.intivepatronage.repositores.ReservationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        var reservation = dtoToReservation(reservationDTO);
        if (reservationRepository.findById(reservation.getId()).isPresent()) {
            throw new IllegalArgumentException(reservation.getId() + " - already exist");
        }
        isCommonConditionsCorrect("none", reservation);
        reservationRepository.save(reservation);
        return reservationDTO;
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
        var reservation = dtoToReservation(reservationDTO);
        if (reservationRepository.findById(reservation.getId()).isPresent() & !id.equals(reservation.getId())) {
            throw new IllegalArgumentException(reservation.getId() + " - already exist");
        }
        isCommonConditionsCorrect(findById(id).getId(), reservation);
        reservationRepository.deleteById(id);
        return reservationToDTO(reservationRepository.save(reservation));
    }

    public void deleteById(String id) {
        reservationRepository.deleteById(findById(id).getId());
    }

    public void deleteAll() {
        reservationRepository.deleteAll();
    }

    private void isCommonConditionsCorrect(String id, Reservation reservation) throws IllegalArgumentException {
        roomsService.findById(reservation.getConferenceRoomId());
        organizationsService.findById(reservation.getOrganizationId());
        var reservationBegin = reservation.getBeginDate();
        var reservationEnd = reservation.getEndDate();
        if (reservationBegin.isAfter(reservationEnd)) {
            throw new IllegalArgumentException("The end date is earlier than the start date");
        }
        if (reservationBegin.plusMinutes(5).isAfter(reservationEnd)) {
            throw new IllegalArgumentException("Minimum rental time is 5 minutes");
        }
        if (reservationBegin.plusHours(2).isBefore(reservationEnd)) {
            throw new IllegalArgumentException("Maximum rental time is 2 hours");
        }
        var condition1 = reservationRepository.findByConferenceRoomIdAndBeginDateBeforeAndEndDateAfter
                (reservation.getConferenceRoomId(), reservationBegin.plusSeconds(60), reservationBegin);
        var condition2 = reservationRepository.findByConferenceRoomIdAndBeginDateAfterAndBeginDateBefore
                (reservation.getConferenceRoomId(), reservationBegin, reservationEnd);
        var condition3 = reservationRepository.findById(id);
        if ((!condition1.isEmpty() && condition3.isEmpty())
                || (!condition2.isEmpty() && condition3.isEmpty())) {
            throw new IllegalArgumentException("Room is already reserved at this time");
        }
    }

    //ObjectMapper doesn't work with dates, need to manually rewrite fields
    DateTimeFormatter stringToDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    DateTimeFormatter dateToString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    Reservation dtoToReservation(ReservationDTO reservationDTO) {
        var reservation = new Reservation();
        reservation.setBeginDate(LocalDateTime.parse(reservationDTO.getBeginDate(), stringToDate));
        reservation.setEndDate(LocalDateTime.parse(reservationDTO.getEndDate(), stringToDate));
        reservation.setConferenceRoomId(reservationDTO.getConferenceRoomId());
        reservation.setId(reservationDTO.getId());
        reservation.setOrganizationId(reservationDTO.getOrganizationId());
        return reservation;
    }

    ReservationDTO reservationToDTO(Reservation reservation) {
        var reservationDTO = new ReservationDTO();
        reservationDTO.setBeginDate(reservation.getBeginDate().format(dateToString));
        reservationDTO.setEndDate(reservation.getEndDate().format(dateToString));
        reservationDTO.setConferenceRoomId(reservation.getConferenceRoomId());
        reservationDTO.setId(reservation.getId());
        reservationDTO.setOrganizationId(reservation.getOrganizationId());
        return reservationDTO;
    }
}